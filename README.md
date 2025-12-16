# StudentWebSocket

A Java-based Student Management System using WebSocket for real-time communication and MySQL for data persistence.

## Features

- Real-time student data synchronization using WebSocket
- CRUD operations (Create, Read, Update, Delete) for student records
- MySQL database integration
- Responsive web interface
- Automatic broadcast of changes to all connected clients

## Technology Stack

- **Java 8**
- **WebSocket API (javax.websocket)**
- **Servlet API 4.0**
- **MySQL 8.0**
- **Maven** (Build tool)
- **Gson** (JSON processing)
- **HTML/CSS/JavaScript** (Frontend)

## Project Structure

```
StudentWebSocket/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── dao/
│   │   │   │   ├── Student.java          # Student entity class
│   │   │   │   └── StudentDAO.java       # Data Access Object for CRUD operations
│   │   │   ├── websocket/
│   │   │   │   └── StudentWebSocketEndpoint.java  # WebSocket endpoint
│   │   │   └── util/
│   │   │       └── DatabaseConnection.java        # Database connection utility
│   │   ├── resources/
│   │   │   └── schema.sql               # Database schema
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   └── web.xml              # Web application configuration
│   │       └── index.html               # Frontend interface
│   └── test/
│       └── java/                        # Test classes
├── pom.xml                              # Maven configuration
└── README.md                            # This file
```

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Maven 3.6 or higher
- MySQL Server 8.0 or higher
- Apache Tomcat 9.0 or higher (or any Java EE compatible server)

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/Jess1-pgn/StudentWebSocket.git
cd StudentWebSocket
```

### 2. Setup MySQL Database

Start your MySQL server and execute the schema script:

```bash
mysql -u root -p < src/main/resources/schema.sql
```

Or manually execute the SQL commands:

```sql
CREATE DATABASE IF NOT EXISTS studentdb;
USE studentdb;

CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    dateBirth DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### 3. Configure Database Connection

Edit `src/main/java/util/DatabaseConnection.java` if needed to match your MySQL configuration:

```java
private static final String URL = "jdbc:mysql://localhost:3306/studentdb";
private static final String USER = "root";
private static final String PASSWORD = "root";
```

### 4. Build the Project

```bash
mvn clean package
```

This will create a WAR file in the `target/` directory named `student-websocket.war`.

### 5. Deploy to Application Server

**Option 1: Using Tomcat**
- Copy the `student-websocket.war` file to your Tomcat's `webapps/` directory
- Start Tomcat server
- The application will be available at: `http://localhost:8080/student-websocket/`

**Option 2: Using Maven Tomcat Plugin**
Add this plugin to your `pom.xml` and run:

```bash
mvn tomcat7:run
```

## Usage

### Web Interface

1. Open your browser and navigate to `http://localhost:8080/student-websocket/`
2. The connection status will show "Connected" when WebSocket is established
3. Use the form to add new students
4. View all students in the table below
5. Use "Edit" to update student information
6. Use "Delete" to remove a student
7. All changes are broadcast in real-time to all connected clients

### WebSocket API

The WebSocket endpoint is available at: `ws://localhost:8080/student-websocket/student`

#### Message Format

**Request:**
```json
{
  "action": "add|update|delete|get|list",
  "id": 1,              // For get, delete operations
  "data": {             // For add, update operations
    "firstName": "John",
    "lastName": "Doe",
    "dateBirth": "2000-01-15"
  }
}
```

**Response:**
```json
{
  "action": "add|update|delete|get|list",
  "success": true,
  "message": "Operation message",
  "data": { ... }
}
```

#### Supported Actions

- **list**: Get all students
- **get**: Get a specific student by ID
- **add**: Add a new student
- **update**: Update an existing student
- **delete**: Delete a student by ID

## Student Entity

The `Student` class represents a student with the following attributes:

- `idStudent` (int): Unique identifier
- `firstNameStudent` (String): Student's first name
- `lastNameStudent` (String): Student's last name
- `dateBirthStudent` (Date): Student's date of birth

## Development

### Running Tests

```bash
mvn test
```

### Building without Tests

```bash
mvn clean package -DskipTests
```

### Cleaning the Project

```bash
mvn clean
```

## Troubleshooting

### Database Connection Issues
- Ensure MySQL server is running
- Verify database credentials in `DatabaseConnection.java`
- Check if the `studentdb` database exists
- Verify MySQL JDBC driver is included in the dependencies

### WebSocket Connection Issues
- Ensure your application server supports WebSocket (Tomcat 8+, Jetty 9+, etc.)
- Check browser console for WebSocket errors
- Verify the WebSocket URL matches your deployment context

### Port Already in Use
- Change the server port in your application server configuration
- Or stop the process using the port

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is open source and available under the MIT License.

## Contact

For questions or support, please open an issue in the GitHub repository.