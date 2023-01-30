# drones
service allowed developing countries to leapfrog older technologies for personal communication,
the drone has the potential to leapfrog traditional transportation infrastructure.


## Version: 1.0.0

### /drones

#### POST
##### Summary:

register a drone

##### Responses

| Code | Description |
| ---- | ----------- |
| 201 | Created |
| 400 | bad request |
| 500 | Internal Server Error |

#### GET
##### Summary:

list drones info

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| eligibleForLoading | query |filter out the available drones for loading  | No | boolean |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | Ok |
| 400 | bad request |
| 500 | Internal Server Error |

### /drones/{id}/medications

#### POST
##### Description:

loading a drone with medication items


##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path |  drone id| Yes | integer |

##### Responses

| Code | Description |
| ---- | ----------- |
| 201 | Created |
| 400 | bad request |
| 404 | not found |
| 500 | Internal Server Error |

#### GET
##### Description:

A drone medication items info


##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | drone id | Yes | integer |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 404 | not found |
| 500 | Internal Server Error |

### /drones/{id}

#### GET
##### Description:

list a drone info


##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | drone id | Yes | integer |
| fields | query | coma seperated query that select a subset of the attributes of an entity to be present in a returned representation. | No | string |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | Ok |
| 400 | bad request |
| 404 | not found |
| 500 | Internal Server Error |

## Database Schema

![Alt text](src/main/resources/DronesERD.png?raw=true "Database Schema")

### requirements to build, run and test the service

- java 11 
- maven
- postman to import [drones.yaml](openAPI/drones.yaml) as postman collection 
    to test the application
- by default the service run on port 8080 if you need to change it, update its value in [application.yaml](src/main/resources/application.yaml)
#### Step 1  *build the service using maven (packaging)*
```bash
    mvn clean package
```
#### Step 2 - *run the service*
```bash
    java -jar ./target/Drones-1.0.0-SNAPSHOT.jar
```
### Step 3  *test the service*
- import [drones.yaml](openAPI/drones.yaml) as postman collection
- change the baseUrl var on postman with your server url and running port number example
  http://localhost:8080
- test all endpoints

### Step 4  *review the service documentation*
- you can review api specs [drones.yaml](openAPI/drones.yaml)
  on swagger editor [here]('http://editor.swagger.io/#/?import=https://github.com/ahmedshaheen93/Drones/raw/master/openAPI/drones.yaml')
