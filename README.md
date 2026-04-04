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
| OpenPDF | — | PDF generation (in progress) |
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

**Test coverage:** 161 tests — all green.

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

## API Endpoints

### Zones
```
POST   /zones          → Create zone
GET    /zones          → Get all zones
GET    /zones/{id}     → Get zone by id
PUT    /zones/{id}     → Update zone
DELETE /zones/{id}     → Delete zone
```

### Drivers
```
POST   /drivers                      → Create driver
GET    /drivers                      → Get all drivers
GET    /drivers/{id}                 → Get driver by id
PUT    /drivers/{id}                 → Update driver
DELETE /drivers/{id}                 → Delete driver
POST   /drivers/{id}/zones/{zoneId}  → Assign zone to driver
```

### Security Objects
```
POST   /security-objects          → Create security object
GET    /security-objects          → Get all security objects
GET    /security-objects/{id}     → Get security object by id
PUT    /security-objects/{id}     → Update security object
DELETE /security-objects/{id}     → Delete security object
```

### Shifts
```
POST   /shifts          → Create shift
GET    /shifts          → Get all shifts
GET    /shifts/{id}     → Get shift by id
DELETE /shifts/{id}     → Delete shift
```

### Checklists
```
POST   /checklists/generate   → Generate checklist for a shift
```

---

## Checklist PDF Layout (in progress)

The generated PDF follows DIN A4 landscape format:

**Header:** Driver name, Zone, Date, Shift time

**Inspections:** Object name with inspection times and one empty checkbox per inspection — the driver fills in the actual time by hand

**Closing:** Object name, scheduled closing time, empty checkbox for handwritten confirmation

**Opening:** Object name, scheduled opening time, empty checkbox — night shifts crossing midnight are handled separately so the driver knows which openings belong to the next morning

---

## Project Status

**Version 1 — completed:**
- ✅ Complete domain model
- ✅ Rule engine with conflict detection
- ✅ Checklist generation as immutable snapshot
- ✅ Full REST API with CRUD for all entities
- ✅ Persistence layer with JPA

**Version 2 — in progress:**
- 🔄 PDF generation (DIN A4 landscape format)

**Version 3 — planned:**
- ⬜ Frontend (Thymeleaf)
- ⬜ Production database (MariaDB)

---

## About

Built by a career changer transitioning from security operations to software development.  
Six months of Java experience. Learning by building real-world domain problems.

GitHub: [github.com/DKuss1994](https://github.com/DKuss1994)