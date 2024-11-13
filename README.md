# 📚 Mongo CRUD Student Project

Welcome to the Mongo CRUD Student Project! This project demonstrates a simple CRUD (Create, Read, Update, Delete) application using MongoDB and Spring Boot.

## 🚀 Getting Started

### Prerequisites

- Java 23 or higher
- Maven 3.6.5 or higher
- MongoDB

### 📦 Installation

1. Clone the repository:

  ```bash
  git clone https://github.com/Josepch1/mongo-crud-student.git
  ```

2. Navigate to the project directory:

  ```bash
  cd mongo-crud-student
  ```

3. Build the project:

  ```bash
  ./mvnw clean package
  ```

4. Run:

```bash
  java -jar target/mongo-project-0.0.1-SNAPSHOT.jar
  ```

### 🏃‍♂️ Running the Application

1. Start MongoDB if it's not already running.

2. Run the Spring Boot application:

  ```bash
  mvn spring-boot:run
  ```

### 📋 API Endpoints

- `GET /api/students` - Retrieve all students
- `GET /api/students/paged` - Retrieve students with pagination
- `GET /api/students/email/{email}` - Retrieve a student by email
- `POST /api/students` - Create a new student
- `PUT /api/students/{email}` - Update a student by email
- `DELETE /api/students/{id}` - Delete a student by ID

### 📜 Swagger Documentation

You can access the Swagger UI for this project to explore and test the API endpoints:

- `http://localhost:8080/swagger-ui.html`

This provides a user-friendly interface to interact with the API and view the available endpoints and their details.

### 🛠️ Built With

- Spring Boot
- MongoDB
- Maven

### 🤝 Contributing

1. Fork the repository.

2. Create your feature branch:

  ```bash
  git checkout -b feature/your-feature
  ```

3. Commit your changes:

  ```bash
  git commit -m 'Add some feature'
  ```

4. Push to the branch:

  ```bash
  git push origin feature/your-feature
  ```

5. Open a pull request.


Happy coding! 🎉