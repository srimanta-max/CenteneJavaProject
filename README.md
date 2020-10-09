# Enrollee-Details Service

## Prerequisite
`Java-11`
`Maven`
`Docker`
`Mysql client`

## Start docker mysql
```
docker run -p 3306:3306 --name=codechallenge -e MYSQL_ROOT_PASSWORD=password -d mysql:latest
create database centeneDB
```

## Running the app 
`mvn spring-boot:run`

## Swagger API URL
```
http://<host:port>/swagger-ui.html#
```

