# 

${Bundle-Description}

## Example

## Configuration
Define spp serviceId for the device
```
{
    "service.factoryPid" : "com.easyiot.ZJ_580.device",
    "service.pid" : "ZJ_580device",
    "id" : "ZJ_580.1",
    "sppServiceNumber" : "2"
  }
```
  
You should configure at least one bluetooth protocol for this device to work
```
  {
    "service.factoryPid" : "com.easyiot.bluetooth.protocol",
    "service.pid" : "btprotocol",
    "id" : "btprotocol.1",
    "host" : "0F03E0C24A69"
  }
```
		
See ZJ_580DeviceConfiguration.java for more details
	
## References
See 
* com.easyiot.bluetooth.protocol
