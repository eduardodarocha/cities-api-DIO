# Projeto realizado no Bootcamp Everis Quality Assurance Beginner da Digital Innovation One
* [Digital Innovation One](https://digitalinnovation.one/)

# Cities API
## Construindo uma API Rest de consulta de cidades do Brasil do zero até a produção
### Neste projeto tivemos o desafio de desenvolver uma API Rest de consulta de cidades do Brasil com dados comparativos. Conhecemos as boas práticas de Java e do Spring, populando o banco de dados Postgresql dentro de um container Docker e criando um serviço para o cálculo de distância entre cidades.

## Requirements

* [Linux - Ubuntu 20.04 LTS](https://ubuntu.com/) ou Windows
* [Git](https://git-scm.com/)
* [GitHub](https://github.com/)
* [Java 8](https://www.oracle.com/br/java/technologies/javase/javase-jdk8-downloads.html)
* [Docker](https://www.docker.com/)
* [IntelliJ Community](https://www.jetbrains.com/pt-br/idea/)
* [Heroku CLI](https://devcenter.heroku.com/articles/heroku-cli)

## DataBase

### Postgres

* [Postgres Docker Hub](https://hub.docker.com/_/postgres)

```shell script
docker run --name cities-db -d -p 5432:5432 -e POSTGRES_USER=postgres_user_city -e POSTGRES_PASSWORD=super_password -e POSTGRES_DB=cities postgres
```

### Populate

* [data (Paises, Estados e Cidades)](https://github.com/chinnonsantos/sql-paises-estados-cidades/tree/master/PostgreSQL)

```shell script
cd ~/workspace/sql-paises-estados-cidades/PostgreSQL

docker run -it --rm --net=host -v $PWD:/tmp postgres /bin/bash (Linux)
docker run -it --rm --net=host -v "%CD%":/tmp postgres /bin/bash (Windows)

psql -h localhost -U postgres_user_city cities -f /tmp/pais.sql
psql -h localhost -U postgres_user_city cities -f /tmp/estado.sql
psql -h localhost -U postgres_user_city cities -f /tmp/cidade.sql

psql -h localhost -U postgres_user_city cities

CREATE EXTENSION cube; 
CREATE EXTENSION earthdistance;
```

* [Postgres Earth distance](https://www.postgresql.org/docs/current/earthdistance.html)
* [earthdistance--1.0--1.1.sql](https://github.com/postgres/postgres/blob/master/contrib/earthdistance/earthdistance--1.0--1.1.sql)
* [OPERATOR <@>](https://github.com/postgres/postgres/blob/master/contrib/earthdistance/earthdistance--1.1.sql)
* [postgrescheatsheet](https://postgrescheatsheet.com/#/tables)
* [datatype-geometric](https://www.postgresql.org/docs/current/datatype-geometric.html)

### Access

```shell script
docker exec -it cities-db /bin/bash

psql -U postgres_user_city cities
```

### Query Earth Distance

Point
```roomsql
select ((select lat_lon from cidade where id = 4929) <@> (select lat_lon from cidade where id=5254)) as distance;
```

Cube
```roomsql
select earth_distance(
    ll_to_earth(-21.95840072631836,-47.98820114135742), 
    ll_to_earth(-22.01740074157715,-47.88600158691406)
) as distance;
```

## Deploy Heroku
```shell script
heroku create

git push heroku main

heroku logs --tail
CTRL + c

heroku addons:create heroku-postgresql
```

* entrar no diretorio dos script para popular o DB do Heroku
```shell script
docker run -it --rm --net=host -v $PWD:/tmp postgres /bin/bash

psql -h <host do postgresql no heroku> -U <usuario do postgresql no heroku> <database do Heroku> -f /tmp/pais.sql
psql -h <host do postgresql no heroku> -U <usuario do postgresql no heroku> <database do Heroku> -f /tmp/estado.sql
psql -h <host do postgresql no heroku> -U <usuario do postgresql no heroku> <database do Heroku> -f /tmp/cidade.sql
```

-entrar no Postgresql do Heroku e adicionar as extensões:
```shell script
psql -h <host do postgresql no heroku> -U <usuario do postgresql no heroku> <database do Heroku>

CREATE EXTENSION cube;

CREATE EXTENSION earthdistance;
```

* Minha API pode ser achada aqui:

https://fierce-scrubland-28454.herokuapp.com/

* endpoints:
	* /countries
	* /countries/1
	* /countries?page=0&size=10&sort=id,asc
	* /countries?page=0&size=10&sort=name,asc
	* /states
	* /cities
	* /distances/by-cube?from=4929&to=5254
	* /distances/by-points?from=4929&to=5254


## Spring Boot

* [https://start.spring.io/](https://start.spring.io/)

+ Java 8
+ Gradle Project
+ Jar
+ Spring Web
+ Spring Data JPA
+ PostgreSQL Driver

### Spring Data

* [jpa.query-methods](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods)

### Properties

* [appendix-application-properties](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html)
* [jdbc-database-connectio](https://www.codejava.net/java-se/jdbc/jdbc-database-connection-url-for-common-databases)

### Types

* [JsonTypes](https://github.com/vladmihalcea/hibernate-types)
* [UserType](https://docs.jboss.org/hibernate/orm/3.5/api/org/hibernate/usertype/UserType.html)

## Heroku

* [DevCenter](https://devcenter.heroku.com/articles/getting-started-with-gradle-on-heroku)

## Code Quality

### PMD

+ https://pmd.github.io/pmd-6.8.0/index.html

### Checkstyle

+ https://checkstyle.org/

+ https://checkstyle.org/google_style.html

+ http://google.github.io/styleguide/javaguide.html

```shell script
wget https://raw.githubusercontent.com/checkstyle/checkstyle/master/src/main/resources/google_checks.xml
```
