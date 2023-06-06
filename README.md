# Geographic Distance Service

This project is a REST service that calculates the geographic distance between two postal codes in the UK.

## Prerequisites

- Java 17
- Maven

## Getting Started

1. Clone the repository:

   ```shell
   git clone https://github.com/toytanik/geo-distance-service.git

2. Navigate to the project directory:
cd geographic-distance-service
3. Build the project using Maven:
mvn clean package
4. Run the application:
java -jar target/geographic-distance-service.jar
The application will start running on http://localhost:{AVAILABLE-PORT}.

Database Migration
The application uses Flyway for database migration. The necessary SQL scripts are located in the src/main/resources/db/migration directory. Flyway will automatically apply the migrations during the application startup.


Usage
To calculate the geographic distance between two postal codes, make a GET request to the following endpoint:
GET /distance/{postalCode1}/{postalCode2}
The request requires basic authentication. Provide the username and password as headers:

The response will be a JSON document containing the following information:
{
"postalCode1": "AB12 3CD",
"latitude1": 57.1234,
"longitude1": -2.3456,
"postalCode2": "XY34 5ZA",
"latitude2": 54.9876,
"longitude2": -1.2345,
"distance": 100.5,
"unit": "km"
}

API Documentation
The API documentation is available through Swagger UI. You can access it using the following URL:
http://localhost:{Available-port}/swagger-ui/#/

Postman Collection
A Postman collection is provided with pre-defined requests for testing the API. You can import the collection using the following file:
WCC.postman_collection.json 

Configuration
The application uses an H2 in-memory database to store the postal code coordinates mapping.
The database is initialized with data from the provided SQL file during startup. You can modify the SQL file or update the database as needed.

Running Tests

The project includes both integration tests and unit tests.
If you want to use Testcontainers for running tests in a Docker container, make sure you have Docker installed on your machine.

The integration tests use Testcontainers to spin up a containerized H2 database. The necessary configuration is already included in the project.
