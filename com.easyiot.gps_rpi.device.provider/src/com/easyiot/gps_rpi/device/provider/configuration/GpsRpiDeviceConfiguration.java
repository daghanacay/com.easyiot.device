package com.easyiot.gps_rpi.device.provider.configuration;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "LT100H device configuration")
public @interface GpsRpiDeviceConfiguration {

	/**
	 * Id of the device instance
	 */
	@AttributeDefinition(name = "Instance ID", description = "Auslora device instance ID", required = true)
	public String id() default "r.device.1";

	// BELOW IS NEEDED FOR REGISTERING TO TTN
	@AttributeDefinition(name = "Subscription Channel", description = "Device subscription channel to read data. Follows mqtt syntax.")
	public String subscriptionChannel() default "70B3D57ED0000185/devices/0000000001020304/up";

	@AttributeDefinition(name = "Publish Channel", description = "Device publish channel to write data. Follows mqtt syntax.")
	public String publishChannel() default "70B3D57ED0000185/devices/0000000001020304/up";

	@AttributeDefinition(name = "MQTT Protocol dependency", description = "Defines the ID of the underlying MQTT protocol instance. Optional if there is only one MQTT protocol instance.", required = false)
	String mqttProtocolReference_target();

}
