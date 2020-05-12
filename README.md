# PNF Simulator

## General description

More information about the project and all its functionalities you can find under the wiki page:
https://wiki.onap.org/display/DW/PNF+Simulator

Project consists of three submodules:
1. netconfsimulator
2. pnfsimulator
3. simulator-cli

Detailed information about submodules can be found in ```README.md``` in their directories.

## Building Docker images
```
mvn clean install -P docker
```
## Run integration tests
```
mvn verify -P integration
```

##Sanity check

See README.md in sanitycheck directory

## Sonar

https://sonarcloud.io/dashboard?id=onap_integration-pnf-simulator

## Jenkins Jobs Configuration

https://gerrit.onap.org/r/gitweb?p=ci-management.git;a=blob;f=jjb/integration/simulators/integration-pnf-simulator.yaml;h=df000a15db1c4774e05b98f26a960d4ce88dbde0;hb=HEAD

## Jenkins Jobs

https://jenkins.onap.org/view/integration/search/?q=integration-pnf-simulator&Jenkins-Crumb=01c2163ed26687ffc4877fedc6bdc9a7eb3d108abd3144d016a6cd7a84e24bb2

## Nexus repository
Images are available at Nexus:
1. netconfsimulator
    https://nexus3.onap.org/#browse/search=keyword%3Dnetconfsimulator
    https://nexus3.onap.org/#browse/search=keyword%3Dnetopeer
2. pnfsimulator
    https://nexus3.onap.org/#browse/search=keyword%3Dpnfsimulator
    
## Release notes
Attention: Always update a release-notes.rst when you add a new functionality or fix a bug. Thanks!
