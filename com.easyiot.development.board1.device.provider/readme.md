# 

${Bundle-Description}

## Example

## Configuration

can connect to device through multiple protocols:

```
{
     "service.factoryPid":"com.easyiot.LT100H.device",
     "service.pid":"apid.sensor0",
     "id":"device.LT100H.1",
     "ausloraServerId":"real.auslora.wss",
     "applicationId":"BE01000C",
  	 "securityToken":"qCbvxPgM0cg0lKb80vkoug",
  	 "deviceEUI":"000DB5311773356B"
  }
```

where protocols are specified as

* Auslora wepsocket protocol
```
{
     "service.factoryPid":"com.easyiot.auslora-websocket.protocol",
     "service.pid":"auslora.wss.pid",
     "id":"real.auslora.wss",
     "host":"nwk.auslora.com.au",
     "port":"80"
  }
  ```
  
* TTN through mqtt protocol
  ```
  {
     "service.factoryPid":"com.easyiot.ttn-mqtt.protocol",
     "service.pid":"mqtt.pid",
     "id":"ttn.staging.mqtt",
     "host":"staging.thethingsnetwork.org",
     "port":"1883",
     "username":"70B3D57ED0000185",
     "userPassword":"vjGkwZGzSGSkhzMawoXv59f84oGjeYHX0mBbC1c7Yq0="
  }  
  ``` 

Please see LT100HDeviceConfiguration.java for more details 
	
## References

See
 
* com.easyiot.ttn-mqtt.protocol
* com.easyiot.auslora-websocket.protocol