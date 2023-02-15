package com.example.hometest.Response;

import java.util.List;

public class Response {
    private int status;
    private String description;
    private String message;
    private List<Object> response;

    public Response(int status, String description, String message, List<Object> response) {
        super();
        this.status = status;
        this.description = description;
        this.message = message;
        this.response = response;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Object> getResponse() {
        return response;
    }

    public void setResponse(List<Object> response) {
        this.response = response;
    }
}
