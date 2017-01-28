package com.easyiot.ZJ_580.bt_printer.device.api.capability;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.easyiot.base.capability.EasyiotNamespace;

import aQute.bnd.annotation.headers.ProvideCapability;
import aQute.bnd.annotation.headers.RequireCapability;

public interface BluetoothDeviceCapability {

	public static final String BLUETOOT_DEVICE = "bluetooth_device";

	@ProvideCapability(ns = EasyiotNamespace.NS, name = BLUETOOT_DEVICE)
	@Retention(RetentionPolicy.CLASS)
	public @interface ProvideZJ_580Device {
		String version() default "1.0.0";
	}

	@RequireCapability(ns = EasyiotNamespace.NS, filter = "(&(" + EasyiotNamespace.NS + "=" + BLUETOOT_DEVICE
			+ ")${frange;${version}})")
	@Retention(RetentionPolicy.CLASS)
	public @interface RequireZJ_580Device {
		/**
		 * Version of the required bluetooth bundle
		 * 
		 * @return
		 */
		String version() default "1.0.0";
	}

}
