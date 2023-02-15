package com.example.hometest.Response;

import java.util.List;

public class Response {
    private int errorCode;
    private String errorDescription;
    private List<Object> response;

    public Response(int errorCode, String errorDescription, List<Object> response) {
        super();
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.response = response;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public List<Object> getResponse() {
        return response;
    }

    public void setResponse(List<Object> response) {
        this.response = response;
    }
}
