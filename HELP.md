# Spring Boot Active MQ Demo

### Run local ActiveMQ Broker in Docker
`docker run -it --rm -p 8161:8161 -p 61616:61616 vromero/activemq-artemis`


### Connection info

 `http://127.0.0.1:8161`
 `username: artemis`
 `password: simetraehcapa`
 
 application.properties
 `spring.artemis.user=artemis
  spring.artemis.password=simetraehcapa`