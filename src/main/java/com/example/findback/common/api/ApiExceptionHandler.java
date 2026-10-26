package com.example.findback.common.api;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.findback.report.ReportNotFoundException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ReportNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleReportNotFound(ReportNotFoundException exception) {
        return Map.of(
                "code", "REPORT_NOT_FOUND",
                "message", exception.getMessage());

    }

}
