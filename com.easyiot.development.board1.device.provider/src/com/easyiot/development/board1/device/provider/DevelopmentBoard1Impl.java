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
import com.easyiot.base.api.Device;
import com.easyiot.development.board1.device.api.dto.DevelopmentBoard1DeviceDataDTO;
import com.easyiot.development.board1.device.capability.DevelopmentBoard1DeviceCapability.ProvideDevelopmentBoard1Device_v1_0_0;
import com.easyiot.development.board1.device.provider.configuration.DevelopmentBoard1Configuration;
import com.easyiot.development.board1.device.provider.converter.AusloraDataConverter;
import com.easyiot.development.board1.device.provider.converter.TtnDataConverter;
import com.easyiot.ttn_mqtt.protocol.api.TtnMqttMessageListener;
import com.easyiot.ttn_mqtt.protocol.api.TtnMqttProtocol;

import osgi.enroute.dto.api.DTOs;

@ProvideDevelopmentBoard1Device_v1_0_0
@Component(name = "com.easyiot.development.board1.device", configurationPolicy = ConfigurationPolicy.REQUIRE)
@Designate(ocd = DevelopmentBoard1Configuration.class, factory = true)
public class DevelopmentBoard1Impl implements Device {
	private static final String NOT_AVAILABLE = "N/A";
	private AtomicReference<DevelopmentBoard1DeviceDataDTO> lastKnownData = new AtomicReference<DevelopmentBoard1DeviceDataDTO>(
			new DevelopmentBoard1DeviceDataDTO());
	private DevelopmentBoard1Configuration deviceConfiguration;
	private AusloraDataConverter myAusloraDataConverter = new AusloraDataConverter();
	private TtnDataConverter myTtnDataConverter = new TtnDataConverter();

	@Reference
	private DTOs dtoConverter;

	private AusloraWebsocketListener callback = (metadata) -> {
		DevelopmentBoard1DeviceDataDTO sensorData = myAusloraDataConverter.convert(metadata, dtoConverter);
		lastKnownData.set(sensorData);
	};

	@Reference(name = "auslorawsProtocolReference", cardinality = ReferenceCardinality.OPTIONAL, policyOption = ReferencePolicyOption.GREEDY)
	private AusloraWebsocketProtocol auslorawsClient;

	// lambda that is subscribed to MQTT
	private TtnMqttMessageListener subsMethod = (metadata) -> {
		DevelopmentBoard1DeviceDataDTO newData = myTtnDataConverter.convert(metadata, dtoConverter);
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

		if (auslorawsClient != null) {
			String deviceChannel = String.format("app?id=%s&token=%s", deviceConfiguration.applicationId(),
					deviceConfiguration.securityToken());
			auslorawsClient.connect(deviceChannel, deviceConfiguration.deviceEUI(), callback);
		}
	}

	@GetMethod
	public DevelopmentBoard1DeviceDataDTO getData() {
		createRandomData();
		return lastKnownData.get();
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
