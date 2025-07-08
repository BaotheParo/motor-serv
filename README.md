# Motorcycle Parts Management System

A web-based application for managing motorcycle parts, customers, and orders, built with **Spring Boot**, **PostgreSQL**, and **JSP**. This system provides a user-friendly interface and RESTful API for CRUD operations, along with sales reporting capabilities.

## Table of Contents
- [Features](#features)
- [Technologies](#technologies)
- [Project Structure](#project-structure)
- [Setup Instructions](#setup-instructions)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Screenshots](#screenshots)
- [Contributing](#contributing)

## Features
- **Manage Parts**: Create, read, update, and delete motorcycle parts with details like name, price, and stock.
- **Manage Customers**: Add, view, update, and delete customer information (name, email, phone).
- **Manage Orders**: Create, view, update, and delete orders, with automatic stock updates and total price calculation.
- **Search Functionality**: Search parts and customers by name, and orders by customer ID.
- **Sales Reporting**: View total revenue, revenue by customer, and revenue by date range, with PDF export.
- **RESTful API**: Expose endpoints for programmatic access to parts, customers, and orders.
- **Validation**: Input validation to ensure data integrity (e.g., positive price/stock, valid email).
- **Pagination**: Paginated display for parts list to improve performance.

## Technologies
- **Backend**: Spring Boot 3.5.3, Spring Data JPA, Spring MVC
- **Frontend**: JSP (JavaServer Pages) with JSTL
- **Database**: PostgreSQL
- **Dependencies**:
  - `spring-boot-starter-web`
  - `spring-boot-starter-data-jpa`
  - `postgresql`
  - `itextpdf` (for PDF generation)
  - `jakarta.servlet.jsp.jstl` (for JSP support)
  - `tomcat-embed-jasper` (for JSP rendering)
- **Build Tool**: Maven
- **Java Version**: 21

## Project Structure
```
motorcycle-parts/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/motorcycleparts/
│   │   │   ├── MotorcyclePartsManagementApplication.java
│   │   │   ├── controller/
│   │   │   │   ├── ApiController.java
│   │   │   │   └── WebController.java
│   │   │   ├── entity/
│   │   │   │   ├── Customer.java
│   │   │   │   ├── Order.java
│   │   │   │   └── Part.java
│   │   │   ├── repository/
│   │   │   │   ├── CustomerRepository.java
│   │   │   │   ├── OrderRepository.java
│   │   │   │   └── PartRepository.java
│   │   │   ├── service/
│   │   │   │   ├── CustomerService.java
│   │   │   │   ├── OrderService.java
│   │   │   │   └── PartService.java
│   │   ├── resources/
│   │   │   └── application.properties
│   │   └── webapp/WEB-INF/views/
│   │       ├── customers.jsp
│   │       ├── index.jsp
│   │       ├── orders.jsp
│   │       ├── parts.jsp
│   │       └── report.jsp
│   └── test/
│       └── java/com/motorcycleparts/
│           └── MotorcyclePartsManagementApplicationTests.java
└── README.md
```

## Setup Instructions
### Prerequisites
- **Java**: JDK 21
- **Maven**: 3.6.0 or higher
- **PostgreSQL**: 12 or higher
- **IDE**: IntelliJ IDEA, Eclipse, or any Java-compatible IDE
- **Postman** (optional): For testing API endpoints

### Steps
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-username/motorcycle-parts.git
   cd motorcycle-parts
   ```

2. **Set Up PostgreSQL**:
   - Create a database named `motorcycle_parts`:
     ```sql
     CREATE DATABASE motorcycle_parts;
     ```
   - Update `src/main/resources/application.properties` with your PostgreSQL credentials:
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/motorcycle_parts
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     spring.jpa.hibernate.ddl-auto=update
     spring.jpa.show-sql=true
     spring.mvc.view.prefix=/WEB-INF/views/
     spring.mvc.view.suffix=.jsp
     spring.jpa.open-in-view=false
     logging.level.org.springframework=INFO
     ```

3. **Build the Project**:
   ```bash
   mvn clean install
   ```

4. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```

5. **Access the Application**:
   - Web interface: `http://localhost:8080/`
   - API endpoints: `http://localhost:8080/api/`

## Usage
1. **Web Interface**:
   - **Home Page**: Access `http://localhost:8080/` to view the main menu.
   - **Manage Parts**: Go to `/parts` to add, edit, delete, or search parts.
   - **Manage Customers**: Go to `/customers` to manage customer information.
   - **Manage Orders**: Go to `/orders` to create, update, or delete orders.
   - **Sales Report**: Go to `/report` to view revenue statistics or export to PDF.

2. **API Usage** (via Postman or cURL):
   - **Get all parts**: `GET http://localhost:8080/api/parts`
   - **Create a part**: `POST http://localhost:8080/api/parts`
     ```json
     {
         "name": "Thai Bao",
         "price": 50.0,
         "stock": 100
     }
     ```
   - **Update a part**: `PUT http://localhost:8080/api/parts/1`
     ```json
     {
         "name": "Updated Thai Bao",
         "price": 60.0,
         "stock": 90
     }
     ```
   - **Delete a part**: `DELETE http://localhost:8080/api/parts/1`

## API Endpoints
| Method | Endpoint                     | Description                     |
|--------|------------------------------|---------------------------------|
| GET    | `/api/customers`             | Get all customers              |
| GET    | `/api/customers/{id}`        | Get customer by ID             |
| POST   | `/api/customers`             | Create a new customer          |
| PUT    | `/api/customers/{id}`        | Update a customer              |
| DELETE | `/api/customers/{id}`        | Delete a customer              |
| GET    | `/api/parts`                 | Get all parts                  |
| GET    | `/api/parts/{id}`            | Get part by ID                 |
| POST   | `/api/parts`                 | Create a new part              |
| PUT    | `/api/parts/{id}`            | Update a part                  |
| DELETE | `/api/parts/{id}`            | Delete a part                  |
| GET    | `/api/orders`                | Get all orders                 |
| GET    | `/api/orders/{id}`           | Get order by ID                |
| POST   | `/api/orders`                | Create a new order             |
| PUT    | `/api/orders/{id}`           | Update an order                |
| DELETE | `/api/orders/{id}`           | Delete an order                |

## Contributing
Contributions are welcome! Please follow these steps:
1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Make your changes and commit (`git commit -m "Add your feature"`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Open a Pull Request.

---

*Developed by Cao Thai Bao (Ben).*