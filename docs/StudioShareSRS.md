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

### 2.1 Customer Stories

- **US-1 — Search for Creative Spaces**  
  *Story:* As a customer, I want to search for available creative spaces so that I can find a space that meets my needs.  
  *Acceptance:*

      Scenario: Customer searches for a creative space
        Given the customer is on the StudioShare search page
        When the customer enters their desired location, date, and type of space
        Then the system displays available spaces that match the customer's search criteria


- **US-2 — Book a Time Slot**  
  *Story:* As a customer, I want to book an available time slot so that I can reserve a creative space for a specific date and time.  
  *Acceptance:*

      Scenario: Customer books an available space
        Given the customer has selected a space with an available time slot
        When the customer selects the date and time and confirms the booking
        Then the system creates the reservation and displays a booking confirmation

- **US-3 — View My Bookings**  
  *Story:* As a customer, I want to view my current and past bookings so that I can keep track of the spaces I have reserved.  
  *Acceptance:*

      Scenario: Customer views booking history
        Given the customer is logged into their account
        When the customer opens the bookings section
        Then the system displays their upcoming and previous reservations                


- **US-4 — View Reviews**  
  *Story:* As a customer, I want to read reviews from previous customers so that I can make a more informed decision before booking a space.  
  *Acceptance:*

      Scenario: Customer views reviews
        Given the customer is viewing a creative space listing
        When the customer opens the reviews section
        Then the system displays ratings and reviews submitted by previous customers



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

## 4. Assumptions, Constraints, and Policies

### Assumptions
- Customers and providers have access to the internet and a device capable of using StudioShare.
- Users will provide accurate and up-to-date account and contact information.
- Providers are responsible for keeping their space availability, pricing, equipment lists, and images accurate.
- Customers are expected to follow the rules established by the provider when using a booked space.
- Providers are assumed to have the legal right to rent or list the spaces they post on StudioShare.

### Constraints
- A space can only be booked during time slots marked as available by the provider.
- Two customers cannot reserve the same space for overlapping time periods.
- Users must create an account and log in before making or managing a reservation.
- StudioShare depends on internet connectivity and may not function properly without an active connection.
- Payment, insurance, and other third-party services may depend on external service providers.

### Policies
- Customers must follow cancellation and refund policies associated with their reservations.
- Providers must accurately describe their spaces, equipment, pricing, and availability.
- Reviews should only be submitted by customers who have completed a booking for that space.
- Users may not submit false, abusive, or misleading reviews or listings.
- Personal information must be handled according to StudioShare's privacy and security policies.
- Customers may be responsible for damage caused to a space or its equipment during their reservation.


## 5. Milestones (course-aligned)

- **M1 Requirements** — This SRS document and user stories opend as issues
- **M2 High-fidelity prototype** — Core customer and provider flows fully interactive.
- **M3 Design** — Architecture, schema, and API outline.
- **M4 Backend API** — Key endpoints and tests.
- **M5 Increment** — At least two use cases completed end-to-end.
- **M6 Final** — Complete system and documentation.


## 6. Change Management

- Changes to requirements, features, or user stories should be tracked through GitHub issues.
- Significant changes should be reviewed by the team before being added to the main version of the SRS.
- When a feature is added, removed, or significantly changed, the corresponding user stories, requirements, and acceptance criteria should also be updated.
- Major changes to StudioShare's scope, such as adding new user roles or major features, should result in an updated version of this SRS.
- Git commit history and pull requests will be used to maintain a record of changes made to the project documentation.

---