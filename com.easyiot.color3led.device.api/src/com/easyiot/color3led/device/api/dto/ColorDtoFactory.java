package com.easyiot.color3led.device.api.dto;

public class ColorDtoFactory {
	public static String LOW_VALUE = "LOW";
	public static String HIGH_VALUE = "HIGH";

	public static final ColorDto NO_COLOR = new ColorDto();
	static {
		NO_COLOR.blueValue = LOW_VALUE;
		NO_COLOR.greenValue = LOW_VALUE;
		NO_COLOR.redValue = LOW_VALUE;
	}

	public static final ColorDto RED_COLOR = new ColorDto();
	static {
		RED_COLOR.blueValue = LOW_VALUE;
		RED_COLOR.greenValue = LOW_VALUE;
		RED_COLOR.redValue = HIGH_VALUE;
	}

	public static final ColorDto GREEN_COLOR = new ColorDto();
	static {
		GREEN_COLOR.blueValue = LOW_VALUE;
		GREEN_COLOR.greenValue = HIGH_VALUE;
		GREEN_COLOR.redValue = LOW_VALUE;
	}

	public static final ColorDto BLUE_COLOR = new ColorDto();
	static {
		BLUE_COLOR.blueValue = HIGH_VALUE;
		BLUE_COLOR.greenValue = LOW_VALUE;
		BLUE_COLOR.redValue = LOW_VALUE;
	}
}
