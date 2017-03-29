package com.easyiot.ZJ_580.bt_printer.device.configuration;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Bluetooth enabled device configuration")
public @interface ZJ_580DeviceConfiguration {

	/**
	 * Id of the device instance
	 */
	@AttributeDefinition(name = "Instance ID", description = "Bluetooth device instance ID", required = true)
	public String id() default "bluetooth.device.1";
	
	@AttributeDefinition(name = "SPP Service Id", description = "Gives the SPP service number for this device.", required = true)
	String sppServiceNumber() default "";
	
	@AttributeDefinition(name = "Bluetooth protocol dependency", description = "Defines the ID of the underlying bluetooth protocol instance. Optional if there is only one bluetooth protocol instance.", required = false)
	String bluetoothProtocolReference_target();

}
