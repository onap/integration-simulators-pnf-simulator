# Changelog
All notable changes to this project will be documented in this file.

## [1.0.1]

### Fix
 - File watcher no longer crashes after adding empty file to template folder. In stead it logs information about wrong template format
   - https://jira.onap.org/browse/INT-1533

## [1.0.2]

### Fix
 - Fix failing build by using image sysrepo-netopeer2:v0.7.7 
   - https://jira.onap.org/browse/INT-1844
  
### Added
 - Response message from VES is returned by PNF simulator when performing one time event request
   - https://jira.onap.org/browse/INT-1804
 - Possibility to authenticate in VES using username and password in VES URL
   - https://jira.onap.org/browse/INT-1805
 - Extend PNF simulator with HTTP server
   - https://jira.onap.org/browse/INT-1814
    
## [1.0.3]
   
## [1.0.4]

###  Fix
- Fix integration test
   - https://jira.onap.org/browse/INT-1844
