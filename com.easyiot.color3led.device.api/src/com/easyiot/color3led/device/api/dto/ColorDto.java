package com.easyiot.color3led.device.api.dto;

import org.osgi.dto.DTO;

import com.easyiot.color3led.device.api.capability.Color3LedCapability.RequireColor3LedDevice;

@RequireColor3LedDevice
public class ColorDto extends DTO {
	public String blueValue = ColorDtoFactory.LOW_VALUE;
	public String greenValue = ColorDtoFactory.LOW_VALUE;
	public String redValue = ColorDtoFactory.LOW_VALUE;
}
