package com.easyiot.development.board1.device.provider;

import java.util.concurrent.atomic.AtomicReference;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.service.metatype.annotations.Designate;

import com.easyiot.auslora_websocket.protocol.api.AusloraWebsocketListener;
import com.easyiot.auslora_websocket.protocol.api.AusloraWebsocketProtocol;
import com.easyiot.auslora_websocket.protocol.api.dto.AusloraMetadataDTO;
import com.easyiot.base.api.Device;
import com.easyiot.development.board1.device.api.dto.DevelopmentBoard1DeviceDataDTO;
import com.easyiot.development.board1.device.capability.DevelopmentBoard1DeviceCapability.ProvideDevelopmentBoard1Device;
import com.easyiot.development.board1.device.provider.configuration.DevelopmentBoard1Configuration;
import com.easyiot.development.board1.device.provider.converter.AusloraDataConverter;
import com.easyiot.development.board1.device.provider.converter.TtnDataConverter;
import com.easyiot.ttn_mqtt.protocol.api.TtnMqttMessageListener;
import com.easyiot.ttn_mqtt.protocol.api.TtnMqttProtocol;

import osgi.enroute.dto.api.DTOs;

@ProvideDevelopmentBoard1Device(version = "1.0.0")
@Component(name = "com.easyiot.development.board1.device", configurationPolicy = ConfigurationPolicy.REQUIRE)
@Designate(ocd = DevelopmentBoard1Configuration.class, factory = true)
public class DevelopmentBoard1Impl implements Device, AusloraWebsocketListener {
	private static final String NOT_AVAILABLE = "N/A";
	private AtomicReference<DevelopmentBoard1DeviceDataDTO> lastKnownData = new AtomicReference<DevelopmentBoard1DeviceDataDTO>(
			new DevelopmentBoard1DeviceDataDTO());
	private DevelopmentBoard1Configuration deviceConfiguration;
	private AusloraDataConverter myAusloraDataConverter = new AusloraDataConverter();
	private TtnDataConverter myTtnDataConverter = new TtnDataConverter();
	private AusloraWebsocketProtocol myWebsocketProtocol;

	@Reference
	private DTOs dtoConverter;

	@Override
	public void processMessage(AusloraMetadataDTO ausloraData) {
		DevelopmentBoard1DeviceDataDTO sensorData = myAusloraDataConverter.convert(ausloraData, dtoConverter);
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
		DevelopmentBoard1DeviceDataDTO newData = myTtnDataConverter.convert(metadata, dtoConverter);
		System.out.println(String.format("Network name: %s, Gateway EUI: %s Device EUI: %s", "TTN",
				metadata.gwts.get(0).gateway_eui, deviceConfiguration.deviceEUI()));
		lastKnownData.set(newData);
	};

	// Bind mqttClient
	@Reference(name = "mqttProtocolReference", cardinality = ReferenceCardinality.OPTIONAL, policyOption = ReferencePolicyOption.GREEDY)
	private TtnMqttProtocol ttnMqttClient;

	@Activate
	public void activate(DevelopmentBoard1Configuration conf) {
		this.deviceConfiguration = conf;
		if (ttnMqttClient != null) {
			ttnMqttClient.subscribe(deviceConfiguration.subscriptionChannel(), subsMethod);
		}
	}

	@GetMethod
	public DevelopmentBoard1DeviceDataDTO getData() {
		createRandomData();
		return lastKnownData.get();
	}

	@PostMethod
	public void sendData(String data) {
		if (myWebsocketProtocol != null) {
			myWebsocketProtocol.sendMessage(deviceConfiguration.applicationId(), deviceConfiguration.deviceEUI(), data);
		}
	}

	// Creates a random data if device configuration defines either of
	// subscription or publish channel as "N/A"
	private void createRandomData() {
		DevelopmentBoard1DeviceDataDTO newData = new DevelopmentBoard1DeviceDataDTO();
		if (NOT_AVAILABLE.equals(deviceConfiguration.subscriptionChannel())
				|| NOT_AVAILABLE.equals(deviceConfiguration.publishChannel())) {
			newData.temp = (int) (120 * Math.random() - 20);
			newData.lat += (.01 * (Math.random() - 0.5));
			newData.lon += (.01 * (Math.random() - 0.5));
			lastKnownData.set(newData);
		}
	}

	@Override
	public String getId() {
		return deviceConfiguration.id();
	}

}
