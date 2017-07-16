package com.easyiot.gps_rpi.device.api.capability;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.easyiot.base.capability.EasyiotNamespace;

import aQute.bnd.annotation.headers.ProvideCapability;
import aQute.bnd.annotation.headers.RequireCapability;

public interface GpsRpiDeviceCapability {

	public static final String GPS_RPI_DEVICE = "gpsRpi_device";

	@ProvideCapability(ns = EasyiotNamespace.NS, name = GPS_RPI_DEVICE)
	@Retention(RetentionPolicy.CLASS)
	public @interface ProvideGpsRpiDevice {
		String version() default "1.0.0";
	}

	@RequireCapability(ns = EasyiotNamespace.NS, filter = "(&(" + EasyiotNamespace.NS + "=" + GPS_RPI_DEVICE
			+ ")${frange;${version}})")
	@Retention(RetentionPolicy.CLASS)
	public @interface RequireGpsRpiDevice {
		/**
		 * Version of the required auslora bundle
		 * 
		 * @return
		 */
		String version() default "1.0.0";
	}

}
