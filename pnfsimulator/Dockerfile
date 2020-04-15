FROM docker.io/openjdk:8-jre-alpine

ARG VERSION=${version}

ADD target/libs /app/libs
ADD target/pnfsimulator-${VERSION}.jar /app/pnf-simulator.jar
CMD apk update
CMD apk add ca-certificates
ADD certificates  /usr/local/share/ca-certificates/
RUN update-ca-certificates
CMD java -Dspring.config.location=file:/app/application.properties  -cp /app/libs/*:/app/pnf-simulator.jar org.onap.pnfsimulator.Main
