package com.example.hometest.Module;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceErrorException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    private String message;

    public ResourceErrorException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s is error with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.message = String.format("%s is error with %s : '%s'", resourceName, fieldName, fieldValue);
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResourceErrorException() {
        super();
    }

    public ResourceErrorException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public ResourceErrorException(String message) {
        super(message);
        this.message = message;
    }

    public ResourceErrorException(Throwable cause) {
        super(cause);
    }
}
