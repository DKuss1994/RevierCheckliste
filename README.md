# SecurityDispatch — Patrol Checklist System

## Live Demo

The application is deployed on Railway and can be accessed here:  
👉 [reviercheckliste-production.up.railway.app](reviercheckliste-production.up.railway.app)

*Note: The first request may take a few seconds because the service spins up from idle.*

---

## Overview

SecurityDispatch is a backend system for generating rule-based patrol checklists in security operations environments.

The system was developed to address real-world challenges in control centers, where patrol processes are often managed manually and lack consistency, traceability, and reliability.

---

## Problem Context

In security control centers, patrol checklists are frequently handled using Excel or paper-based systems.

This results in:
- Inconsistent or incomplete documentation
- Limited traceability of performed inspections
- Increased coordination effort under time pressure

---

## Solution

SecurityDispatch provides a structured backend system that:

- Automates checklist generation
- Applies time-dependent business rules
- Produces an immutable checklist snapshot for operational use
- Provides a web‑based user interface for all core entities
- Generates a formatted PDF for printing

The goal is to standardize patrol workflows and reduce operational errors.

---

## Key Features

- Rule‑based checklist generation (Override, Additional, Reduction)
- Immutable checklist snapshots
- Conflict detection with warning generation
- Web frontend with Bootstrap 5 (CRUD for zones, drivers, security objects, shifts)
- PDF checklist generation (landscape, inspection table, closing/opening tables)
- Docker containerization and PostgreSQL production setup
- Test‑driven development with comprehensive test coverage (~200 tests)

---

## Technology Stack

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL (production) / H2 (development)
- Thymeleaf (frontend templates)
- Bootstrap 5 (styling)
- OpenPDF (PDF generation)
- Docker / Docker Compose
- Railway (deployment)
- JUnit 5, AssertJ, Mockito
- Maven

---

## Architecture

The system follows a layered architecture with strict separation of concerns:

domain/ → Business logic (framework-independent)
application/ → Use case orchestration
infrastructure/ → REST API, persistence, web controllers, Spring Boot integration


### Design Principles

- Domain‑driven design approach
- Framework‑independent domain layer
- Separation of JPA entities and domain model
- Deterministic rule processing without side effects
- High testability of all business logic

---

## Domain Model

Zone
└── SecurityObject
├── StandardConfiguration
├── OverrideRule
├── AdditionalRule
└── ReductionRule

Driver
└── assignedZones

Shift
├── Driver
├── Zone
└── deploymentDate

Checklist (immutable snapshot)
├── Shift
├── List<ChecklistEntry> (each with resolved configuration)
└── warnings


---

## Rule Engine

The `ConfigurationResolver` applies rules in a defined order:

1. **StandardConfiguration** – base state
2. **OverrideRule** – replaces entire configuration
3. **AdditionalRule** – adds services (inspectionCount, openingTime, closingTime)
4. **ReductionRule** – removes services
5. Conflict detection and warning generation

---

## Web Interface

The application provides a complete web frontend for all core entities:

- Dashboard with quick access to zones, drivers, security objects, shifts, and checklist generation
- Create, edit, list and delete entities
- Bootstrap responsive design

### Checklist Generation

1. Go to `/checklists/generate`
2. Select a shift from the dropdown
3. Click "PDF herunterladen" – the checklist is generated and downloaded

---

## API Example

Generate a checklist for a shift (REST):

```http
POST /api/checklists/generate
Content-Type: application/json

{
  "shiftId": 1
}
```

# Testing Strategy

The system was developed using Test‑Driven Development (TDD):

    ~200 unit and integration tests

    Business logic tested independently of framework

    Focus on edge cases and reliability

Output

The system generates a DIN A4 landscape PDF checklist containing:

    One‑line header: driver, zone, date, shift start/end (night shift indicator)

    Inspection table: security objects, time window, checkboxes (one per required inspection)

    Closing table: only for objects with a closing time

    Opening table: only for objects with an opening time (next day displayed for night shifts)

    Warnings section for any rule conflicts or inconsistencies

Project Status

Version 1 (completed):

    ✅ Domain model with all rules

    ✅ Rule engine and conflict detection

    ✅ Immutable checklist snapshots

    ✅ REST API for all entities (/api/...)

    ✅ Web frontend with Thymeleaf (zones, drivers, security objects, shifts)

    ✅ PDF checklist generation (full layout as described)

    ✅ Docker containerization

    ✅ Deployment on Railway with PostgreSQL

    ✅ Comprehensive test suite (~200 tests)

Planned for future versions:

    Advanced search / wildcard support in REST API

    Service-level documentation (OpenAPI)

    OAuth2 login (optional)

Author

This project was developed based on real‑world experience in a security control center environment.

Focus areas:

    Java backend development

    Clean architecture and domain‑driven design

    Test‑driven development (TDD)

    Full‑stack web applications with Thymeleaf

GitHub: https://github.com/DKuss1994

Live Demo: https://reviercheckliste-production.up.railway.app
