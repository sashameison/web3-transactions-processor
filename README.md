# web3-transactions-processor

This is a web3 processing application built with Spring Boot, Gradle, and Java. 
It processes transaction from blockchain and stores data in PostgresSQL and Elasticsearch

## Prerequisites

- Java 17 or later
- Gradle 7.x or later
- Git

Ensure that the following tools are installed and added to your system's PATH.

## Setup and Installation

### 1. Clone the Repository

```bash
git clone https://github.com/sashameison/web3-transactions-processor.git
cd web3-transactions-processor
```

### 2. Build docker-compose containers to start up app. In case you are not using MacOS update image for ElasticSearch
```bash
docker-compose up -d
```

### 3. Build and run the app
```
./gradlew bootRun
```


## 4. API Documentation

For detailed API documentation, please visit the [Swagger UI](http://localhost:8080/swagger-ui/index.html).

You can search through transaction blockNumber using (Update block number) -  
```bash
curl http://localhost:8080/api/v1/transactions?page=0&size=100&blockNumber=275059938
```
