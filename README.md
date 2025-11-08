# BuyZee - User Service

The *User Service* is a core microservice in the *BuyZee E-Commerce Platform*, responsible for:

- User Registration (Signup)
- Secure Authentication (Login)
- Role-Based Access Control (USER, MANAGER, ADMIN)
- JWT Access Token generation
- Authenticated Profile Access (/users/me)

This service is built using *Spring Boot 3, **JWT, **Spring Security, and **MySQL*, following a clean, scalable, enterprise-ready architecture.

---

## 💡 Features

| Feature | Description |
|--------|-------------|
| Signup | Register using Email or Phone |
| Login | Authenticate using Email or Phone |
| Password Encryption | BCrypt secure hashing |
| JWT Authentication | Stateless, scalable auth |
| Role Based Access | USER / MANAGER / ADMIN |
| Profile Endpoint | Get logged-in user details |

---

## 📦 Tech Stack

| Layer | Technology |
|------|------------|
| Language | Java 17 |
| Framework | Spring Boot 3 |
| Security | Spring Security + JWT |
| Database | MySQL |
| ORM | Hibernate / JPA |
| Build Tool | Maven |
| Logging | SLF4J / Logback |

---

## 🔧 Configuration

*application.properties*
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/userdb?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

buyzee.security.jwt.secret=YOUR_32+_CHAR_SECRET_KEY
buyzee.security.jwt.exp-min=60
