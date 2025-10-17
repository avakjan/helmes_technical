## Helmes Technical Assignment - Java (Spring Boot) + Angular

### Overview
This project implements the assignment using a Spring Boot backend (Maven, Java 17) and an Angular frontend. It covers:
- Sectors seeded into a database and exposed via an API
- A form (Name, Sectors multi-select, Agree to terms)
- Validation (all fields are mandatory)
- Save to DB, auto-refill from stored data
- Session-based editing (update your submission during the session)

### Why these choices
- **Spring Boot 3 (Web, Data JPA)**: minimal boilerplate to expose REST.
- **H2 (file mode)**: embedded DB persisting to disk - zero external setup.
- **JPA entities**:
  - `Sector(id, name, parentId, sortOrder)`
  - `UserSubmission(name, agreeTerms, sectors)` with Many-to-Many sectors: one user can select many sectors; many users can select the same sector.
- **Angular**: reactive forms + HttpClient.
- **Session identity**: no login; server session cookie enables “edit during the session” as requested.

---

## Getting Started

### Prerequisites
- Java 17 (e.g., Temurin 17)
- Node 18+ and npm
- Maven

### Run the backend (Spring Boot)
From the project root:
```bash
mvn -DskipTests spring-boot:run
```
- H2 database runs in file mode at `./data/appdb`.
- Optional H2 console: `http://localhost:8080/h2-console` (JDBC: `jdbc:h2:file:./data/appdb`, user `sa`, empty password).

### Run the frontend (Angular)
From the project root:
```bash
cd frontend
npm start
```
Open `http://localhost:4200`.
- Dev proxy routes `/api/*` to `http://localhost:8080`.

---

## Running Tests

### Backend (Spring Boot)
From the project root:
```bash
mvn -DskipITs test
```
- Uses an in-memory H2 for tests (`jdbc:h2:mem:`) and `create-drop` schema.
- Includes:
  - `SectorControllerTest`: verifies `/api/sectors` returns ordered sectors.
  - `SubmissionControllerTest`: validates input, persists submission, and refills via session (`/api/submissions`, `/api/submissions/me`).

### Frontend (Angular)
From `frontend/` directory:
```bash
npm test
```
- Runs Karma with Chrome using Angular’s default test runner.
- Includes:
  - `app.component.spec.ts`: mocks `/api/sectors` and `/api/submissions/me`, verifies reactive form validity and data load.

---

## API
- `GET /api/sectors`
  - Returns a flat, ordered list: `[{ id, name, parentId, sortOrder }]`
  - The client computes indentation depth from `parentId`.

- `GET /api/submissions/me`
  - Returns current session’s submission, e.g. `{ id, name, agreeToTerms, sectorIds }`, or empty if none exists yet.

- `POST /api/submissions`
  - Request body (validated):
```json
{ "name": "Alice", "sectorIds": [1, 18], "agreeToTerms": true }
```
  - Returns the stored submission.

### Validation rules
- `name`: required, max length 200
- `sectorIds`: required (at least one)
- `agreeToTerms`: must be true

---

## Data Model
- `Sector`
  - `id`: numeric ids from the original sector list
  - `parentId`: preserves the hierarchy for indentation
  - `sortOrder`: reproduces the original display order
  - Seeded at startup only if empty (idempotent)

- `UserSubmission`
  - `name`: string
  - `agreeTerms`: boolean
  - `sectors`: Many-to-Many relation to `Sector`

Seeding uses a Spring `ApplicationRunner` to avoid SQL-escaping pitfalls and ensure idempotency with the persistent H2 file DB.

---

## Session Behavior (Task 3.4)
- On first Save, a submission is created; its id is stored in the HTTP session.
- Subsequent Saves in the same session update that same record.
- Session ends when the browser is closed (default session cookie behavior). No login.

---

## Project Structure (key parts)
```
backend (Spring Boot, Maven)
└─ src/main/java/com/example/demo
   ├─ HelmesTechnicalApplication.java
   ├─ sector/
   │  ├─ Sector.java
   │  ├─ SectorRepository.java
   │  └─ SectorDataLoader.java
   └─ submission/
      ├─ UserSubmission.java
      ├─ UserSubmissionRepository.java
      ├─ SubmissionRequest.java
      ├─ SubmissionResponse.java
      └─ SubmissionController.java

frontend (Angular)
└─ src/
   ├─ app/
   │  ├─ app.component.ts
   │  └─ app.component.html
   ├─ index.html
   └─ styles.css
```
## Notes
- Sectors are served from the backend and rendered by Angular with proper indentation computed client-side.
