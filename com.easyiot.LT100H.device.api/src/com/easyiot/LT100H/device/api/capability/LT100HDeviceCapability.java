package com.easyiot.LT100H.device.api.capability;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.easyiot.base.capability.EasyiotNamespace;

import aQute.bnd.annotation.headers.ProvideCapability;
import aQute.bnd.annotation.headers.RequireCapability;

public interface LT100HDeviceCapability {

	public static final String LT100H_DEVICE = "lth100h_device";

	@ProvideCapability(ns = EasyiotNamespace.NS, name = LT100H_DEVICE)
	@Retention(RetentionPolicy.CLASS)
	public @interface ProvideLT100HDevice {
		String version() default "1.0.0";
	}

	@RequireCapability(ns = EasyiotNamespace.NS, filter = "(&(" + EasyiotNamespace.NS + "=" + LT100H_DEVICE
			+ ")${frange;${version}})")
	@Retention(RetentionPolicy.CLASS)
	public @interface RequireLT100HDevice {
		/**
		 * Version of the required auslora bundle
		 * 
		 * @return
		 */
		String version() default "1.0.0";
	}

}
