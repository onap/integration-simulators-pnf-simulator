FROM docker.io/openjdk:11-jre-slim

ARG VERSION=${version}

ADD target/libs /app/libs
ADD ./templates /app/templates
ADD ./src/main/resources/application.properties /app/application.properties
ADD target/pnfsimulator-${VERSION}.jar /app/pnf-simulator.jar
CMD apk update
CMD apk add ca-certificates
ADD certificates  /usr/local/share/ca-certificates/
RUN update-ca-certificates
CMD java -Dspring.config.location=file:/app/application.properties  -cp /app/libs/*:/app/pnf-simulator.jar org.onap.pnfsimulator.Main
