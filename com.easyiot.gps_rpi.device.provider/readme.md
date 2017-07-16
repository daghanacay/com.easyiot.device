# 

${Bundle-Description}

## Example

## Configuration

can connect to device through multiple protocols:
```
{
     "service.factoryPid":"com.easyiot.development.board1.device",
     "service.pid":"apid.sensor1",
     "id":"device.gps.rpi.1",
     "mqttProtocolReference.target" : "(id=ttn.staging.mqtt)",
     "subscriptionChannel":"70B3D57ED0000185/devices/0000000001774B22/up",
     "publishChannel":"70B3D57ED0000185/devices/0000000001774B22/up"
  }
```
where protocol is specified as

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
	
## References

See
 
* com.easyiot.ttn-mqtt.protocol