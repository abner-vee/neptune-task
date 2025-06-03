# NeptuneBank Order Processing System
This project is a microservices-based Order Processing System consisting of:

A PostgreSQL database

Two Spring Boot services (inventory-service and order-service)

An Angular frontend (frontend)

All components are containerized using Docker and orchestrated via docker-compose.
 
## Tech Stack
Backend: Spring Boot (gRPC-based microservices)

Frontend: Angular

Database: PostgreSQL

Containerization: Docker & Docker Compose

## Getting Started
### Prerequisites
Ensure the following are installed:

* Docker

* Docker Compose


️Project Structure

```bash
.
├── docker-compose.yml
├── inventory-service/
│   └── Dockerfile
├── order-service/
│   └── Dockerfile
├── order-processing-frontend/
│   └── Dockerfile

```
## Environment Configuration
The following environment variables are preconfigured:

PostgreSQL

```bash
POSTGRES_DB=neptunebank

POSTGRES_USER=postgres

POSTGRES_PASSWORD=postgres
```

Spring Services

```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/neptunebank

SPRING_DATASOURCE_USERNAME=postgres

SPRING_DATASOURCE_PASSWORD=postgres
```

## Running the Project
To build and start all services, simply run:

```bash
docker-compose up --build
```

## This will:

Start PostgreSQL on port 5432

Start inventory-service on port 8083

Start order-service on port 8084

Serve the Angular frontend at http://localhost:4200


## Clean Up
To stop and remove all containers, networks, and volumes:

```bash
docker-compose down -v
```

## Access Points:
### Service	URL

* Angular Frontend	http://localhost:4200
* Order Service (API)	http://localhost:8084
* Inventory Service	http://localhost:8083
* SWAGGER             http://localhost:8084/swagger-ui.html
* PostgreSQL DB	    localhost:5432

## Notes
Make sure no other services are using ports 4200, 5432, 8083, or 8084 before starting.

If using gRPC, gRPC clients must be configured to connect to the appropriate service ports.

Let me know if you'd like a badge section, Docker image publishing guide, or CI/CD instructions added.