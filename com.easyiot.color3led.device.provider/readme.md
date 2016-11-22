# 

${Bundle-Description}

## Example

## Configuration

Device can be configured to be connected to any pin

```
{
    "service.factoryPid" : "com.easyiot.color3led.device",
    "service.pid" : "color3led",
    "id":"discoDevice.1",
    "gpioProtocolReference.target" : "(id=gpio1)",
    "redPin" : "pin23",
    "greenPin" : "pin24",
    "bluePin" : "pin25"
  }
```  
You need to define at least one  GPIO protocol for this device to work.
```
  {
    "service.factoryPid" : "com.easyiot.gpio.protocol",
    "service.pid" : "gpioProtocol",
    "id":"gpio1"
  }
```

See Color3LedConfiguration
	
## References

See 
* com.easyiot.gpio.protocol