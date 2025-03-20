# ResqueWay

## Project Overview

ResqueWay is a comprehensive healthcare management platform built with Kotlin and Spring Boot. The application facilitates the administration of hospital resources, user access control, and subscription management for healthcare organizations. It provides a secure and efficient way to manage healthcare services through a modern RESTful API architecture.

## Features

### Authentication System
- Secure JWT token-based authentication stored in HTTP-only cookies
- Role-based access control with different user permission levels
- Session management with automatic token expiration
- Protection against common web vulnerabilities

### User Management
- Multiple user types (USER, ADMIN, etc.) defined in the TypeUtilisateur enum
- Complete user profile management
- Email-based user identification
- Secure password storage using BCrypt encryption

### Hospital Management
- Hospital registration and profile management
- Location and contact information tracking
- Service availability monitoring
- Hospital capacity and resource management

### Company/Enterprise Management
- Enterprise registration and profile management
- Association between users and their respective enterprises
- Company-specific settings and configurations
- Administrative control over enterprise resources

### Subscription Management
- Tiered subscription plans
- Subscription status tracking
- Automatic renewal processing
- Usage limitations based on subscription level

### Metrics and Reporting
- System performance monitoring
- Usage statistics
- Operational analytics
- Activity logging and audit trails

## Technical Stack

### Backend
- **Language**: Kotlin 1.8+
- **Framework**: Spring Boot 3.x
- **REST API**: Spring Web MVC
- **Documentation**: Spring Doc/OpenAPI

### Data Layer
- **ORM**: JPA/Hibernate
- **Entities**: Fully annotated JPA entities
- **ID Generation**: UUID-based entity identification
- **Relationships**: Properly defined entity relationships (One-to-One, Many-to-One, etc.)

### Security
- **Framework**: Spring Security
- **Authentication**: JWT (JSON Web Tokens)
- **Password Encryption**: BCrypt via Spring Security
- **Security Headers**: CSRF protection, XSS protection

### DevOps
- **Build Tool**: Gradle
- **Containerization**: Docker support
- **CI/CD**: GitHub Actions ready

## API Endpoints

### Authentication (`AuthController`)
- `POST /api/auth/login` - Authenticate user and generate JWT token
  - Request: Email, password, and client type
  - Response: Sets HTTP-only cookie with JWT token
- `GET /api/auth/me` - Retrieve current user information from JWT token
  - Response: User details including type, company ID, and subscription information
- `POST /api/auth/logout` - Invalidate the current session
  - Response: Clears the authentication cookie

### User Management (`UtilisateurController`)
- `GET /api/utilisateurs` - List all users (admin only)
- `GET /api/utilisateurs/{id}` - Get specific user details
- `POST /api/utilisateurs` - Create new user
- `PUT /api/utilisateurs/{id}` - Update user information
- `DELETE /api/utilisateurs/{id}` - Delete a user
- Additional endpoints for user-specific operations

### Hospital Management (`HopitalController`)
- `GET /api/hopitaux` - List all hospitals
- `GET /api/hopitaux/{id}` - Get specific hospital details
- `POST /api/hopitaux` - Register a new hospital
- `PUT /api/hopitaux/{id}` - Update hospital information
- `DELETE /api/hopitaux/{id}` - Remove a hospital
- Additional endpoints for hospital-specific operations

### Company Management (`EntrepriseController`)
- `GET /api/entreprises` - List all companies
- `GET /api/entreprises/{id}` - Get specific company details
- `POST /api/entreprises` - Register a new company
- `PUT /api/entreprises/{id}` - Update company information
- `DELETE /api/entreprises/{id}` - Remove a company
- Additional endpoints for company-specific operations

### Subscription Management (`AbonnementController`)
- `GET /api/abonnements` - List all subscription plans
- `GET /api/abonnements/{id}` - Get specific subscription details
- `POST /api/abonnements` - Create a new subscription
- `PUT /api/abonnements/{id}` - Update subscription information
- `DELETE /api/abonnements/{id}` - Cancel a subscription
- Additional endpoints for subscription-specific operations

### Metrics and Monitoring (`MetricController`)
- `GET /api/metrics` - Retrieve system metrics
- `GET /api/metrics/usage` - Get usage statistics
- `GET /api/metrics/performance` - Get performance metrics
- Additional endpoints for metrics-specific operations

## Data Models

### User (Utilisateur)
```kotlin
class Utilisateur(
    var id: UUID? = null,
    var nom: String,
    var prenom: String,
    var email: String,
    var password: String, // Automatically hashed
    var typeUtilisateur: TypeUtilisateur,
    var entreprise: Entreprise? = null
)
```

### Hospital (Hopital)
```kotlin
class Hopital(
    var id: UUID? = null,
    val name: String,
    val adresse: String,
    val adresseMail: String,
    // Additional properties
)
```

### Company (Entreprise)
```kotlin
class Entreprise(
    var id: UUID? = null,
    val name: String,
    val abonnement: Abonnement?,
    val adresse: String,
    val adresseMail: String
)
```

### Subscription (Abonnement)
```kotlin
class Abonnement(
    var id: UUID? = null,
    val name: String,
    val description: String,
    val prix: Double,
    // Additional properties
)
```

### User Types (TypeUtilisateur)
```kotlin
enum class TypeUtilisateur {
    USER,
    ADMIN,
    // Additional user types
}
```

## Development

### Environment Setup
1. **Prerequisites**:
   - JDK 17 or newer
   - Gradle 7.5+
   - PostgreSQL/MySQL database

2. **Clone the repository**:
   ```bash
   git clone https://github.com/dragnyon/resqueWay.git
   cd resqueWay
   ```

3. **Configure the database**:
   - Update `application.properties` or `application.yml` with your database connection details

4. **Build the application**:
   ```bash
   ./gradlew clean build
   ```

5. **Run the application**:
   ```bash
   ./gradlew bootRun
   ```

### Security Configuration
- JWT token configuration in `JwtUtil.kt`
- Security filters in the security package
- CORS configuration for cross-origin requests
- Authentication rules in Spring Security configuration

### Code Organization
- **Controller layer**: API endpoints and request handling
- **Service layer**: Business logic implementation
- **Repository layer**: Data access and persistence
- **Model layer**: Entity definitions and relationships
- **DTO layer**: Data transfer objects for API responses
- **Exception layer**: Custom exception handling
- **Security layer**: Authentication and authorization

## Deployment

### Production Recommendations
- Use HTTPS in production environments
- Configure proper CORS policies
- Set secure cookie attributes (Secure, HttpOnly, SameSite)
- Implement rate limiting for API endpoints
- Set up database connection pooling

### Docker Deployment
1. Build the Docker image:
   ```bash
   docker build -t resque-way .
   ```
2. Run the container:
   ```bash
   docker run -p 8080:8080 resque-way
   ```



## License
This project is proprietary software. All rights reserved.
