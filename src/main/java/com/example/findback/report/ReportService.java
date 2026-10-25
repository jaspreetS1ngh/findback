package com.example.findback.report;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class ReportService {
    

    private final ItemReportRepository repository;

    public ReportService(ItemReportRepository repository) {
        this.repository = repository;
    }

    public ItemReport getReport(UUID id){
        
        Objects.requireNonNull(id, "Id can't be null");
        return repository.findById(id).orElseThrow(()->new ReportNotFoundException(id));
    }
}
