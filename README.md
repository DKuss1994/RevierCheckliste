# SecurityDispatch — Security Patrol Checklist System

A backend system for generating rule-based checklists for security patrol drivers.  
Built as a portfolio project to demonstrate clean Java backend development with Spring Boot.

---

## What This System Does

Security control centers manage multiple patrol drivers across different zones.  
Each zone contains security objects (buildings, premises) with individual inspection rules.

This system automates checklist generation for patrol drivers based on:
- Standard configurations per security object
- Time-based override rules
- Additional service rules
- Reduction rules

The generated checklist is an **immutable snapshot** — printed once and used as a reference for handwritten entries during the patrol.

---

## Architecture

The project follows a **layered architecture** with strict separation of concerns:

```
domain/          → Pure business logic, no framework dependencies
application/     → Use cases and orchestration
infrastructure/  → Spring Boot, JPA, REST controllers
```

Key design decisions:
- Domain model is completely framework-independent
- All business logic is covered by unit tests
- JPA entities are separate from domain classes
- Rule engine processes configurations without side effects

---

## Domain Model

```
Zone
 └── SecurityObject
      ├── StandardConfiguration
      ├── OverrideRule (replaces config for a time period)
      ├── AdditionalRule (adds services for a time period)
      └── ReductionRule (removes services for a time period)

Driver
 └── assignedZones (qualification check)

Shift
 ├── Driver
 ├── Zone
 └── deploymentDate

Checklist (immutable snapshot)
 ├── Shift
 ├── resolved StandardConfiguration
 └── Warnings
```

---

## Rule Engine

The `ConfigurationResolver` processes rules in this order:

1. Load `StandardConfiguration` as base
2. Apply active `OverrideRule` — replaces entire config
3. Apply active `AdditionalRule` — adds inspection count, opening/closing times
4. Apply active `ReductionRule` — removes inspection count, opening/closing times
5. Generate warnings for conflicts (e.g. negative inspection count, removing non-existing services)

---

## Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 21 | Language |
| Spring Boot | 3.4.4 | Framework |
| Spring Data JPA | — | Persistence |
| H2 | — | In-memory database (development) |
| MariaDB | — | Production database (planned) |
| JUnit 5 | — | Testing |
| AssertJ | — | Test assertions |
| Maven | — | Build tool |

---

## Development Approach

This project was built strictly following **Test Driven Development (TDD)**:

1. Write a failing test
2. Write minimal code to make it pass
3. Refactor

Every feature starts with a test. Business logic is tested independently of the framework.

**Test coverage:** 108 tests — all green.

---

## Getting Started

### Prerequisites

- Java 21
- Maven

### Run the application

```bash
./mvnw spring-boot:run
```

The application starts on `http://localhost:8080`.  
Test data is loaded automatically from `src/main/resources/data.sql`.

### Run the tests

```bash
./mvnw test
```

---

## API

### Generate a Checklist

```
POST /checklists/generate
Content-Type: application/json

{
    "shiftId": 1,
    "securityObjectId": 1
}
```

**Response:**

```json
{
    "id": 1,
    "generatedAt": "2026-03-29T09:31:33.038105849",
    "inspectionCount": 2,
    "warnings": []
}
```

---

## Project Status

**Version 1 — completed:**
- ✅ Complete domain model
- ✅ Rule engine with conflict detection
- ✅ Checklist generation as immutable snapshot
- ✅ REST API
- ✅ Persistence layer with JPA

**Version 2 — planned:**
- ⬜ Additional REST endpoints (Zone, Driver, SecurityObject, Shift)
- ⬜ Frontend (React or Thymeleaf)
- ⬜ PDF generation (DIN A4 landscape format)
- ⬜ Production database (MariaDB)

---

## About

Built by a career changer transitioning from security operations to software development.  
Six months of Java experience. Learning by building real-world domain problems.

GitHub: [github.com/DKuss1994](https://github.com/DKuss1994)

LinkedIn: [linkedin.com/in/dennis-kuß-06a040320](https://linkedin.com/in/dennis-kuß-06a040320)