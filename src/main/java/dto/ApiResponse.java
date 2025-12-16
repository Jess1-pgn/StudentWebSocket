package dto;

/**
 * Generic API response wrapper for consistent response format.
 * Used across all controller endpoints to standardize responses.
 * 
 * @param <T> the type of data being returned
 */
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    /**
     * Default constructor
     */
    public ApiResponse() {
    }

    /**
     * Constructor with all fields
     */
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    /**
     * Constructor without data (for error responses)
     */
    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.data = null;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
