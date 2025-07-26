# Compar-Car

A comprehensive web application for comparing car data from user input, built with Java Spring Boot 4, React 19, and PostgreSQL.

## Features

- **Car Data Management**: Add, view, and compare car information
- **Advanced Filtering**: Filter cars by multiple criteria with ascending/descending sorting
- **Pagination**: View 20 cars per page with navigation
- **Data Validation**: Comprehensive backend validation for all car attributes
- **Modern UI**: Responsive React interface with dropdown selections
- **Domain-Driven Design**: Clean architecture with domain models and business logic
- **Layered Validation**: DTO validation in controller layer, domain validation in service layer

## Car Attributes

- Car model
- Manufacturing year
- Engine volume
- Body type (station wagon, hatchback, crossover, etc.)
- Fuel type
- Trunk size
- Fuel consumption
- Average service price (EUR)
- Car price (EUR)
- Mileage

## Technology Stack

### Backend
- Java 21
- Spring Boot 4
- Spring Data JPA with Specifications
- PostgreSQL
- Gradle
- MapStruct (Object Mapping)
- Lombok (Code Generation)
- Domain-Driven Design
- Bean Validation (Jakarta Validation)

### Frontend
- React 19
- TypeScript
- Vite
- React Router
- React Hook Form
- React Query
- Tailwind CSS

### Infrastructure
- Docker & Docker Compose
- PostgreSQL

## Backend Architecture

The backend follows a clean architecture pattern with:

- **Domain Model**: `Car` - Contains business logic and validation
- **Entity**: `CarEntity` - JPA entity for database persistence
- **DTO**: `CarDto` - Data transfer objects for API communication with validation
- **Mappers**: 
  - `CarDtoMapper` - Maps between DTO and domain model (Controller layer)
  - `CarEntityMapper` - Maps between domain model and entity (Service layer)
- **Repository**: `CarRepository` - Data access layer
- **Service**: `CarService` - Business logic orchestration (works with domain models)
- **Controller**: `CarController` - REST API endpoints (handles DTOs and validation)

### Validation Layers

1. **Controller Layer**: DTO validation using `@Valid` annotations
2. **Service Layer**: Domain model validation using business logic methods
3. **Entity Layer**: JPA and database-level constraints

## Quick Start

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd compar-car
   ```

2. **Run with Docker Compose**
   ```bash
   docker-compose up -d
   ```

3. **Access the application**
   - Frontend: http://localhost:3000
   - Backend API: http://localhost:8080
   - Database: localhost:5432

## Development Setup

### Prerequisites
- Java 21
- Node.js 18+
- PostgreSQL 15+
- Docker & Docker Compose

### Backend Development
```bash
cd backend
./gradlew bootRun
```

### Frontend Development
```bash
cd frontend
npm install
npm run dev
```

## API Documentation

The backend provides RESTful APIs for:
- `GET /api/cars` - List cars with filtering and pagination
- `POST /api/cars` - Add new car (with validation)
- `GET /api/cars/{id}` - Get car by ID
- `PUT /api/cars/{id}` - Update car (with validation)
- `DELETE /api/cars/{id}` - Delete car
- `GET /api/cars/body-types` - Get available body types
- `GET /api/cars/fuel-types` - Get available fuel types

### Validation

All POST and PUT endpoints validate the request body using:
- `@Valid` annotations for DTO validation
- Comprehensive validation rules on CarDto fields
- Domain model validation in service layer
- Detailed error responses with field-specific messages

## Project Structure

```
compar-car/
├── backend/                 # Spring Boot application
│   ├── src/main/java/com/comparcar/
│   │   ├── controller/      # REST controllers (DTO handling)
│   │   ├── service/         # Business logic (domain models)
│   │   ├── repository/      # Data access
│   │   ├── model/          # Domain models and entities
│   │   ├── dto/            # Data transfer objects with validation
│   │   ├── mapper/         # MapStruct mappers
│   │   │   ├── CarDtoMapper    # DTO ↔ Domain mapping
│   │   │   └── CarEntityMapper # Domain ↔ Entity mapping
│   │   ├── specification/  # JPA specifications
│   │   └── exception/      # Global exception handling
│   ├── build.gradle        # Gradle build configuration
│   └── Dockerfile          # Backend container
├── frontend/               # React application
├── docker-compose.yml      # Docker orchestration
└── README.md              # This file
```

## Domain Model Features

The `Car` domain model includes business logic methods:
- `isNewCar()` - Check if car is from current or previous year
- `isElectric()` - Check if car is electric
- `isHybrid()` - Check if car is hybrid
- `isEcoFriendly()` - Check if car uses eco-friendly fuel
- `getTotalCostOfOwnership()` - Calculate total cost including service
- `isGoodValueForMoney()` - Evaluate value proposition
- `isFuelEfficient()` - Check fuel efficiency
- `isSpacious()` - Evaluate trunk space
- `isRecentModel()` - Check if car is recent
- `isHighMileage()` / `isLowMileage()` - Mileage evaluation
- `isValidForComparison()` - Comprehensive validation

## Mapping Architecture

The application uses a two-mapper approach for clean separation of concerns:

1. **CarDtoMapper** (Controller Layer):
   - Maps between `CarDto` and `Car` domain model
   - Handles API request/response transformations
   - Works with validated DTOs

2. **CarEntityMapper** (Service Layer):
   - Maps between `Car` domain model and `CarEntity`
   - Handles persistence layer transformations
   - Works with validated domain models
