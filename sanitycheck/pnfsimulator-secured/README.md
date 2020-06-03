Standalone PNF Simulator configuration for HTTPS communication to VES
------------------------

### General description

Makefile in sanitycheck/pnfsimulator-secured is an interface for deployment of PNF simulator with fetching certs from 
chosen source. 

Makefile offers functionalities that allows to:    

    * Run PNF simulator with fetching certs from Certman
    * Run PNF simulator with fetching certs from Certservice

## Fetching from Certman
### Description

docker-compose-certman.yml prepares PNF simulator container for HTTPS communication with VES.

When docker-compose starts certs-init container fills connected volume with certificates, truststores, keystores, 
passwords etc. Next pnf-simulator container starts and connects to the same volume. On startup it should read password
values from proper files and set them in system environment variables. With these variables and files in volume 
application is ready to work on HTTPS.

### Prerequisites

1. certs-init container works with external AAF on cloud. Due to that fact it must have set correct IPs to workers that
has access to AAF. In docker-compose.yml fields with mentioned IPs are:
    
    * aaf-locate.onap
    * aaf-cm.onap
    * aaf-service.onap

### Start

**ATTENTION** 

Proper IPs to AAF must be set in the docker-compose-certman.yml before start (as described in prerequisites)!

```
make start-pnfsim-with-certman-certs
```

### Send event

**ATTENTION**

``sanitycheck/events/eventToVes.json`` file which is request for sending event to VES must have correct ``vesServerURL`` 
field before sending event. 
IP of ``vesServerURL`` should be the same as given in docker-compose-certman.yml in ``aaf-locate.onap`` field.
To use secured connection remember about setting protocol to https:// and port to proper secured port of VES.

To send event from PNF simulator to VES use this command from ``pnf-simulator/sanitycheck`` directory:

````
make generate-event
````

Sample ``sanitycheck/events/eventToVes.json`` file content is:

```json
{
  "vesServerUrl": "https://10.183.35.177:30417/eventListener/v7",
  "event": {
    "event": {
      "commonEventHeader": {
        "version": "4.0.1",
        "vesEventListenerVersion": "7.0.1",
        "domain": "fault",
        "eventName": "Fault_Vscf:Acs-Ericcson_PilotNumberPoolExhaustion",
        "eventId": "fault0000245",
        "sequence": 1,
        "priority": "High",
        "reportingEntityId": "cc305d54-75b4-431b-adb2-eb6b9e541234",
        "reportingEntityName": "ibcx0001vm002oam001",
        "sourceId": "de305d54-75b4-431b-adb2-eb6b9e546014",
        "sourceName": "scfx0001vm002cap001",
        "nfVendorName": "Ericsson",
        "nfNamingCode": "scfx",
        "nfcNamingCode": "ssc",
        "startEpochMicrosec": 1413378172000000,
        "lastEpochMicrosec": 1413378172000000,
        "timeZoneOffset": "UTC-05:30"
      },
      "faultFields": {
        "faultFieldsVersion": "4.0",
        "alarmCondition": "PilotNumberPoolExhaustion",
        "eventSourceType": "other",
        "specificProblem": "Calls cannot complete - pilot numbers are unavailable",
        "eventSeverity": "CRITICAL",
        "vfStatus": "Active",
        "alarmAdditionalInformation": {
          "PilotNumberPoolSize": "1000"
        }
      }
    }
  }
}
```

### Stop
To remove pnf-simulator containers use:
```
make clean-pnfsim-with-certman-setup
```

## Fetching from Certservice
### Description

Running Makefile with Certservice target will start the following flow:

1. Create certificates that will be used for internal communication between Certservice and Certservice-client. 
    Generated internal certificates should be present in sanitycheck/pnfsimulator-secured/certservice/certs directory.

2. Run docker-compose-certservice.yml that creates:
    
    2.1. Certservice container with mounted previously generated certificates.
    
    2.2. Certservice-client with mounted internal certificates as well. This containers requests Certservice for
        Certificates that will be used by PNF simulator in HTTPS connection. Before closing of container it saves
        these certs in locally mounted volume in 
        sanitycheck/pnfsimulator-secured/certservice/client-resources/client-volume 
    
    2.3. PNF simulator that has mounted certificates from client. Before starting the simulator itself, names of certs 
        files are changed to fit the PNF simulator configuration.
        
### Prerequisites


##### EJBCA configuration
Certservice container will try to connect to EJBCA on docker-compose-certservice.yml startup to fetch certs. 
Whole connection configuration to EJBCA server must be done before start in file 
sanitycheck/pnfsimulator-secured/certservice/certservice-resources/cmpServers.json.

EJBCA might be deployed locally or externally. Described in this README Makefile has a target that runs configured EJBCA
container locally. To run that target use:

```
make start-ejbca
```


Configuration of cmpServers.json for this local EJBCA container should be:
```json
{
  "cmpv2Servers": [
    {
      "caName": "Client",
      "url": "http://<docker0_network_ip>:80/ejbca/publicweb/cmp/cmp",
      "issuerDN": "CN=ManagementCA",
      "caMode": "CLIENT",
      "authentication": {
        "iak": "mypassword",
        "rv": "mypassword"
      }
    },
    {
      "caName": "RA",
      "url": "http://<docker0_network_ip>:80/ejbca/publicweb/cmp/cmpRA",
      "issuerDN": "CN=ManagementCA",
      "caMode": "RA",
      "authentication": {
        "iak": "mypassword",
        "rv": "mypassword"
      }
    }
  ]
}
```
``docker0_network_ip`` might be found when running `ifconfig docker0` next to `inet` field.

### Start

**ATTENTION**

Remember that before starting certservice, the EJBCA server must run, be properly configured and 
sanitycheck/pnfsimulator-secured/certservice/certservice-resources/cmpServers.json must be set correctly. 

For more info read _prerequisites_ section.
 
```
make start-pnfsim-with-certman-certs
```

### Send event

**ATTENTION**

Destination VES collector should use the exact same certificates as generated from EJBCA for PNF simulator for 
successful HTTPS communication. There is local deployment of VES (with DMAAP simulator) to be used from Makefile that 
uses certificates generated by Certservice container. If you want to use that VES deployment enter:

```
make start-local-secured-ves
``` 

``sanitycheck/events/eventToVes.json`` file which is request for sending event to VES must have correct ``vesServerURL`` 
field before sending event. For mentioned local VES it should have value: 
``https://<docker0_network_ip>:8444/eventListener/v7``.
``docker0_network_ip`` might be found when running `ifconfig docker0` next to `inet` field.

 
To send event from PNF simulator to VES use this command from ``pnf-simulator/sanitycheck`` directory:

```
make generate-event
```

### Stop

To clean all generated certificates, remove pnf-simulator, certservice, ejbca and ves containers use:
```
make clean-pnfsim-with-certman-setup
```