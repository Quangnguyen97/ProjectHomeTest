package com.example.hometest.Module;

public class ResourceException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    private String message;

    public ResourceException() {
        super();
    }

    public ResourceException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public ResourceException(String message) {
        super(message);
        this.message = message;
    }

    public ResourceException(Throwable cause) {
        super(cause);
    }

    public ResourceException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s have exception with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.message = String.format("%s have exception with %s : '%s'", resourceName, fieldName, fieldValue);
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

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
