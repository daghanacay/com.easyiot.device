# 

${Bundle-Description}

## Example

## Configuration

can connect to device through multiple protocols:
```
{
     "service.factoryPid":"com.easyiot.development.board1.device",
     "service.pid":"apid.sensor1",
     "id":"device.development.board.1",
     "ausloraServerId":"real.auslora.wss",
     "applicationId":"BE01000C",
  	 "securityToken":"qCbvxPgM0cg0lKb80vkoug",
  	 "deviceEUI":"000DB531176F3557",
  	 "mqttProtocolReference.target" : "(id=ttn.staging.mqtt)",
     "subscriptionChannel":"70B3D57ED0000185/devices/0000000001774B22/up",
     "publishChannel":"70B3D57ED0000185/devices/0000000001774B22/up"
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