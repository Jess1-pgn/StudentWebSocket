package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.Student;
import dao.StudentDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * REST API Servlet for Student Management.
 * Handles all CRUD operations for students.
 */
@WebServlet("/api/students")
public class StudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentDAO studentDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        studentDAO = new StudentDAO();
        gson = new Gson();
    }

    /**
     * Handles GET requests.
     * - GET /api/students - Returns all students
     * - GET /api/students?id=1 - Returns a single student
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        setCorsHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String idParam = request.getParameter("id");
        
        try {
            if (idParam != null && !idParam.isEmpty()) {
                // Get single student by ID
                int id = Integer.parseInt(idParam);
                Student student = studentDAO.getStudentById(id);
                
                if (student != null) {
                    sendJsonResponse(response, HttpServletResponse.SC_OK, true, 
                                   "Student retrieved successfully", student);
                } else {
                    sendJsonResponse(response, HttpServletResponse.SC_NOT_FOUND, false, 
                                   "Student not found", null);
                }
            } else {
                // Get all students
                List<Student> students = studentDAO.getAllStudents();
                sendJsonResponse(response, HttpServletResponse.SC_OK, true, 
                               "Students retrieved successfully", students);
            }
        } catch (NumberFormatException e) {
            sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, 
                           "Invalid student ID format", null);
        } catch (Exception e) {
            e.printStackTrace();
            sendJsonResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, false, 
                           "Internal server error: " + e.getMessage(), null);
        }
    }

    /**
     * Handles POST requests - Creates a new student.
     * Expects JSON body with student data (without ID).
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        setCorsHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            String jsonBody = getRequestBody(request);
            Student student = gson.fromJson(jsonBody, Student.class);
            
            // Validate input
            if (student == null || student.getFirstNameStudent() == null || 
                student.getLastNameStudent() == null || student.getDateBirthStudent() == null) {
                sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, 
                               "Invalid student data. All fields are required.", null);
                return;
            }
            
            boolean success = studentDAO.addStudent(student);
            
            if (success) {
                sendJsonResponse(response, HttpServletResponse.SC_CREATED, true, 
                               "Student created successfully", student);
            } else {
                sendJsonResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, false, 
                               "Failed to create student", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, 
                           "Invalid JSON format: " + e.getMessage(), null);
        }
    }

    /**
     * Handles PUT requests - Updates an existing student.
     * Expects JSON body with complete student data (including ID).
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        setCorsHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            String jsonBody = getRequestBody(request);
            Student student = gson.fromJson(jsonBody, Student.class);
            
            // Validate input
            if (student == null || student.getIdStudent() <= 0 || 
                student.getFirstNameStudent() == null || student.getLastNameStudent() == null || 
                student.getDateBirthStudent() == null) {
                sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, 
                               "Invalid student data. All fields including ID are required.", null);
                return;
            }
            
            // Check if student exists
            Student existingStudent = studentDAO.getStudentById(student.getIdStudent());
            if (existingStudent == null) {
                sendJsonResponse(response, HttpServletResponse.SC_NOT_FOUND, false, 
                               "Student not found", null);
                return;
            }
            
            boolean success = studentDAO.updateStudent(student);
            
            if (success) {
                sendJsonResponse(response, HttpServletResponse.SC_OK, true, 
                               "Student updated successfully", student);
            } else {
                sendJsonResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, false, 
                               "Failed to update student", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, 
                           "Invalid JSON format: " + e.getMessage(), null);
        }
    }

    /**
     * Handles DELETE requests - Deletes a student.
     * Expects id parameter in query string.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        setCorsHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String idParam = request.getParameter("id");
        
        try {
            if (idParam == null || idParam.isEmpty()) {
                sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, 
                               "Student ID is required", null);
                return;
            }
            
            int id = Integer.parseInt(idParam);
            
            // Check if student exists
            Student existingStudent = studentDAO.getStudentById(id);
            if (existingStudent == null) {
                sendJsonResponse(response, HttpServletResponse.SC_NOT_FOUND, false, 
                               "Student not found", null);
                return;
            }
            
            boolean success = studentDAO.deleteStudent(id);
            
            if (success) {
                sendJsonResponse(response, HttpServletResponse.SC_OK, true, 
                               "Student deleted successfully", null);
            } else {
                sendJsonResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, false, 
                               "Failed to delete student", null);
            }
        } catch (NumberFormatException e) {
            sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, 
                           "Invalid student ID format", null);
        } catch (Exception e) {
            e.printStackTrace();
            sendJsonResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, false, 
                           "Internal server error: " + e.getMessage(), null);
        }
    }

    /**
     * Handles OPTIONS requests for CORS preflight.
     */
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Sets CORS headers for cross-origin requests.
     * Note: Access-Control-Allow-Origin is set to '*' for development/testing.
     * In production, restrict to specific trusted domains for security.
     */
    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Max-Age", "3600");
    }

    /**
     * Reads the request body and returns it as a string.
     */
    private String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    /**
     * Sends a JSON response with standard format.
     */
    private void sendJsonResponse(HttpServletResponse response, int statusCode, 
                                 boolean success, String message, Object data) throws IOException {
        response.setStatus(statusCode);
        
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", success);
        jsonResponse.addProperty("message", message);
        
        if (data != null) {
            jsonResponse.add("data", gson.toJsonTree(data));
        }
        
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(jsonResponse));
        out.flush();
    }
}
