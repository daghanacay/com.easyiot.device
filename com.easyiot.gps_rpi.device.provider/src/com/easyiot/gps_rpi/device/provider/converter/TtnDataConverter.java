package com.easyiot.gps_rpi.device.provider.converter;

import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

import com.easyiot.gps_rpi.device.api.dto.GpsRpiDeviceDataDTO;
import com.easyiot.gps_rpi.device.api.dto.GpsRpiDeviceMetaDataDTO;
import com.easyiot.ttn_mqtt.protocol.api.dto.TtnMetaDataDTO;

import osgi.enroute.dto.api.DTOs;

public class TtnDataConverter {
	@SuppressWarnings("unused")
	private DTOs dtoConverter;

	public GpsRpiDeviceDataDTO convert(TtnMetaDataDTO metadata, DTOs dtoConverter) {
		this.dtoConverter = dtoConverter;
		return parseSensorData(metadata);
	}

	private GpsRpiDeviceDataDTO parseSensorData(TtnMetaDataDTO metadata) {
		// Decode sensor data
		metadata.payload = new String(DatatypeConverter.parseHexBinary(metadata.payload));
		// Parse data
		String[] values = metadata.payload.split(",");
		GpsRpiDeviceDataDTO returnVal = null;
		if (values.length == 7) {
			returnVal = new GpsRpiDeviceDataDTO();
			returnVal.longitude = values[0];
			returnVal.latitude = values[1];
		}
		returnVal.metadata = parseMetadata(metadata);

		return returnVal;
	}

	private GpsRpiDeviceMetaDataDTO parseMetadata(TtnMetaDataDTO metadata) {
		GpsRpiDeviceMetaDataDTO newMeta = new GpsRpiDeviceMetaDataDTO();
		// TODO newMeta.averageReceptionPeriod
		// TODO newMeta.calculatedAltitude
		// TODO newMeta.calculatedLatitude
		newMeta.gateway_eui = metadata.gwts.stream().map((mData) -> mData.gateway_eui).collect(Collectors.joining(","));
		newMeta.isRoaming = false;
		newMeta.networkName = "TTN";
		newMeta.timeStamp = metadata.gwts.get(0).gateway_timestamp;
		return newMeta;
	}

}
