FROM eclipse-temurin:11-jre-alpine
COPY --from=python:3.6 / /

ARG VERSION=${version}

ADD target/libs /app/libs
ADD config /opt/db/config
ADD ./templates /app/templates
ADD ./src/main/resources/application.properties /app/application.properties
ADD target/pnfsimulator-${VERSION}.jar /app/pnf-simulator.jar

RUN python -m pip install -r /opt/db/config/requirements.txt
ADD certificates  /usr/local/share/ca-certificates/
RUN update-ca-certificates
CMD python /opt/db/config/mongo_db_schema_creation.py \
    && if [ -f /app/store/trust.pass ]; then cp /app/store/trust.pass /app/store/truststore.pass; fi \
    && java -Dspring.config.location=file:/app/application.properties  -cp /app/libs/*:/app/pnf-simulator.jar org.onap.pnfsimulator.Main \
