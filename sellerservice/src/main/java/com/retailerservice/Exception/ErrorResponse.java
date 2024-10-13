package com.retailerservice.Exception;

public class ErrorResponse {

    private String message;

    // Constructor
    public ErrorResponse(String message) {
        this.message = message;
    }

    // Getter and Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Optional: Override toString() method for better debugging
    @Override
    public String toString() {
        return "ErrorResponse{message='" + message + "'}";
    }
}
