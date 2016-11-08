package com.easyiot.ZJ_580.bt_printer.device.provider;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;

import com.easyiot.ZJ_580.bt_printer.device.api.capability.BluetoothDeviceCapability.ProvideZJ_580Device_v1_0_0;
import com.easyiot.ZJ_580.bt_printer.device.configuration.BluetoothDeviceConfiguration;
import com.easyiot.base.api.Device;
import com.easyiot.bluetooth.protocol.api.BluetoothProtocol;
import com.easyiot.com.easyiot.ZJ_580.bt_printer.device.api.dto.SensorData;

/**
 * A sample implementation of a Bluetooth device.
 */
@ProvideZJ_580Device_v1_0_0
@Designate(ocd = BluetoothDeviceConfiguration.class, factory = true)
@Component(name = "com.easyiot.bluetooth.device", configurationPolicy = ConfigurationPolicy.REQUIRE)
public class BluetoothDeviceImpl implements Device {
	private BluetoothDeviceConfiguration deviceConfiguration;

	@Reference(name = "bluetoothProtocolReference")
	BluetoothProtocol bluetoothPotocol;

	@PostMethod
	public String sendDataThroughSPP(SensorData msg) {
		bluetoothPotocol.sendDataThroughSPP(deviceConfiguration.sppServiceNumber(), msg.message);
		return "Success";
	}

	@Activate
	public void activate(BluetoothDeviceConfiguration config) {
		this.deviceConfiguration = config;
	}

	@Override
	public String getId() {
		return deviceConfiguration.id();
	}

}
