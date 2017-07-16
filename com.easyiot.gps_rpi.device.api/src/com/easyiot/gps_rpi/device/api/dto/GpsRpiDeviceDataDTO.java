package com.easyiot.gps_rpi.device.api.dto;

import org.osgi.dto.DTO;

public class GpsRpiDeviceDataDTO extends DTO {

	// in (E or W)dddmm.mmmm format
	public String longitude = "E14456.4799";

	// in (N or S)ddmm.mmmm
	public String latitude = "S3750.3815";
	
	public GpsRpiDeviceMetaDataDTO metadata;

}
