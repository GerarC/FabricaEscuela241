# FabricaEscuela241

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=GerarC_FabricaEscuela241&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=GerarC_FabricaEscuela241)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=GerarC_FabricaEscuela241&metric=bugs)](https://sonarcloud.io/summary/new_code?id=GerarC_FabricaEscuela241)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=GerarC_FabricaEscuela241&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=GerarC_FabricaEscuela241)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=GerarC_FabricaEscuela241&metric=coverage)](https://sonarcloud.io/summary/new_code?id=GerarC_FabricaEscuela241)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=GerarC_FabricaEscuela241&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=GerarC_FabricaEscuela241)

This is the repository of the backend for Search Flights module of Fabrica-Escuela UdeA.    
<br/>
The project is a Spring Boot application that exposes an API a frontend side can use.

## Using with docker
If you want to use the dockerized version of this application open bootdocker directory in a terminal,
whether Gnu/Linux or Windows, and execute the next command:
~~~ bash
docker compose up
~~~
or, if you want to run in background, add -d option at the final of the command
~~~ bash
docker compose up -d
~~~

The default port of the application is *8099*

## Main Entities
The main entities are *Flight*, *Scale*, *Airport*, *AirplaneModel*, *Person*, *IdentificationType*, *SearchHistory* and *FlightHistory* and these are associated to several end points.
These endpoints have the next format:
- `/entities`: can be used with GET and POST verbs to get all items and save a new one.
- `/entities/{id}`: can be used with GET, PUT and DELETE verbs, it's not necessary to explain what does each verb.
- `/entities/{id}/sub-entities`: can be used with GET verb to show those sub-elements.
    
**Flight** entity has something special, and it's that can be filtered using the next format: `/flights?parameter=value`,
these queries can be concatenated like `/flights?param1=val1&param2=val2`.

The available entity end-points are: `flights`, `scales`, `airports`, `airplane-models`, `people`, `identification-types`, `search-histories`, `flight-histories`.

## TODO
- [ ] Improve Swagger documentation.