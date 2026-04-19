# SecurityDispatch — Patrol Checklist Backend

## 🚨 Real-World Problem

In security control centers, patrol checklists are often managed manually using Excel or paper.

From my own experience working in a control center, this leads to:
- Missing or incorrect entries
- No reliable traceability
- High coordination effort under time pressure

---

## 💡 Solution

SecurityDispatch is a backend system that generates **rule-based patrol checklists** for security drivers.

The system:
- Automates checklist creation
- Applies time-based rules
- Produces an immutable checklist snapshot used during patrols

👉 Goal: reduce errors, improve traceability, and standardize patrol workflows

---

## ⚙️ Key Features

- Rule-based checklist generation
- Time-dependent override / additional / reduction rules
- Immutable checklist snapshots
- Conflict detection with warnings
- Full REST API (CRUD + checklist generation)
- ~160+ automated tests (TDD)

---

## 🧱 Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- H2 (dev)
- JUnit 5 / AssertJ
- Maven

---

## 🏗 Architecture

Layered architecture with strict separation:

domain/          → Business logic (framework-independent)  
application/     → Use cases  
infrastructure/  → Spring Boot, REST, persistence  

### Key decisions:

- Domain model is completely framework-independent  
- JPA entities separated from domain  
- Rule engine is deterministic and side-effect free  
- Business logic fully unit tested  

---

## 🧠 Domain Model

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

## ⚙️ Rule Engine

The ConfigurationResolver processes rules in this order:

1. StandardConfiguration (base)  
2. OverrideRule (replace config)  
3. AdditionalRule (add services)  
4. ReductionRule (remove services)  
5. Conflict detection  

---

## 🔌 Example API

POST /checklists/generate

{
  "shiftId": 1
}

---

## 🧪 Testing

This project was built using **Test Driven Development (TDD)**.

- ~160 tests  
- Business logic tested independently of framework  
- Focus on reliability and edge cases  

---

## 📄 Output

The system generates a **printable checklist (PDF in progress)**:

- Structured inspection plan  
- Clear time slots  
- Designed for handwritten confirmation  

---

## 🚀 Project Status

Version 1 (completed):  
- Domain model  
- Rule engine  
- REST API  
- Persistence layer  

In Progress:  
- PDF generation  

Planned:  
- Frontend (Thymeleaf)  
- MariaDB  

---

## 👤 About Me

I built this project based on real-world problems from my work in a security control center.

Currently transitioning into backend development with a focus on:
- Java / Spring Boot  
- Clean architecture  
- Test-driven development  

GitHub: https://github.com/DKuss1994
