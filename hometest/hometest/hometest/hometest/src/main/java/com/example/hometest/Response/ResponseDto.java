package com.example.hometest.Response;

import java.util.List;
import lombok.Data;

@Data
public class ResponseDto {
    private int status;
    private String description;
    private String message;
    private List<Object> response;
}
