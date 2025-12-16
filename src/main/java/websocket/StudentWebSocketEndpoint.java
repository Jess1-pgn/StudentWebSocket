package websocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.Student;
import dao.StudentDAO;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.Date;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ServerEndpoint("/student")
public class StudentWebSocketEndpoint {
    
    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
    private StudentDAO studentDAO = new StudentDAO();
    private Gson gson = new Gson();
    
    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("WebSocket opened: " + session.getId());
        
        // Send all students to the newly connected client
        List<Student> students = studentDAO.getAllStudents();
        sendMessage(session, createResponse("list", students, true, "Connected successfully"));
    }
    
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Message received: " + message);
        
        try {
            JsonObject json = gson.fromJson(message, JsonObject.class);
            String action = json.get("action").getAsString();
            
            switch (action) {
                case "list":
                    handleList(session);
                    break;
                case "add":
                    handleAdd(json, session);
                    break;
                case "update":
                    handleUpdate(json, session);
                    break;
                case "delete":
                    handleDelete(json, session);
                    break;
                case "get":
                    handleGet(json, session);
                    break;
                default:
                    sendMessage(session, createResponse("error", null, false, "Unknown action"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage(session, createResponse("error", null, false, "Error processing message: " + e.getMessage()));
        }
    }
    
    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        System.out.println("WebSocket closed: " + session.getId());
    }
    
    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("WebSocket error: " + throwable.getMessage());
        throwable.printStackTrace();
    }
    
    private void handleList(Session session) {
        List<Student> students = studentDAO.getAllStudents();
        sendMessage(session, createResponse("list", students, true, "Students retrieved"));
    }
    
    private void handleAdd(JsonObject json, Session session) {
        JsonObject data = json.getAsJsonObject("data");
        Student student = new Student(
            data.get("firstName").getAsString(),
            data.get("lastName").getAsString(),
            Date.valueOf(data.get("dateBirth").getAsString())
        );
        
        boolean success = studentDAO.addStudent(student);
        String message = success ? "Student added successfully" : "Failed to add student";
        
        if (success) {
            // Broadcast to all clients
            List<Student> students = studentDAO.getAllStudents();
            broadcastToAll(createResponse("list", students, true, message));
        } else {
            sendMessage(session, createResponse("add", null, false, message));
        }
    }
    
    private void handleUpdate(JsonObject json, Session session) {
        JsonObject data = json.getAsJsonObject("data");
        Student student = new Student();
        student.setIdStudent(data.get("id").getAsInt());
        student.setFirstNameStudent(data.get("firstName").getAsString());
        student.setLastNameStudent(data.get("lastName").getAsString());
        student.setDateBirthStudent(Date.valueOf(data.get("dateBirth").getAsString()));
        
        boolean success = studentDAO.updateStudent(student);
        String message = success ? "Student updated successfully" : "Failed to update student";
        
        if (success) {
            // Broadcast to all clients
            List<Student> students = studentDAO.getAllStudents();
            broadcastToAll(createResponse("list", students, true, message));
        } else {
            sendMessage(session, createResponse("update", null, false, message));
        }
    }
    
    private void handleDelete(JsonObject json, Session session) {
        int id = json.get("id").getAsInt();
        
        boolean success = studentDAO.deleteStudent(id);
        String message = success ? "Student deleted successfully" : "Failed to delete student";
        
        if (success) {
            // Broadcast to all clients
            List<Student> students = studentDAO.getAllStudents();
            broadcastToAll(createResponse("list", students, true, message));
        } else {
            sendMessage(session, createResponse("delete", null, false, message));
        }
    }
    
    private void handleGet(JsonObject json, Session session) {
        int id = json.get("id").getAsInt();
        Student student = studentDAO.getStudentById(id);
        
        if (student != null) {
            sendMessage(session, createResponse("get", student, true, "Student retrieved"));
        } else {
            sendMessage(session, createResponse("get", null, false, "Student not found"));
        }
    }
    
    private String createResponse(String action, Object data, boolean success, String message) {
        JsonObject response = new JsonObject();
        response.addProperty("action", action);
        response.addProperty("success", success);
        response.addProperty("message", message);
        response.add("data", gson.toJsonTree(data));
        return gson.toJson(response);
    }
    
    private void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void broadcastToAll(String message) {
        synchronized (sessions) {
            for (Session session : sessions) {
                sendMessage(session, message);
            }
        }
    }
}
