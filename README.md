# Student Management REST API

A complete REST API for student management built with Java Servlets, MySQL, and modern HTML/CSS/JavaScript interface. Designed for deployment on WildFly application server.

## 🎯 Features

- **Complete CRUD Operations**: Create, Read, Update, and Delete students
- **REST API**: Clean REST endpoints with JSON responses
- **Modern Web Interface**: Responsive HTML/CSS/JavaScript interface with AJAX
- **Secure**: Uses PreparedStatements to prevent SQL injection
- **CORS Support**: Cross-origin resource sharing enabled
- **MySQL Database**: Persistent storage with MySQL
- **WildFly Compatible**: Ready for deployment on WildFly server

## 📋 Prerequisites

- Java 8 or higher
- Maven 3.6+
- MySQL 5.7+ or MySQL 8.0+
- WildFly 10+ or any Java EE 7+ compatible application server

## 🗄️ Database Setup

1. Install MySQL and start the MySQL service

2. Run the database schema:
```bash
mysql -u root -p < schema.sql
```

Or manually create the database:
```sql
CREATE DATABASE IF NOT EXISTS studentdb;
USE studentdb;

CREATE TABLE IF NOT EXISTS students (
    idStudent INT AUTO_INCREMENT PRIMARY KEY,
    firstNameStudent VARCHAR(100) NOT NULL,
    lastNameStudent VARCHAR(100) NOT NULL,
    dateBirthStudent DATE NOT NULL
);
```

3. Update database credentials in `src/main/java/util/DatabaseConnection.java` if needed (default: root/root)

## 🚀 Build and Deploy

### Build the project:
```bash
mvn clean package
```

This creates `target/student-websocket.war`

### Deploy to WildFly:

**Option 1: Copy WAR file**
```bash
cp target/student-websocket.war $WILDFLY_HOME/standalone/deployments/
```

**Option 2: Use WildFly CLI**
```bash
$WILDFLY_HOME/bin/jboss-cli.sh --connect
deploy target/student-websocket.war
```

**Option 3: Use Admin Console**
1. Access WildFly Admin Console (http://localhost:9990)
2. Navigate to Deployments
3. Upload `student-websocket.war`

## 📡 API Endpoints

### Base URL: `/api/students`

#### Get All Students
```http
GET /api/students
```
**Response:**
```json
{
  "success": true,
  "message": "Students retrieved successfully",
  "data": [
    {
      "idStudent": 1,
      "firstNameStudent": "John",
      "lastNameStudent": "Doe",
      "dateBirthStudent": "2000-01-15"
    }
  ]
}
```

#### Get Student by ID
```http
GET /api/students?id=1
```
**Response:**
```json
{
  "success": true,
  "message": "Student retrieved successfully",
  "data": {
    "idStudent": 1,
    "firstNameStudent": "John",
    "lastNameStudent": "Doe",
    "dateBirthStudent": "2000-01-15"
  }
}
```

#### Create Student
```http
POST /api/students
Content-Type: application/json

{
  "firstNameStudent": "Jane",
  "lastNameStudent": "Smith",
  "dateBirthStudent": "1999-05-20"
}
```
**Response:** Status 201 Created

#### Update Student
```http
PUT /api/students
Content-Type: application/json

{
  "idStudent": 1,
  "firstNameStudent": "John",
  "lastNameStudent": "Doe",
  "dateBirthStudent": "2000-01-15"
}
```
**Response:** Status 200 OK

#### Delete Student
```http
DELETE /api/students?id=1
```
**Response:** Status 200 OK

## 🖥️ Web Interface

After deployment, access the web interface at:
```
http://localhost:8080/student-websocket/
```

Features:
- View all students in a responsive table
- Add new students with a form
- Edit students inline
- Delete students with confirmation
- Real-time updates via AJAX
- Success/error messages

## 🏗️ Project Structure

```
StudentWebSocket/
├── src/main/
│   ├── java/
│   │   ├── dao/
│   │   │   ├── Student.java          # Entity class
│   │   │   └── StudentDAO.java       # Data Access Object
│   │   ├── servlet/
│   │   │   └── StudentServlet.java   # REST API servlet
│   │   └── util/
│   │       └── DatabaseConnection.java # Database utility
│   └── webapp/
│       ├── WEB-INF/
│       │   └── web.xml               # Web configuration
│       └── index.html                # Web interface
├── schema.sql                         # Database schema
├── pom.xml                           # Maven configuration
└── README.md                         # Documentation
```

## 🔒 Security Features

- **PreparedStatements**: All database queries use PreparedStatements to prevent SQL injection
- **Input Validation**: Server-side validation for all inputs
- **Error Handling**: Comprehensive exception handling
- **CORS Configuration**: Controlled cross-origin access

## 🛠️ Technologies Used

- **Backend**: Java Servlets 4.0
- **Database**: MySQL 8.0
- **JSON Processing**: Gson 2.10.1
- **Build Tool**: Maven
- **Application Server**: WildFly (Java EE)
- **Frontend**: HTML5, CSS3, JavaScript (ES6+)

## 📝 HTTP Status Codes

- `200 OK` - Success
- `201 Created` - Resource created successfully
- `400 Bad Request` - Invalid input
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

## 🧪 Testing the API

Using cURL:

```bash
# Get all students
curl http://localhost:8080/student-websocket/api/students

# Get student by ID
curl http://localhost:8080/student-websocket/api/students?id=1

# Create student
curl -X POST http://localhost:8080/student-websocket/api/students \
  -H "Content-Type: application/json" \
  -d '{"firstNameStudent":"Jane","lastNameStudent":"Smith","dateBirthStudent":"1999-05-20"}'

# Update student
curl -X PUT http://localhost:8080/student-websocket/api/students \
  -H "Content-Type: application/json" \
  -d '{"idStudent":1,"firstNameStudent":"John","lastNameStudent":"Doe","dateBirthStudent":"2000-01-15"}'

# Delete student
curl -X DELETE http://localhost:8080/student-websocket/api/students?id=1
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is open source and available under the MIT License.

## 👥 Authors

Student Management System REST API

## 🐛 Troubleshooting

### Database Connection Issues
- Verify MySQL is running: `systemctl status mysql`
- Check credentials in `DatabaseConnection.java`
- Ensure database exists: `mysql -u root -p -e "SHOW DATABASES;"`

### Deployment Issues
- Check WildFly logs: `$WILDFLY_HOME/standalone/log/server.log`
- Verify MySQL JDBC driver is available
- Ensure no port conflicts (8080 default)

### API Not Responding
- Verify deployment: Check WildFly admin console
- Check application context path
- Review browser console for CORS errors