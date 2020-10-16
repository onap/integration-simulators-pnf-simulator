default:
	@echo "There is no default target. Use: make <specific_target>"

### CertMan
start-pnfsim-with-certman-certs:
	docker-compose -f docker-compose-certman.yml up

clean-pnfsim-with-certman-setup:
	docker-compose -f docker-compose-certman.yml down


### CertService
setup-env-with-certservice-setup: --start-certservice-and-ejbca --run-certservice-clients --start-local-secured-ves

start-pnfsim-with-certservice-setup:
	docker-compose -f docker-compose-pnfsim.yml up

restart-pnfsim-with-certservice-setup: --clean-pnfsim start-pnfsim-with-certservice-setup

clean-pnfsim-with-certservice-setup: --clean-pnfsim --clean-env


--start-certservice-and-ejbca: --create-certservice-internal-certs --start-certservice-ejbca-containers --configure-ejbca

--start-certservice-ejbca-containers:
	docker-compose -f docker-compose-backend.yml up -d

--create-certservice-internal-certs:
	make -C certservice/certs all

--configure-ejbca: --wait-for-ejbca --run-ejbca-script

--wait-for-ejbca:
	@echo 'Waiting for EJBCA... It may take a minute or two'
	until docker container inspect oomcert-ejbca | grep '"Status": "healthy"'; do sleep 3; done

--run-ejbca-script:
	docker exec oomcert-ejbca /opt/primekey/scripts/ejbca-configuration.sh

--run-certservice-clients: --create-client-volumes
	docker-compose -f docker-compose-client-certs.yml up -d
	@echo 'Waiting for client certifiactes...'
	@until ls -1 ./certservice/client-resources/client-volume | grep "store" 1>/dev/null; do sleep 3; done
	@until ls -1 ./certservice/client-resources/client-volume-for-ves | grep "store" 1>/dev/null; do sleep 3; done

--create-client-volumes:
	mkdir -p ./certservice/client-resources/client-volume-for-ves -m 777
	mkdir -p ./certservice/client-resources/client-volume -m 777

--start-local-secured-ves:
	docker-compose -f docker-compose-ves.yml up

--clean-pnfsim:
	docker-compose -f docker-compose-pnfsim.yml down
	rm -rf ./certservice/client-resources/client-volume/cert.p12 || true
	rm -rf ./certservice/client-resources/client-volume/trust.jks || true

--clean-env:
	docker-compose -f docker-compose-ves.yml down
	docker-compose -f docker-compose-client-certs.yml down
	rm -rf ./certservice/client-resources/client-volume-for-ves || true
	rm -rf ./certservice/client-resources/client-volume || true
	docker-compose -f docker-compose-backend.yml down
	make -C certservice/certs clear
