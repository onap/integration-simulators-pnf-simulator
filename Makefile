create-sonarqube:
	@echo "##### Create SonarQube #####"
	docker run -d --name sonarqube -p 9000:9000 -p 9092:9092 sonarqube
	@echo "************************************************"
	@echo "*** Follow instructions from README_SONAR.md ***"
	@echo "************************************************"
	@echo "##### DONE #####"

start-sonarqube:
	@echo "##### Start SonarQube #####"
	docker start sonarqube
	@echo "************************************************"
	@echo "*** Follow instructions from README_SONAR.md ***"
	@echo "************************************************"
	@echo "##### DONE #####"

stop-sonarqube:
	@echo "##### Stop SonarQube #####"
	docker stop sonarqube
	@echo "##### DONE #####"

