#  Auth-backend

## API Specs documentation: https://jdevtree-org.github.io/jdevtree-documentation/

The `auth-backend` is the authentication and authorization microservice of **jdevtree** — a modular, production-grade developer platform built to showcase fullstack skills, modern architecture, and cloud-native design.

This service handles all identity-related operations, including:

- OAuth2 login (GitHub)
- Local username/password login
- JWT-based authentication and token rotation
- Redis-based refresh token reuse detection
- Secure registration and password management
- GitHub account linking
- Centralized response format
- PII masking and CSRF protection (in progress)

---

## Tech Stack

- **Spring Boot 3.x** (Java 17+)
- **Gradle** build system
- **Spring Security** with OAuth2 + JWT
- **Redis** (for refresh token storage and blacklist)
- **PostgreSQL** (for user persistence)
- **Docker** (containerized services)
- **WebClient** (for external GitHub API calls)
- **Swagger / OpenAPI** (planned)
- **CI/CD** with GitHub Actions (planned)
- **Deployed to AWS Free Tier** via Terraform (planned)

---

## ⚙️ How to Build and Run

### 🚧 Prerequisites

- Java 17+
- Docker (for Redis or PostgreSQL if running locally)
- Redis Cloud URL (or local Redis)
- PostgreSQL DB running (or use Docker)
- Gradle installed or use the included wrapper (`./gradlew`)

### 🧪 Local Development Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/jdevtree-auth-backend.git
   cd auth-backend


## Running Locally

Please ensure that you have met the prerequisites

### Build the project

```bash
./gradlew clean build
```

### Run the App

```bash
./gradlew bootRun
```

### Run via Docker (Optional)

```bash
docker run -d -p 6379:6379 --name redis redis:7
```

## Environment Variables

```properties
SPRING_REDIS_HOST=localhost
SPRING_REDIS_PORT=6379

GITHUB_CLIENT_ID=your_client_id
GITHUB_CLIENT_SECRET=your_client_secret

JWT_SECRET=your_secure_key
```

## Deployment

- Docker + Docker Compose
- GitHub Actions (CI/CD planned)
- Terraform for AWS provisioning (planned)
- Free-tier AWS: EC2, RDS, S3, Secrets Manager, CloudWatch

## Part of jdevTree Microservice Suite
- auth-backend	Handles auth flows and JWT security
- user-backend	Manages user profiles and roles
- project	Developers' portfolio projects
- marketplace	Plugin sharing and monetization
- notification	Event-based email/in-app notifications
- analytics	Usage tracking and engagement metrics

## Author

just another senior engineer that likes to build stuff and try out different tech stack
