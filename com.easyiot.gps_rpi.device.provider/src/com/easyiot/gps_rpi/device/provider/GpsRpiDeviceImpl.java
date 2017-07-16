package com.easyiot.gps_rpi.device.provider;

import java.util.concurrent.atomic.AtomicReference;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.service.metatype.annotations.Designate;

import com.easyiot.base.api.Device;
import com.easyiot.gps_rpi.device.api.capability.GpsRpiDeviceCapability.ProvideGpsRpiDevice;
import com.easyiot.gps_rpi.device.api.dto.GpsRpiDeviceDataDTO;
import com.easyiot.gps_rpi.device.provider.configuration.GpsRpiDeviceConfiguration;
import com.easyiot.gps_rpi.device.provider.converter.TtnDataConverter;
import com.easyiot.ttn_mqtt.protocol.api.TtnMqttMessageListener;
import com.easyiot.ttn_mqtt.protocol.api.TtnMqttProtocol;

import osgi.enroute.dto.api.DTOs;

@ProvideGpsRpiDevice
@Designate(ocd = GpsRpiDeviceConfiguration.class, factory = true)
@Component(name = "com.easyiot.gps_rpi.device")
public class GpsRpiDeviceImpl implements Device {
	private GpsRpiDeviceConfiguration deviceConfiguration;
	private AtomicReference<GpsRpiDeviceDataDTO> lastKnownData = new AtomicReference<GpsRpiDeviceDataDTO>(
			new GpsRpiDeviceDataDTO());
	private TtnDataConverter myTtnDataConverter = new TtnDataConverter();
	@Reference
	private DTOs dtoConverter;

	// lambda that is subscribed to MQTT
	private TtnMqttMessageListener subsMethod = (metadata) -> {
		GpsRpiDeviceDataDTO newData = myTtnDataConverter.convert(metadata, dtoConverter);
		System.out.println(String.format("Network name: %s, Gateway EUI: %s", "TTN",
				metadata.gwts.get(0).gateway_eui));
		lastKnownData.set(newData);
	};

	// Bind mqttClient
	@Reference(name = "mqttProtocolReference", cardinality = ReferenceCardinality.OPTIONAL, policyOption = ReferencePolicyOption.GREEDY)
	private TtnMqttProtocol ttnMqttClient;

	@Activate
	public void activate(GpsRpiDeviceConfiguration deviceConfiguration) {
		this.deviceConfiguration = deviceConfiguration;

		if (ttnMqttClient != null && deviceConfiguration.mqttProtocolReference_target() != null) {
			ttnMqttClient.subscribe(deviceConfiguration.subscriptionChannel(), subsMethod);
		}
	}

	@GetMethod
	public GpsRpiDeviceDataDTO getData() {
		return lastKnownData.get();
	}

	@PostMethod
	public void sendData(String data) {
		if (ttnMqttClient != null) {
			ttnMqttClient.publish(deviceConfiguration.subscriptionChannel(), data);
		}
	}

	@Override
	public String getId() {
		return deviceConfiguration.id();
	}

}
