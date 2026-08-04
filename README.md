# Smart Task Manager - Backend API

Spring Boot REST API for the **Smart Task Management and Productivity Tracker** application.

## Tech Stack

- Java 17
- Spring Boot 3.2
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL
- Maven

## Features

- User registration and login with JWT authentication
- CRUD operations for tasks
- Task priority: High, Medium, Low
- Task status: Pending, In Progress, Completed
- Dashboard statistics (total, pending, in progress, completed, completion rate)
- User-scoped tasks (each user sees only their own tasks)

## Prerequisites

- Java 17+
- Maven 3.8+
- PostgreSQL 14+

## Database Setup

1. Install and start PostgreSQL
2. Create the database:

```sql
CREATE DATABASE taskmanager_db;
```

3. Update credentials in `src/main/resources/application.properties` if needed:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/taskmanager_db
spring.datasource.username=postgres
spring.datasource.password=postgres
```

## Running the Application

```bash
# From the Backend directory
mvn spring-boot:run
```

The API runs at `http://localhost:8080`

## API Endpoints

### Authentication (Public)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login and receive JWT token |

### Tasks (Protected - requires Bearer token)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/tasks` | Get all tasks for logged-in user |
| GET | `/api/tasks/{id}` | Get task by ID |
| POST | `/api/tasks` | Create a new task |
| PUT | `/api/tasks/{id}` | Update a task |
| PATCH | `/api/tasks/{id}/status` | Update task status only |
| DELETE | `/api/tasks/{id}` | Delete a task |

### Dashboard (Protected)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/dashboard/stats` | Get productivity statistics |

## Sample Request Bodies

**Register:**
```json
{
  "fullName": "John Doe",
  "email": "john@example.com",
  "password": "password123"
}
```

**Login:**
```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

**Create Task:**
```json
{
  "title": "Complete project report",
  "description": "Write and submit the final report",
  "priority": "HIGH",
  "status": "PENDING",
  "dueDate": "2026-08-10"
}
```

**Update Status:**
```json
{
  "status": "IN_PROGRESS"
}
```

## Authentication

Include the JWT token in the Authorization header:

```
Authorization: Bearer <your-jwt-token>
```

## Push to GitHub

This folder is designed as a standalone repository:

```bash
cd Backend
git init
git add .
git commit -m "Initial commit: Smart Task Manager Backend API"
git remote add origin https://github.com/YOUR_USERNAME/smart-task-manager-backend.git
git branch -M main
git push -u origin main
```

## Project Structure

```
Backend/
├── src/main/java/com/avikaar/taskmanager/
│   ├── config/          # Security & CORS configuration
│   ├── controller/      # REST controllers
│   ├── dto/             # Request/Response objects
│   ├── entity/          # JPA entities & enums
│   ├── exception/       # Exception handling
│   ├── repository/      # Data access layer
│   ├── security/        # JWT & authentication
│   └── service/         # Business logic
├── src/main/resources/
│   └── application.properties
└── pom.xml
```

## License

Personal project - Avikaar SMT
