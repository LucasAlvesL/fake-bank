# Fake Bank REST API Documentation

## Overview
This is a fake banking REST API built using **Spring Boot**, **Spring Security**, **Spring Validation**, **JWT Authentication**, **Swagger**, **Spring Docker**, and **MySQL**. The API allows users to register, log in, and make transactions between accounts securely using the `@Transactional` annotation.

## Technologies Used
- **Spring Boot** - Framework for building the application
- **Spring Security** - Secures the API
- **Spring Validation** - Validates request inputs
- **Spring Docker** - Containerization
- **JWT Auth** - Secure authentication mechanism
- **Swagger** - API documentation
- **MySQL** - Database for storing user and transaction data

## Features
- **User Registration & Login**
- **JWT-based Authentication & Authorization**
- **Transactional Money Transfers between Accounts**
- **API Documentation with Swagger**
- **Docker Support for Deployment**

## API Endpoints

### Authentication
#### Register a new user
```http
POST /api/v1/auth/register
```
**Request Body:**
```json
{
  "fullName": "John Doe",
  "age": "31",
  "cpf": "000.000.000-XX",
  "password": "johndoe@1234",
  "email": "john.doe@example.com"
}
```
**Response:**
```json
{
    "id": "a2cb2993-0bf9-4f77-9e51-6df06e851976", // UUID Generation
    "fullName": "John Doe",
    "age": 31,
    "email": "john.doe@example.com",
    "password": "$2a$10$FnW6hpxmGYD.v1Uw3uQpje1U5VJKphvM4TyrhdSB546nui3ABk6OS",
    "cpf": "000.000.000-XX",
    "balance": 0,
    "createdAt": "2025-02-06T10:42:31.663136",
    "updatedAt": "2025-02-06T10:42:31.663136"
}
```

#### Login
```http
POST /api/v1/users/auth/login
```
**Request Body:**
```json
{
  "email": "john.doe@example.com",
  "password": "johndoe@1234"
}
```
**Response:**
```json
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJqd3RfaXNzdWVyIiwic3ViIjoiYTJjYjI5OTMtMGJmOS00Zjc3LTllNTEtNmRmMDZlODUxOTc2Iiwicm9sZXMiOlsiVVNFUiJdLCJleHAiOjE3Mzg4NTAwMzN9.hyl0FftfBwamXNZcOwQer-Kq-VqPNqynpIMP1aCCqiI",
    "expires_in": 1738850033230
}
```

### User Transactions
#### Transfer Money Between Accounts, Although user needs to be authenticated, transactions between accounts is being make through UUID authenticated in Bearer, It takes the UUID stored in token subject and set as Sender, in the body only will need to be specified the receiver email and the amount to be tranferred
```http
POST /api/v1/users/transfer
```
**Headers:**
```http
Authorization: Bearer <JWT Token>
```
**Request Body:**
```json
{
  "receiverEmail": "test@example.com",
  "amount": 100.00
}
```
**Response:**
```json
{
    "message": "Transfer successful",
    "amountTransferred": 1000,
    "receiverEmail": "john.doe@example.com"
}
```

## Running with Docker
To build and run the project using Docker:
```sh
docker build -t fake-bank-api .
docker-compose up -d
```

## Database Configuration (MySQL)
Configure the MySQL database in `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fakebank
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

## Swagger API Documentation
Once the application is running, access the API documentation at:
```
http://localhost:8080/swagger-ui/index.html
```
![Image](https://github.com/user-attachments/assets/15cf1ddb-59ee-462c-8bcb-841b227dc7ad)

## Security & Transactions
- **Spring Security & JWT**: Ensures that only authenticated users can perform transactions.
- **@Transactional**: Ensures that transactions are atomic and rollback in case of failure.

## Conclusion
This fake bank REST API demonstrates a fully functional banking system with secure authentication, transaction management, and Docker deployment.
