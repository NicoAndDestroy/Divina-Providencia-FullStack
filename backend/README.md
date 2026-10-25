# Divina Providencia - Project API

## Evidencia

GA7-220501096-AA5-EV03  
Diseño y desarrollo de servicios web – proyecto.

## Descripción

Este proyecto corresponde al desarrollo de una API REST para el proyecto formativo Divina Providencia.

La API fue desarrollada con Spring Boot y permite gestionar diferentes funcionalidades relacionadas con la plataforma, incluyendo:

- Registro e inicio de sesión de usuarios.
- Gestión de alojamientos.
- Gestión de reservas.
- Consulta de alojamientos disponibles.
- Consulta de reservas por estado.
- Consulta de reservas asociadas a un alojamiento.

La información es almacenada en una base de datos MySQL mediante Spring Data JPA e Hibernate.

## Tecnologías utilizadas

- Java 21
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA
- Hibernate
- MySQL
- Bean Validation
- BCrypt
- Maven
- Postman
- Git
- GitHub

## Arquitectura del proyecto

El proyecto utiliza una arquitectura organizada en diferentes capas:

- Controller: expone los servicios REST.
- Service: contiene la lógica de negocio.
- Repository: realiza el acceso a datos.
- Model: contiene las entidades JPA.
- DTO: define los objetos utilizados para recibir y devolver información.
- Config: contiene configuraciones generales del proyecto.

## Base de datos

Nombre de la base de datos:

```text
divina_providencia_project