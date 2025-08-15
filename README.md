# Job-Advert

A modular Java/Spring Boot application for managing job advertisements, designed for platforms connecting job seekers and employers. It supports CRUD operations on adverts, image/file uploads, user and job management, and robust authorization.

---

## Features

- **Advert Management**
  - Create, update, delete job adverts
  - Assign adverts to jobs and users
  - Upload and manage advert images

- **Job & Category Integration**
  - Link adverts to job entities and categories
  - Search and filter jobs by keywords or category

- **User Management**
  - Associates adverts with users
  - User authorization checks for updates/deletes

- **RESTful API Endpoints**
  - Create, get, update, and delete adverts
  - Get adverts by user, job, or advertiser type

- **Role-Based Access**
  - Only authorized users or admins can update/delete adverts

---

## API Overview

Base path: `/v1/job-service/advert`

| Method | Endpoint                        | Description                        |
|--------|---------------------------------|------------------------------------|
| POST   | `/create`                       | Create new advert                  |
| GET    | `/getAll`                       | Get all adverts                    |
| GET    | `/getAdvertById/{id}`           | Get advert by ID                   |
| GET    | `/getAdvertsByUserId/{id}`      | Get adverts by user & advertiser   |
| PUT    | `/update`                       | Update advert (admin or owner)     |
| DELETE | `/deleteAdvertById/{id}`        | Delete advert (admin or owner)     |

---

## Data Model (Main Entities)

- **Advert**: name, description, deliveryTime, price, status, advertiser, job, userId, imageId
- **Job**: name, description, category, keys, imageId
- **User**: Managed via UserServiceClient

---

## Getting Started

1. **Clone the repository**
   ```bash
   git clone https://github.com/shikhasingh1999/Job-Advert.git
   cd Job-Advert
   ```

2. **Build and Run**
   ```bash
   ./mvnw spring-boot:run
   ```
   Or use your IDE to run the main application.

3. **API Usage Example (Create Advert)**
   ```http
   POST /v1/job-service/advert/create
   Content-Type: multipart/form-data

   {
     "name": "Senior Java Developer",
     "description": "Spring Boot, Hibernate, REST",
     "deliveryTime": 5,
     "price": 2000,
     "advertiser": "EMPLOYER",
     "userId": "user123",
     "jobId": "job456",
     "file": <optional image>
   }
   ```

---

## Technologies Used

- Java 17+
- Spring Boot
- Spring Data JPA
- ModelMapper
- Multipart file handling
- REST API
- Lombok

---


