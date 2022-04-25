
# Mendel-challenge application
Backend Mendel challenge

### Tech

    * Java 11
    * Spring-boot
    
## Install the app

    ./mvnw install

## Run local app 🚀
    ./mvnw spring-boot:run

## Run the tests

    ./mvnw test

# REST API

The REST API to handle transactions.

## Create a new transactions

### Request

`PUT transactions/transaction/${transactionId}/`

      curl -X PUT -H 'Content-Type: application/json' -d '{"type": "car", "amount": 100, "parent_id": null}'  http://localhost:8080/transactions/1000

### Response

    {"status": "ok"}

## Get all transactions ids by type.

### Request

`GET transactions/types/${type}/`

     curl -X GET -H 'Content-Type: application/json'  http://localhost:8080/transactions/types/car

### Response

   [long, long, long]

## Sum related transactions.

### Request

`GET transactions/sum/${transactionId}/`

    curl -X GET -H 'Content-Type: application/json'  http://localhost:8080/transactions/sum/1000


### Response

     {"sum": double}

# Docker Container

### Container name
`matiascaravajal/mendel:v0.0.4`

### Prerequisities


In order to run this container you'll need docker installed.

* [Windows](https://docs.docker.com/windows/started)
* [OS X](https://docs.docker.com/mac/started/)
* [Linux](https://docs.docker.com/linux/started/)

### Run 🚀


```shell
docker run -p 8080:8080 --env SPRING_PROFILES_ACTIVE=docker matiascaravajal/mendel:v0.0.4
```
