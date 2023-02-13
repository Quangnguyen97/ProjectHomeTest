package com.example.hometest.Module;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceErrorException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public ResourceErrorException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public ResourceErrorException() {
        super();
    }

    public ResourceErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceErrorException(String message) {
        super(message);
    }

    public ResourceErrorException(Throwable cause) {
        super(cause);
    }
}
