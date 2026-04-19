# SecurityDispatch — Patrol Checklist Backend

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

The goal is to standardize patrol workflows and reduce operational errors.

---

## Key Features

- Rule-based checklist generation
- Time-dependent configuration handling:
  - Override rules
  - Additional rules
  - Reduction rules
- Immutable checklist snapshots
- Conflict detection with warning generation
- REST API for all core entities
- Test-driven development with comprehensive unit test coverage (~160 tests)

---

## Technology Stack

- Java 21
- Spring Boot
- Spring Data JPA
- H2 (development)
- JUnit 5
- AssertJ
- Maven

---

## Architecture

The system follows a layered architecture with strict separation of concerns:

domain/          → Business logic (framework-independent)  
application/     → Use case orchestration  
infrastructure/  → REST API, persistence, Spring Boot integration  

### Design Principles

- Domain-driven design approach
- Framework-independent domain layer
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
 ├── resolved configuration  
 └── warnings  

---

## Rule Engine

The ConfigurationResolver applies rules in a defined order:

1. StandardConfiguration (base state)  
2. OverrideRule (replaces configuration)  
3. AdditionalRule (adds services)  
4. ReductionRule (removes services)  
5. Conflict detection and warning generation  

---

## API Example

Generate a checklist for a shift:

POST /checklists/generate

{
  "shiftId": 1
}

---

## Testing Strategy

The system was developed using Test Driven Development (TDD):

- ~160 unit tests
- Business logic tested independently of framework
- Focus on edge cases and reliability

---

## Output

The system generates structured checklist data.

A printable PDF representation (DIN A4 landscape) is currently in development and is designed for operational use during patrols.

---

## Project Status

Version 1 (completed):
- Domain model
- Rule engine
- REST API
- Persistence layer

In progress:
- PDF generation

Planned:
- Frontend (Thymeleaf)
- MariaDB integration

---

## Author

This project was developed based on real-world experience in a security control center environment.

Focus areas:
- Java backend development
- Clean architecture
- Test-driven development

GitHub: https://github.com/DKuss1994
