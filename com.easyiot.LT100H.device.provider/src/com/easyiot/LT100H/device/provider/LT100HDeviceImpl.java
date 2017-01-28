package com.easyiot.LT100H.device.provider;

import java.util.concurrent.atomic.AtomicReference;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.service.metatype.annotations.Designate;

import com.easyiot.LT100H.device.api.capability.LT100HDeviceCapability.ProvideLT100HDevice;
import com.easyiot.LT100H.device.api.dto.LT100HSensorDataDTO;
import com.easyiot.LT100H.device.provider.configuration.LT100HDeviceConfiguration;
import com.easyiot.LT100H.device.provider.converter.AusloraDataConverter;
import com.easyiot.LT100H.device.provider.converter.TtnDataConverter;
import com.easyiot.auslora_websocket.protocol.api.AusloraWebsocketListener;
import com.easyiot.auslora_websocket.protocol.api.AusloraWebsocketProtocol;
import com.easyiot.auslora_websocket.protocol.api.dto.AusloraMetadataDTO;
import com.easyiot.base.api.Device;
import com.easyiot.ttn_mqtt.protocol.api.TtnMqttMessageListener;
import com.easyiot.ttn_mqtt.protocol.api.TtnMqttProtocol;

import osgi.enroute.dto.api.DTOs;

@ProvideLT100HDevice(version = "1.0.0")
@Component(name = "com.easyiot.LT100H.device")
@Designate(ocd = LT100HDeviceConfiguration.class, factory = true)
public class LT100HDeviceImpl implements Device, AusloraWebsocketListener {
	private LT100HDeviceConfiguration deviceConfiguration;
	private AtomicReference<LT100HSensorDataDTO> lastKnownData = new AtomicReference<LT100HSensorDataDTO>(
			new LT100HSensorDataDTO());
	private AusloraDataConverter myAusloraDataConverter = new AusloraDataConverter();
	private TtnDataConverter myTtnDataConverter = new TtnDataConverter();
	private AusloraWebsocketProtocol myWebsocketProtocol;

	@Reference
	private DTOs dtoConverter;

	@Override
	public void processMessage(AusloraMetadataDTO ausloraData) {
		LT100HSensorDataDTO sensorData = myAusloraDataConverter.convert(ausloraData, dtoConverter);
		System.out.println(String.format("Network name: %s, Gateway EUI: %s Device EUI: %s", "Auslora",
				ausloraData.gwts.get(0).gweui, deviceConfiguration.deviceEUI()));
		lastKnownData.set(sensorData);
	}

	@Override
	public void setProtocolHandler(AusloraWebsocketProtocol protocolInstance) {
		this.myWebsocketProtocol = protocolInstance;
	}

	// lambda that is subscribed to MQTT
	private TtnMqttMessageListener subsMethod = (metadata) -> {
		LT100HSensorDataDTO newData = myTtnDataConverter.convert(metadata, dtoConverter);
		System.out.println(String.format("Network name: %s, Gateway EUI: %s Device EUI: %s", "TTN",
				metadata.gwts.get(0).gateway_eui, deviceConfiguration.deviceEUI()));
		lastKnownData.set(newData);
	};

	// Bind mqttClient
	@Reference(name = "mqttProtocolReference", cardinality = ReferenceCardinality.OPTIONAL, policyOption = ReferencePolicyOption.GREEDY)
	private TtnMqttProtocol ttnMqttClient;

	@Activate
	public void activate(LT100HDeviceConfiguration deviceConfiguration) {
		this.deviceConfiguration = deviceConfiguration;

		if (ttnMqttClient != null) {
			ttnMqttClient.subscribe(deviceConfiguration.subscriptionChannel(), subsMethod);
		}
	}

	@GetMethod
	public LT100HSensorDataDTO getData() {
		return lastKnownData.get();
	}

	@PostMethod
	public void sendData(String data) {
		if (myWebsocketProtocol != null) {
			myWebsocketProtocol.sendMessage(deviceConfiguration.applicationId(), deviceConfiguration.deviceEUI(), data);
		}

		if (ttnMqttClient != null) {
			ttnMqttClient.publish(deviceConfiguration.subscriptionChannel(), data);
		}
	}

	@Override
	public String getId() {
		return deviceConfiguration.id();
	}

}
