package com.easyiot.color3led.device.provider;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.service.metatype.annotations.Designate;

import com.easyiot.base.api.Device;
import com.easyiot.color3led.device.api.capability.Color3LedCapability.ProvideColor3LedDevice;
import com.easyiot.color3led.device.api.dto.ColorDto;
import com.easyiot.color3led.device.api.dto.ColorDtoFactory;
import com.easyiot.color3led.device.configuration.Color3LedConfiguration;
import com.easyiot.gpio.protocol.api.GpioProtocol;
import com.easyiot.gpio.protocol.api.InputOutputEnum;
import com.easyiot.gpio.protocol.api.PinLevelEnum;
import com.easyiot.gpio.protocol.api.PinTypeEnum;

/**
 * 3 color LED device implementation
 * 
 * @author daghan
 *
 */
@ProvideColor3LedDevice(version = "1.0.0")
@Component(name = "com.easyiot.color3led.device", configurationPolicy = ConfigurationPolicy.REQUIRE)
@Designate(ocd = Color3LedConfiguration.class, factory = true)
public class Color3LedDevice implements Device {
	private Color3LedConfiguration configuration;

	@Reference(name = "gpioProtocolReference", cardinality = ReferenceCardinality.MANDATORY, policyOption = ReferencePolicyOption.GREEDY)
	GpioProtocol protocol;

	@Activate
	public void activate(Color3LedConfiguration configuration) {
		this.configuration = configuration;
		setupGpio();
	}

	@Override
	public String getId() {
		return configuration.id();
	}

	private void setupGpio() {
		protocol.forceConfigurePin(configuration.bluePin(), PinTypeEnum.digital, InputOutputEnum.output,
				PinLevelEnum.low);
		protocol.forceConfigurePin(configuration.greenPin(), PinTypeEnum.digital, InputOutputEnum.output,
				PinLevelEnum.low);
		protocol.forceConfigurePin(configuration.redPin(), PinTypeEnum.digital, InputOutputEnum.output,
				PinLevelEnum.low);
	}

	@GetMethod
	public ColorDto getPinValues() {
		ColorDto returnVal = new ColorDto();
		returnVal.blueValue = protocol.readDigitalPinValue(configuration.bluePin()) ? ColorDtoFactory.HIGH_VALUE
				: ColorDtoFactory.LOW_VALUE;
		returnVal.redValue = protocol.readDigitalPinValue(configuration.redPin()) ? ColorDtoFactory.HIGH_VALUE
				: ColorDtoFactory.LOW_VALUE;
		returnVal.greenValue = protocol.readDigitalPinValue(configuration.greenPin()) ? ColorDtoFactory.HIGH_VALUE
				: ColorDtoFactory.LOW_VALUE;
		return returnVal;
	}

	@PostMethod
	public void setPinValues(ColorDto value) {
		protocol.writeDigitalPinValue(configuration.bluePin(),
				value.blueValue.equalsIgnoreCase(ColorDtoFactory.HIGH_VALUE) ? true : false);
		protocol.writeDigitalPinValue(configuration.greenPin(),
				value.greenValue.equalsIgnoreCase(ColorDtoFactory.HIGH_VALUE) ? true : false);
		protocol.writeDigitalPinValue(configuration.redPin(),
				value.redValue.equalsIgnoreCase(ColorDtoFactory.HIGH_VALUE) ? true : false);
	}

}
