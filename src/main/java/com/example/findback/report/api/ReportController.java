package com.example.findback.report.api;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.findback.report.ItemReport;
import com.example.findback.report.ReportService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ItemReport createReport( @Valid @RequestBody CreateReportRequest request){
        return reportService.createReport(request.type(),request.category(),request.color(),request.location(),request.description(),request.eventDate());


    }
    @GetMapping("/{id}")
    public ItemReport getReport(@PathVariable("id") UUID id){
        return reportService.getReport(id);

        
    }

    
}
