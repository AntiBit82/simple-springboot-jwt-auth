# Spring Boot 3.5.10 – JWT Authentication & Authorization

This project demonstrates minimal JWT-based authentication & authorization using:

- Spring Boot 3.5.10
- Spring Security 6
- JWT (JJWT 0.12.x)
- H2 in-memory database
- Role-based authorization using `@Secured`

---

# Workflow
## Try to access the API without being logged in
```bash
curl -i http://localhost:8080/auth/hello
```
Response:
```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Authentication required"
}

```
## Register a standard User (`USER_ROLE`)

```bash
curl -X -i POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"antibit","password":"1234"}'
```
Response:
```
User registered
```
## Login as standard user
### Wrong password
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"antibit","password":"I_DONT_KNOW"}'
```
Response:
```json
{
  "error":"Bad Request",
  "message":"Invalid credentials",
  "timestamp":"2026-02-08T20:16:24.515369",
  "status":400
}
```
### Correct password (get JWT)
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"antibit","password":"1234"}'
```
Response:
```
eyJhbGciOiJI... <TOKEN>
```
## Access the API again with correct JWT
```bash
curl -X GET http://localhost:8080/auth/hello \
  -H "Authorization: Bearer <TOKEN>"
```
Response:
```
Hello authenticated user
```
## Access ADMIN endpoint (JWT not authorized)
```bash
curl -X GET http://localhost:8080/auth/admin \
  -H "Authorization: Bearer <TOKEN>"
```
Response:
```json
{
  "error":"Forbidden",
  "message":"You do not have permission to access this resource",
  "timestamp":"2026-02-08T20:33:21.055540800",
  "status":403
}
```
## Register ADMIN user (`ADMIN_ROLE`)
```bash
curl -X POST http://localhost:8080/auth/register-admin \
-H "Content-Type: application/json" \
-d '{"username":"admin","password":"admin123"}'
```
Response:
```
Admin registered
```
## Login as admin (get ADMIN JWT)
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```
Response:
```
eyJhbGciO... <ADMIN-TOKEN>

```
## Access ADMIN endpoint (with correct ADMIN JWT)
```bash
curl -X GET http://localhost:8080/auth/admin \
  -H "Authorization: Bearer <ADMIN-TOKEN>"
```
Response:
```
Hello Admin
```