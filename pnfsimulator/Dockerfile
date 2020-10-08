FROM docker.io/openjdk:11-jre-slim
COPY --from=python:3.6 / /

ARG VERSION=${version}

ADD target/libs /app/libs
ADD config/pnf_simulator_db.py  /opt/db/config
ADD ./templates /app/templates
ADD ./src/main/resources/application.properties /app/application.properties
ADD target/pnfsimulator-${VERSION}.jar /app/pnf-simulator.jar
CMD apk update
CMD apk add ca-certificates
RUN python -m insatll -r /opt/db/config/requirements.txt
ADD certificates  /usr/local/share/ca-certificates/
RUN update-ca-certificates
CMD if [ -f /app/store/p12.pass ]; then export CLIENT_CERT_PASS=$(cat /app/store/p12.pass); fi \
    && if [ -f /app/store/trust.pass ]; then export TRUST_CERT_PASS=$(cat /app/store/trust.pass); fi \
    && python /opt/db/config/mongo_db_schema_creation.py \
    && java -Dspring.config.location=file:/app/application.properties  -cp /app/libs/*:/app/pnf-simulator.jar org.onap.pnfsimulator.Main \