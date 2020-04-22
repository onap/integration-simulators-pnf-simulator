all: start

.PHONY: start

build:
	@echo "##### build (dmaap sim) #####"
	make -C dmaap-simulator build
	@echo "##### DONE #####"

start: build
	@echo "##### start (dmaap sim,ves,pnf sim) #####"
	make -C ves start
	make -C ../pnfsimulator start
	@echo "##### DONE #####"

stop:
	@echo "##### Stop (dmaap sim,ves,pnf sim)  #####"
	make -C ves stop
	make -C ../pnfsimulator stop
	@echo "##### DONE #####"

generate-event:
	@echo "##### Trigger PNF Simulator to generate event #####"
	curl -X  POST http://localhost:5000/simulator/event -d @events/eventToVes.json --header "Content-Type: application/json"
	@echo "\n##### DONE #####"

reconfigure-ves-url:
	@echo "##### Change VES address configuration in PNF Simulator #####"
	curl -X  PUT http://localhost:5000/simulator/config -d @events/vesAddressConfiguration.json --header "Content-Type: application/json"
	@echo "\n##### DONE #####"

generate-multiple-events: reconfigure-ves-url
	@echo "\n##### Trigger PNF Simulator to generate multiple events #####"
	curl -X  POST http://localhost:5000/simulator/start -d @events/fewEventsToVes.json --header "Content-Type: application/json"
	@echo "\n##### DONE #####"

check-dmaap:
	@echo "##### Check dmaap simulator for collected events #####"
	make -C dmaap-simulator get-data
	@echo "\n##### DONE #####"