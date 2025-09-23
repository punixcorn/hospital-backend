package com.example.hospital_backend.Error;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(ERROR_PATH)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request) {
        Map<String, Object> errorResponse = new HashMap<>();
        
        // Get error attributes
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String errorMessage = (String) request.getAttribute("javax.servlet.error.message");
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        
        // Build response
        errorResponse.put("timestamp", System.currentTimeMillis());
        errorResponse.put("status", statusCode != null ? statusCode : 500);
        errorResponse.put("error", getErrorReason(statusCode));
        errorResponse.put("message", errorMessage != null ? errorMessage : "An error occurred");
        errorResponse.put("path", requestUri != null ? requestUri : "unknown");
        
        if (exception != null) {
            errorResponse.put("exception", exception.getClass().getSimpleName());
        }
        
        HttpStatus status = statusCode != null ? HttpStatus.valueOf(statusCode) : HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(errorResponse, status);
    }
    
    private String getErrorReason(Integer statusCode) {
        if (statusCode == null) return "Internal Server Error";
        
        switch (statusCode) {
            case 400: return "Bad Request";
            case 401: return "Unauthorized";
            case 403: return "Forbidden";
            case 404: return "Not Found";
            case 405: return "Method Not Allowed";
            case 500: return "Internal Server Error";
            case 502: return "Bad Gateway";
            case 503: return "Service Unavailable";
            default: return "Error";
        }
    }
}