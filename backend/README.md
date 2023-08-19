# Project K

---
Small project focused on backend development with microservices and K8s.

## Dependencies
### AMQP messaging
Broker for messaging between services.
```shell
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 -d rabbitmq:3.12-management
```

### Mongodb
For the Quote Manager service.
```shell
docker run -it --name mongodb -p 27017:27017 -d mongo
```

### neo4j
For the User Manager service.
```shell
docker run -it --name neo4j -p 7474:7474 -p 7687:7687 -d neo4j
```

### Postgres
For the auth service.
```shell
docker run -it --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=postgres POSTGRES_DB=postgres POSTGRES_USER=postgres -d postgres
```
> Note that in dev environement, we use a H2 db