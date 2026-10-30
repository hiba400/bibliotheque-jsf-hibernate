# Library Management System

**Java EE | JSF | Hibernate | MySQL | Maven**

A comprehensive library management system built with Java EE technologies, demonstrating enterprise Java development with JSF (JavaServer Faces), Hibernate ORM, and MySQL database integration.

## Project Overview

This library management system provides a web-based interface for managing library operations including user management, book catalog management, and administrative functions. The application follows the Model-View-Controller (MVC) architecture pattern with JSF as the presentation layer, Hibernate for data persistence, and MySQL as the database.

### Key Features

- **User Authentication**: Login and registration system with role-based access (ADMIN, ADHERENT)
- **Book Management**: Add, edit, and manage library books
- **User Management**: Admin interface for managing user accounts
- **Database Integration**: MySQL database with Hibernate ORM
- **Role-Based Access**: Admin and regular user (adherent) roles
- **Web Interface**: Responsive JSF web pages

## Technology Stack

| Category | Technologies |
|----------|--------------|
| **Backend** | Java 17, Java EE 10, Jakarta Faces 4.0.2 |
| **ORM** | Hibernate 6.4.4.Final |
| **Database** | MySQL 8.3.0 |
| **Build Tool** | Maven |
| **Application Server** | WildFly |
| **Packaging** | WAR |

## Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                  Web Browser (JSF Pages)                    │
│    index.xhtml, login.xhtml, register.xhtml, admin/*        │
└──────────────────────┬──────────────────────────────────────┘
                       │ HTTP
                       ▼
┌─────────────────────────────────────────────────────────────┐
│                  JSF Managed Beans (View Layer)              │
│                  com.bibliotheque.bean.*                     │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│                  Service Layer (Business Logic)              │
│                  com.bibliotheque.service.*                  │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│                  DAO Layer (Data Access)                    │
│                  com.bibliotheque.dao.*                       │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│                  Hibernate ORM (Persistence)                  │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│                  MySQL Database                              │
│                  bibliotheque_db                               │
└─────────────────────────────────────────────────────────────┘
```

## Repository Structure

```text
bibliotheque-jsf-hibernate/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       ├── bibliotheque/
│   │   │       │   ├── bean/          # JSF Managed Beans
│   │   │       │   ├── config/        # Hibernate configuration
│   │   │       │   ├── dao/           # Data Access Objects
│   │   │       │   ├── model/         # Entity classes
│   │   │       │   └── service/       # Business logic
│   │   │       └── example/          # Example code
│   │   ├── resources/
│   │   │   └── META-INF/
│   │   │       └── persistence.xml   # Hibernate configuration
│   │   └── webapp/
│   │       ├── admin/                # Admin pages
│   │       ├── user/                 # User pages
│   │       ├── resources/            # CSS, JS, images
│   │       ├── WEB-INF/
│   │       │   └── web.xml           # Web application descriptor
│   │       ├── index.xhtml           # Home page
│   │       ├── init.xhtml            # Initialization page
│   │       ├── login.xhtml           # Login page
│   │       ├── register.xhtml        # Registration page
│   │       ├── setup.xhtml           # Database setup
│   │       └── test.html             # Test page
├── init-users.sql                   # Database initialization script
├── mysql-datasource.cli            # WildFly datasource configuration
├── pom.xml                         # Maven configuration
├── mvnw                            # Maven wrapper
├── mvnw.cmd                        # Maven wrapper (Windows)
└── .gitignore                      # Git ignore patterns
```

## Installation

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- WildFly application server (optional, for deployment)

### Database Setup

1. **Create MySQL database**
   ```sql
   CREATE DATABASE bibliotheque_db;
   ```

2. **Run initialization script**
   ```bash
   mysql -u root -p bibliotheque_db < init-users.sql
   ```

   This creates test users:
   - Admin: `admin@biblio.com` / `admin123`
   - User: `user@biblio.com` / `user123`

### Build and Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/hiba400/bibliotheque-jsf-hibernate.git
   cd bibliotheque-jsf-hibernate
   ```

2. **Build with Maven**
   ```bash
   ./mvnw clean package
   ```

3. **Deploy to WildFly**
   ```bash
   ./mvnw wildfly:deploy
   ```

4. **Access the application**
   - Open: `http://localhost:8080/projet_123`

### Local Development

1. **Configure database connection**
   Edit `src/main/resources/META-INF/persistence.xml` with your MySQL credentials

2. **Run with Maven**
   ```bash
   ./mvnw wildfly:run
   ```

## Configuration

### Database Configuration

Configure MySQL connection in `persistence.xml`:

```xml
<property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/bibliotheque_db"/>
<property name="jakarta.persistence.jdbc.user" value="root"/>
<property name="jakarta.persistence.jdbc.password" value="your_password"/>
```

### WildFly Datasource

The project includes `mysql-datasource.cli` for automatic datasource configuration on WildFly deployment.

## User Roles

| Role | Email | Password | Permissions |
|------|-------|----------|-------------|
| ADMIN | admin@biblio.com | admin123 | Full administrative access |
| ADHERENT | user@biblio.com | user123 | Regular user access |

## Pages and Features

### index.xhtml
- Home page with navigation
- Quick access to login and registration

### login.xhtml
- User authentication form
- Email and password validation
- Role-based redirection

### register.xhtml
- New user registration
- Email, password, and name fields
- Default role assignment

### init.xhtml
- Database initialization page
- Schema creation
- Initial data seeding

### setup.xhtml
- Application setup configuration
- Database connection testing

### admin/*
- Administrative interface
- User management
- Book management
- System configuration

### user/*
- User interface
- Book browsing
- User profile management

## Data Models

### User (Utilisateur)
- Email (primary key)
- Password (hashed)
- Name
- Role (ADMIN, ADHERENT)

### Book (Livre)
- ID (primary key)
- Title
- Author
- ISBN
- Availability status

## Security Considerations

- Passwords are stored in plain text in this version (not production-ready)
- Session management via JSF
- Role-based access control
- SQL injection prevention via Hibernate ORM

**Important**: This is an academic project. For production use, implement:
- Password hashing (BCrypt, Argon2)
- HTTPS/TLS encryption
- Input validation and sanitization
- CSRF protection
- Session timeout configuration

## Troubleshooting

### Database Connection Errors

1. Verify MySQL is running
2. Check database credentials in `persistence.xml`
3. Ensure database `bibliotheque_db` exists
4. Test connection with MySQL client

### WildFly Deployment Issues

1. Check WildFly server status
2. Verify WAR file is built successfully
3. Check WildFly logs for errors
4. Ensure datasource is configured

### Maven Build Errors

1. Verify Java version (17+ required)
2. Check Maven installation
3. Clear Maven cache: `./mvnw clean`
4. Update dependencies: `./mvnw dependency:purge-local-repository`

## Implementation Status

| Feature | Status | Notes |
|---------|--------|-------|
| User Authentication | ✅ Complete | Login/Register with roles |
| Database Integration | ✅ Complete | MySQL with Hibernate |
| JSF Pages | ✅ Complete | All UI pages implemented |
| Admin Interface | ✅ Complete | User and book management |
| User Interface | ✅ Complete | User dashboard |
| Password Hashing | ⏳ Not Implemented | Plain text passwords |
| Input Validation | ⏳ Basic | Minimal validation |
| Session Management | ✅ Complete | JSF session scope |
| CSRF Protection | ⏳ Not Implemented | |

## Future Enhancements

- Implement password hashing (BCrypt)
- Add input validation and sanitization
- Implement CSRF protection
- Add email verification for registration
- Implement password reset functionality
- Add book borrowing and return system
- Implement search and filtering
- Add analytics and reporting
- Deploy to production server with HTTPS

## License

No license has been selected for this project. All rights reserved to the author.

## Author

**Hiba Ben Adou**

Computer Engineering Student – ENSA Tangier

This project demonstrates enterprise Java development with JSF, Hibernate ORM, and MySQL, following MVC architecture patterns and Java EE best practices.
