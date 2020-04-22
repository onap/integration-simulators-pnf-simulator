### Run  test case pnfsimulator -> ves collector -> dmaap simulator

### Prerequisites
* Check your docker network ip:
```
ip a | grep docker0 | grep inet
```
If the IP address is different than (172.17.0.1/16):   
inet 172.17.0.1/16 brd 172.17.255.255 scope global docker0

You have to change the IP address in file events/vesAddressConfiguration.json
```
{
  "vesServerUrl": "http://<IP_Address>:8080/eventListener/v7"
}
```
### 1. Build Projects
```
make start
```
### 2. Send one event
```
make generate-event
```
### 2.1 Check dmaap sim
```
make check-dmaap
```
### 3. Send few events:
### 3.1 Reconfigure ves url
```
make reconfigure-ves-url
```
### 3.2 Send events
```
make generate-multiple-events
```
### 3.3 Check dmaap sim
```
make check-dmaap
```
### 4. Clear environment
```
make stop
```
