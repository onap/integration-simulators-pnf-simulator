default:
	@echo "There is no default target. Use: make <specific_target>"

build-http-server:
	@echo "##### Build http server docker image #####"
	docker build . -t onap/org.onap.integration.simulators.httpserver
	@echo "##### DONE #####"

start-http-server:
	@echo "##### Start Http Server #####"
	docker-compose -f docker-compose.yml up
	@echo "##### DONE #####"

stop-http-server:
	@echo "##### Stop Http Server #####"
	docker-compose -f docker-compose.yml down
	rm -rf ~/httpservervolumes/ || true
	@echo "##### DONE #####"
