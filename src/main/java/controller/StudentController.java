package controller;

import com.google.gson.Gson;
import dto.ApiResponse;
import model.Student;
import service.StudentService;
import service.StudentServiceImpl;

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
 * REST API Controller for Student Management.
 * Handles HTTP requests/responses and delegates to service layer.
 * 
 * TIER 1: Presentation Layer
 * - Handles HTTP methods (GET, POST, PUT, DELETE)
 * - Parses request parameters and JSON body
 * - Formats JSON responses using ApiResponse
 * - Sets appropriate HTTP status codes
 * - Handles CORS headers
 * - NO business logic
 * - NO database access
 */
@WebServlet("/api/students")
public class StudentController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private StudentService studentService;
    private Gson gson;

    /**
     * Initialize servlet - dependency injection
     */
    @Override
    public void init() throws ServletException {
        super.init();
        this.studentService = new StudentServiceImpl();
        this.gson = new Gson();
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
                Student student = studentService.getStudentById(id);
                
                if (student != null) {
                    ApiResponse<Student> apiResponse = new ApiResponse<>(true, "Student retrieved successfully", student);
                    sendJsonResponse(response, HttpServletResponse.SC_OK, apiResponse);
                } else {
                    ApiResponse<Student> apiResponse = new ApiResponse<>(false, "Student not found");
                    sendJsonResponse(response, HttpServletResponse.SC_NOT_FOUND, apiResponse);
                }
            } else {
                // Get all students
                List<Student> students = studentService.getAllStudents();
                ApiResponse<List<Student>> apiResponse = new ApiResponse<>(true, "Students retrieved successfully", students);
                sendJsonResponse(response, HttpServletResponse.SC_OK, apiResponse);
            }
        } catch (NumberFormatException e) {
            ApiResponse<Object> apiResponse = new ApiResponse<>(false, "Invalid student ID format");
            sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, apiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse<Object> apiResponse = new ApiResponse<>(false, "Internal server error: " + e.getMessage());
            sendJsonResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, apiResponse);
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
            
            // Call service layer (validation happens there)
            boolean success = studentService.createStudent(student);
            
            if (success) {
                ApiResponse<Student> apiResponse = new ApiResponse<>(true, "Student created successfully", student);
                sendJsonResponse(response, HttpServletResponse.SC_CREATED, apiResponse);
            } else {
                ApiResponse<Object> apiResponse = new ApiResponse<>(false, "Failed to create student");
                sendJsonResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, apiResponse);
            }
        } catch (IllegalArgumentException e) {
            // Validation error from service layer
            ApiResponse<Object> apiResponse = new ApiResponse<>(false, e.getMessage());
            sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, apiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse<Object> apiResponse = new ApiResponse<>(false, "Invalid JSON format: " + e.getMessage());
            sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, apiResponse);
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
            
            // Call service layer (validation happens there)
            boolean success = studentService.updateStudent(student);
            
            if (success) {
                ApiResponse<Student> apiResponse = new ApiResponse<>(true, "Student updated successfully", student);
                sendJsonResponse(response, HttpServletResponse.SC_OK, apiResponse);
            } else {
                ApiResponse<Object> apiResponse = new ApiResponse<>(false, "Failed to update student");
                sendJsonResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, apiResponse);
            }
        } catch (IllegalArgumentException e) {
            // Validation error or student not found from service layer
            if (e.getMessage().contains("not found")) {
                ApiResponse<Object> apiResponse = new ApiResponse<>(false, e.getMessage());
                sendJsonResponse(response, HttpServletResponse.SC_NOT_FOUND, apiResponse);
            } else {
                ApiResponse<Object> apiResponse = new ApiResponse<>(false, e.getMessage());
                sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, apiResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse<Object> apiResponse = new ApiResponse<>(false, "Invalid JSON format: " + e.getMessage());
            sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, apiResponse);
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
                ApiResponse<Object> apiResponse = new ApiResponse<>(false, "Student ID is required");
                sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, apiResponse);
                return;
            }
            
            int id = Integer.parseInt(idParam);
            
            // Call service layer (existence check happens there)
            boolean success = studentService.deleteStudent(id);
            
            if (success) {
                ApiResponse<Object> apiResponse = new ApiResponse<>(true, "Student deleted successfully");
                sendJsonResponse(response, HttpServletResponse.SC_OK, apiResponse);
            } else {
                ApiResponse<Object> apiResponse = new ApiResponse<>(false, "Failed to delete student");
                sendJsonResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, apiResponse);
            }
        } catch (NumberFormatException e) {
            ApiResponse<Object> apiResponse = new ApiResponse<>(false, "Invalid student ID format");
            sendJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, apiResponse);
        } catch (IllegalArgumentException e) {
            // Student not found from service layer
            ApiResponse<Object> apiResponse = new ApiResponse<>(false, e.getMessage());
            sendJsonResponse(response, HttpServletResponse.SC_NOT_FOUND, apiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse<Object> apiResponse = new ApiResponse<>(false, "Internal server error: " + e.getMessage());
            sendJsonResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, apiResponse);
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
     * Sends a JSON response with ApiResponse wrapper.
     */
    private void sendJsonResponse(HttpServletResponse response, int statusCode, ApiResponse<?> apiResponse) 
            throws IOException {
        response.setStatus(statusCode);
        
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(apiResponse));
        out.flush();
    }
}
