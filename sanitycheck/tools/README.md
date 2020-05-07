VES with DMAAP simulator
------------------------

### Description

Docker-compose prepares PNF simulator container for HTTPS communication.

certs-init container works with external AAF on cloud. Due to that fact it must have set correct IPs to workers that
has access to AAF. In docker-compose fields with mentioned IPs are:
* aaf-locate.onap
* aaf-cm.onap
* aaf-service.onap 

When docker-compose starts certs-init container fills connected volume with certificates, truststores, keystores, passwords etc.
Next pnf-simulator container starts and connects to the same volume. On startup it should read password values from 
proper files and set them in system environment variables. With these variables application is ready to work on HTTPS.

### Start
```
docker-compose up
```

### Healthcheck
To check whether PNF works correctly see ``sanitycheck/README.md``.