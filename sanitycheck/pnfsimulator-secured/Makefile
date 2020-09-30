default:
	@echo "There is no default target. Use: make <specific_target>"

start-ejbca: --run-ejbca-container --wait-for-ejbca --configure-ejbca

start-pnfsim-with-certservice-certs: --create-certservice-internal-certs --create-client-volume --run-certservice-and-pnfsim-containers

start-local-secured-ves:
	docker-compose -f docker-compose-ves.yml up

start-pnfsim-with-certman-certs:
	docker-compose -f docker-compose-certman.yml up

clean-pnfsim-with-certman-setup:
	docker-compose -f docker-compose-certman.yml down

clean-pnfsim-with-certservice-setup: --clean-certservice-internal-certs --clean-client-volume
	docker rm -f oomcert-ejbca || true
	docker-compose -f docker-compose-certservice.yml down
	docker-compose -f docker-compose-ves.yml down

--run-ejbca-container:
	docker run \
		-d \
		--rm \
		--name oomcert-ejbca \
		--hostname cahostname \
 		-p 80:8080 \
 		-p 443:8443 \
 		--volume `pwd`/certservice/ejbca-resources/ejbca-configuration.sh:/opt/primekey/scripts/ejbca-configuration.sh \
 		--health-cmd "curl -kI https://localhost:8443/ejbca/publicweb/healthcheck/ejbcahealth" \
 		--health-interval 10s \
 		--health-timeout 3s \
 		--health-retries 15 \
		primekey/ejbca-ce:6.15.2.5

--configure-ejbca:
	docker exec oomcert-ejbca /opt/primekey/scripts/ejbca-configuration.sh

--create-client-volume:
	mkdir -p ./certservice/client-resources/client-volume -m 777

--run-certservice-and-pnfsim-containers:
	docker-compose -f docker-compose-certservice.yml up

--create-certservice-internal-certs:
	make -C certservice/certs all

--clean-certservice-internal-certs:
	make -C certservice/certs clear

--clean-client-volume:
	rm -rf certservice/client-resources/client-volume

--wait-for-ejbca:
	@echo 'Waiting for EJBCA...'
	until docker container inspect oomcert-ejbca | grep '"Status": "healthy"'; do sleep 3; done
