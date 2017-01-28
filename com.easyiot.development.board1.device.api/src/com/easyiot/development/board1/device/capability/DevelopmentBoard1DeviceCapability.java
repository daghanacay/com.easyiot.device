package com.easyiot.development.board1.device.capability;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.easyiot.base.capability.EasyiotNamespace;

import aQute.bnd.annotation.headers.ProvideCapability;
import aQute.bnd.annotation.headers.RequireCapability;

public interface DevelopmentBoard1DeviceCapability {
	public static final String DEVELOPMENT_BOARD1_DEVICE = "DevelopmentBoard1_device";

	@ProvideCapability(ns = EasyiotNamespace.NS, name = DEVELOPMENT_BOARD1_DEVICE)
	@Retention(RetentionPolicy.CLASS)
	public @interface ProvideDevelopmentBoard1Device {
		String version() default "1.0.0";
	}

	@RequireCapability(ns = EasyiotNamespace.NS, filter = "(&(" + EasyiotNamespace.NS + "=" + DEVELOPMENT_BOARD1_DEVICE
			+ ")${frange;${version}})")
	@Retention(RetentionPolicy.CLASS)
	public @interface RequireDevelopmentBoard1Device {
		/**
		 * Version of the required lora bundle
		 * 
		 * @return
		 */
		String version() default "1.0.0";
	}

}
