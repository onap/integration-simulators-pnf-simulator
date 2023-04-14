# Run SonarQube locally
All instructions based at an article at https://www.vogella.com/tutorials/SonarQube/article.html

## Configure and run SonarQube locally
* Create and run SonarQube container
    ```
        make create-sonarqube
    ```
* Configure SonarQube

    a). Log in at http://localhost:9000
    
     ![alt text](tutorials/sonarqube/login_sonarqube.png "Log in")
        
        Username: admin
        Password: admin
    
    b). Create a new Sonar project.
    
    ![alt text](tutorials/sonarqube/create_new_project_sonarqube.png "Create a new project")
    
    - Set project name
    
    ![alt text](tutorials/sonarqube/create_new_project_step1_sonarqube.png "Set project name")
    
    - Generate token
    
    ![alt text](tutorials/sonarqube/create_new_project_step2_sonarqube.png "Generate token")
    
    You should see
    
    ![alt text](tutorials/sonarqube/create_new_project_step3_sonarqube.png "Generated token")
    
    - Select code language and building technology
    
    ![alt text](tutorials/sonarqube/create_new_project_step4_sonarqube.png "Select code language")
        
    c). Run code analyse using command from a previous step. 
    
    Before code analyse ALWAYS execute:
    
    ```
        mvn clean test
    ```
  
    Next (from previous step): 
    ```
    mvn sonar:sonar \
      -Dsonar.projectKey=pnf-simulator \
      -Dsonar.host.url=http://localhost:9000 \
      -Dsonar.login=de5dac7da79a4de88876006a05457902aab1a3a3
    ```
    After command execution you should see at the console:
    ```
    [INFO] ANALYSIS SUCCESSFUL, you can browse http://localhost:9000/dashboard?id=pnf-simulator
    [INFO] Note that you will be able to access the updated dashboard once the server has processed the submitted analysis report
    ```
    Click at link http://localhost:9000/dashboard?id=pnf-simulator to see Sonar report
    ![alt text](tutorials/sonarqube/sonarqube_report.png "Select code language")
        
    IMPORTANT: Please note command used to run code analise. You will need it later.  
      
## Stopping SonarQube
If you do not want to repeat step **Configure and run SonarQube locally** you must use stopping and starting make goals.
```
   make stop-sonarqube
```

## Starting SonarQube
If you do not want to repeat step **Configure and run SonarQube locally** you must use stopping and starting make goals.
```
   make start-sonarqube
```
