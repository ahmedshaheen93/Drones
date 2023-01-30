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
