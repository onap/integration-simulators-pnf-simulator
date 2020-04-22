### Run  test case pnfsimulator -> ves collector -> dmaap simulator

### 1. Build Projects
```
make start
```
### 2. Send one event
```
make generate-event
```
### 2.1 Check dmaap sim
```
make check-dmaap
```
### 3. Send few events
```
make generate-multiple-events
```
### 3.1 Check dmaap sim
```
make check-dmaap
```
### 4. Clear environment
```
make stop
```
