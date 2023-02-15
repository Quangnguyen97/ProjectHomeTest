package com.example.hometest.Response;

import java.util.List;
import lombok.Data;

@Data
public class ResponseDto {
    private int errorCode;
    private String errorDescription;
    private List<Object> response;
}
