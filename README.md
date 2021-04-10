## Simple Spring Boot project featuring a REST API and a CRUD module

Built with maven and exposes a [docker-compose.yaml](blob/master/docker-compose.yaml) to provide a test-ready environment with a PostgreSQL container.

ATM, it features creation and reading of a **user** entity through a Rest API. With the application running, an OpenAPI specification(Swagger rendered) can be found at [localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)