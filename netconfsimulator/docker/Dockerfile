FROM docker.io/openjdk:8-jre-alpine

ARG VERSION=${version}

ADD libs /app/libs
ADD netconfsimulator-${VERSION}.jar /app/netconf-simulator.jar
CMD java -cp /app/libs/*:/app/netconf-simulator.jar org.onap.netconfsimulator.Main
