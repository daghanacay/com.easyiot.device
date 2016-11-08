package com.easyiot.com.easyiot.ZJ_580.bt_printer.device.api.dto;

import org.osgi.dto.DTO;

import com.easyiot.ZJ_580.bt_printer.device.api.capability.BluetoothDeviceCapability.RequireZJ_580Device;

/**
 * Input data to be send to sensor
 * 
 * @author daghan
 *
 */
@RequireZJ_580Device
public class ZJ_580Data extends DTO {
	public String message = "default message";
}
