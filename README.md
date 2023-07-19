# Project K

---
Small project focused on backend development with microservices and K8s.

## Dependencies
### AMQP messaging
We will be using RabbitMQ as our message broker
```shell
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 -d rabbitmq:3.12-management
```