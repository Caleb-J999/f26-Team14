# Requirements – Studio Share

**Project Name:** Studio Share  
**Team:** Caleb James, Clay Hyka  
**Course:** CSC 340  
**Version:** 1.0  
**Date:** 2026-09-16  

---

## 1. Overview

**Vision.** Studio Share is designed for small groups who need short-term access to specialized creative spaces. The platform makes it easier to find and book available creative spaces in one place.

**Glossary**
- **Renter/Customer:** A user who searches for and books creative spaces.
- **Space Owner/Provider:** A user who lists creative spaces and manages bookings.
- **SysAdmin:** A user who manages users, monitors platform activity, and removes inappropriate content.

**Primary Users / Roles.**
- **Renter/Customer** — Searches for creative spaces, checks availability, and books time slots.
- **Space Owner/Provider** — Creates and edits listings and manages bookings for available creative spaces.
- **SysAdmin** — Manages users, monitors platform activity, and removes inappropriate content.

**Scope (this semester).**
- User account creation
- Slot booking
- View available time slots

**Out of scope (deferred).**
- Online payments
- Automatic refund system
- Recommendation system based on reviews

> This document is **requirements-level** and solution-neutral; design decisions such as UI layouts, API endpoints, and schemas are documented separately.

---

## 2. Functional Requirements (User Stories)

### 2.2 Provider Stories

- **US-1 — Earn income from creative spaces**  
  _Story:_ As a provider, I want to list my creative spaces so that I can create more income.  
  _Acceptance:_

  ```gherkin
  Scenario: Provider lists a creative space
    Given I am logged in as a provider
    When I create a listing for an available creative space
    Then the listing should be saved and available for renters to view
  ```

- **US-2 — Edit a listing**  
  _Story:_ As a space owner, I want to be able to edit listings in case any mistakes are made.  
  _Acceptance:_

  ```gherkin
  Scenario: Provider edits an existing listing
    Given I am logged in as a provider and have an existing listing
    When I edit the listing information
    Then the updated information should be saved and displayed
  ```

- **US-3 — Manage bookings**  
  _Story:_ As a provider, I want to view and manage bookings for my creative spaces so that I can keep track of reservations.  
  _Acceptance:_

  ```gherkin
  Scenario: Provider views and manages bookings
    Given I am logged in as a provider and have an active listing
    When I view the bookings for my creative space
    Then I should be able to see the reservation details and manage the booking
  ```

---

## 3. Non-Functional Requirements

- **Performance:** The system should load available creative spaces and time slots within 3 seconds under normal use.
- **Availability/Reliability:** The system should reliably save user accounts, listings, bookings, and listing updates without losing information.
- **Security/Privacy:** Users should only be able to access and modify information associated with their own account and role.
- **Usability:** Renters and providers should be able to complete their main tasks, such as viewing availability, booking spaces, and managing listings, with clear and understandable steps.

---

## 5. Milestones (course-aligned)

- **M1 Requirements** — This SRS document and user stories opend as issues
- **M2 High-fidelity prototype** — Core customer and provider flows fully interactive.
- **M3 Design** — Architecture, schema, and API outline.
- **M4 Backend API** — Key endpoints and tests.
- **M5 Increment** — At least two use cases completed end-to-end.
- **M6 Final** — Complete system and documentation.

---