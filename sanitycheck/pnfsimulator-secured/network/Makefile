all: start

.PHONY: start

start-ves-pnfsim-network:
	@echo "##### Creating ves-pnfsim network #####"
	@docker network create ves-pnfsim
	@echo "##### Done #####"

stop-ves-pnfsim-network:
	@echo "##### Removing ves-pnfsim network #####"
	@docker network rm ves-pnfsim
	@echo "##### Done #####"
