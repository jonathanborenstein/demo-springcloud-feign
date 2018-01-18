# demo-springcloud-feign

This is a simple demo showing how to use Spring Cloud with Eureka, Feign, and Zuul.

First, download or clone the project and import it into your IDE. You can also run the project using the <code>mvn clean package spring-boot:run</code> command in each directory (person-service, eureka-server, person-client).

Start the Eureka server first. It will run on port 8761.

Next start the Person-Service, and then start the Person-Client.

Once this is done go to a terminal to acceess the Client and retrieve the downstream resources using Feign.

Use <code>curl http://localhost:9999</code> to access all persons. Use <code>curl http://localhost:9999/person?id=2</code> to access an invididual person.

To POST a person to the downstream service, use <code>curl -d '{"name":"sarah"}' -H "Content-Type: application/json" -X POST http://localhost:9999/person</code>. Now if you go back to <code>curl http://localhost:9999</code> you will see this person has been added.

The demo uses an in memory H2 database so no need to worry about setting up a database.
