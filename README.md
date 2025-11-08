
# 🛒 BuyZee – Microservices E‑Commerce Backend

A production‑grade, **backend‑first** e‑commerce platform designed to evolve over time with clear GitHub releases and professional architecture.

## 🚀 Current Release

**v1.0.0 – User Service (Auth + JWT + RBAC)**  
- Signup & Login using **Email or Phone**  
- **JWT** based stateless authentication  
- **Role‑Based Access**: `ADMIN > MANAGER > USER`  
- MySQL + Spring Data JPA

> Next planned: v1.1.0 Product Service → v1.2.0 Order Service → v2.0.0 Eureka + API Gateway → v2.1.0 Resilience4j → v3.0.0 Docker → v4.0.0 Kubernetes.

---

## 🧱 Architecture (Backend‑First)

```
Client → API Gateway (v2.0.0) → [User, Product, Order, Payment] Services
                                 ↓
                               MySQL
                                 ↓
                               Kafka (v1.4.0) → Notification Service
                                 ↓
                           Sleuth + Zipkin (Tracing)
```

### Class/Data Flow (User Service v1.0.0)

- **Signup/Login:** `Client → AuthController → UserService → PasswordEncoder → UserRepository → MySQL → JwtService`
- **Authenticated request:** `Client + JWT → JwtAuthFilter → UserController → UserRepository → MySQL`

**Diagrams:**  
- [System Flow PNG](sandbox:/mnt/data/buyzee_user_service_flow.png)  
- [Signup Sequence PNG](sandbox:/mnt/data/buyzee_user_service_sequence.png)  
- [Login Sequence PNG](sandbox:/mnt/data/buyzee_login_sequence.png)

---

## 🛠️ Tech Stack

- **Language:** Java 17
- **Framework:** Spring Boot 3 (Web, Security, Data JPA, Validation)
- **Auth:** Spring Security, JWT (jjwt)
- **DB:** MySQL (1 DB per service)
- **Build:** Maven
- **Observability (planned):** Sleuth + Zipkin
- **Resilience (planned):** Resilience4j
- **Async (planned):** Kafka
- **Deployment (planned):** Docker → Kubernetes

---

## 📦 Module Layout (Monorepo)

```
BuyZee/
 ├── user-service          # v1.0.0 (this repo’s current code)
 ├── product-service       # v1.1.0 (planned)
 ├── order-service         # v1.2.0 (planned)
 ├── payment-service       # v1.3.0 (planned)
 ├── notification-service  # v1.4.0 (planned, Kafka consumer)
 ├── api-gateway           # v2.0.0 (planned)
 └── service-registry      # v2.0.0 (planned, Eureka)
```

---

## ✅ Getting Started (User Service Only)

### Prerequisites
- Java 17
- Maven 3.9+
- MySQL 8+

### Database
```sql
CREATE DATABASE userdb;
```

### Configuration
`user-service/src/main/resources/application.properties`
```properties
server.port=8080
spring.application.name=user-service

spring.datasource.url=jdbc:mysql://localhost:3306/userdb?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

# JWT
buyzee.security.jwt.secret=change_this_to_a_very_long_random_secret_key_please
buyzee.security.jwt.exp-min=60
```

### Build & Run
```bash
mvn clean spring-boot:run
```

---

## 🔐 API (v1.0.0 – User Service)

### Signup
```
POST /auth/signup
Content-Type: application/json
{
  "name": "Haris",
  "email": "haris@example.com",
  "password": "Secret@123"
}
```
**Response:** `token`, `role`, `userId`, `name`

### Login
```
POST /auth/login
Content-Type: application/json
{
  "email": "haris@example.com",
  "password": "Secret@123"
}
```
**Response:** `token`, `role`, `userId`, `name`

### Get Profile (JWT required)
```
GET /users/me
Authorization: Bearer <token>
```

---

## 👥 Roles & Permissions

```
ADMIN > MANAGER > USER
```
- **ADMIN:** all permissions, can manage roles/users/products/orders
- **MANAGER:** can manage products and view orders
- **USER:** browse products, place orders, see own orders

> Default signup role: `USER`

---

## 🧪 Test Quickly (curl)

```bash
# Signup
curl -X POST http://localhost:8080/auth/signup \
  -H 'Content-Type: application/json' \
  -d '{"name":"Haris","email":"haris@example.com","password":"Secret@123"}'

# Login
TOKEN=$(curl -s -X POST http://localhost:8080/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"email":"haris@example.com","password":"Secret@123"}' | jq -r .token)

# Profile
curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/users/me
```

---

## 🧭 Git & GitHub (first‑time setup)

1) **Initialize & commit**
```bash
git init
git add .
git commit -m "feat(user-service): v1.0.0 JWT auth + RBAC foundation"
```

2) **Connect to GitHub**
- Create a repo named **BuyZee** on GitHub (public)
- Then:
```bash
git branch -M main
git remote add origin https://github.com/<your-username>/BuyZee.git
git push -u origin main
```

3) **Create a tagged release**
```bash
git tag v1.0.0 -m "User Service ready: signup/login/JWT/roles"
git push origin v1.0.0
```

> If you prefer SSH, configure an SSH key and use the SSH remote URL instead.

---

## 🗺️ Roadmap

- **v1.1.0 Product Service:** CRUD, stock, admin routes (role‑guarded)
- **v1.2.0 Order Service:** checkout workflow, stock checks
- **v1.3.0 Payment Service:** mock payment + callbacks
- **v1.4.0 Notification Service:** Kafka producer/consumer
- **v2.0.0:** Eureka + API Gateway
- **v2.1.0:** Resilience4j (Circuit Breaker, Retry)
- **v3.0.0:** Docker/Compose
- **v4.0.0:** Kubernetes deployment (HPA, secrets, configmaps)

---

## 📄 License
MIT (or your preferred license).

---

## 🙌 Contributing
PRs welcome. Please open an issue for feature requests or bugs.
