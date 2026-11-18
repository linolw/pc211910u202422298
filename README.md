# Facturease Peru - Corporate Electronic Invoice API

**Author:** Replace With Your Full Name

## Purpose
REST API for registering corporate electronic invoices using Domain-Driven Design,
CQRS commands, layered architecture, repository adapters, and Spring Data JPA.

## Technical stack
Java 26 · Spring Boot 4.1.0 · Maven · PostgreSQL · Spring Data JPA · Swagger/OpenAPI.

## API
`POST http://localhost:8097/api/v1/invoices`

Request fields: invoiceIdentifier (UUID string), description (optional), status
(optional, DRAFT by default), netAmount, calculatedTax, itemCount.
The generated `id` is not part of the POST request.

Successful response: HTTP 201 containing id, invoiceId, description, status,
netAmountValue, calculatedTaxValue and itemCount.

## Domain invariants
- Unique invoiceIdentifier (domain check and database unique constraint).
- TaxSummary has netAmount and calculatedTax; calculatedTax = netAmount * 0.18 exactly.
- ISSUED or PAID requires itemCount > 0; DRAFT is default.
- Invoice is auditable through the persistence entity.

## Project setup
1. Create PostgreSQL database `facturease`.
2. Execute `db/create_schema.sql` against that database in pgAdmin.
3. Use JDK 26 in IntelliJ Project SDK and Maven Runner.
4. Set local environment variable `DB_PASSWORD` (never commit a real password).
5. Run `./mvnw.cmd clean compile` then `./mvnw.cmd spring-boot:run` in PowerShell.
6. Open `http://localhost:8097/swagger-ui/index.html`.
7. Run the JSON examples with `curl.exe` from the project root.
8. Verify rows with `db/verify_schema.sql`.

## Errors and internationalization
Validation and business-rule errors use HTTP Problem Details; messages support
`Accept-Language: en-US` and `Accept-Language: es-PE`.

## Scope
Only POST /api/v1/invoices is implemented. No CORS, Security, or test suite.
