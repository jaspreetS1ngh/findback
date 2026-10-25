package com.example.findback.report;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class ReportService {
    

    private final ItemReportRepository repository;
    private final Clock clock;

    public ReportService(ItemReportRepository repository, Clock clock) {
        this.repository = repository;
        this.clock=clock;
    }

    public ItemReport getReport(UUID id){
        
        Objects.requireNonNull(id, "Id can't be null");
        return repository.findById(id).orElseThrow(()->new ReportNotFoundException(id));
    }

    public ItemReport createReport(ReportType type, ItemCategory category, String color, CampusLocation location, String description, LocalDate eventDate){

        UUID id= UUID.randomUUID();
        Instant createdAt= Instant.now(clock);
        ItemReport report= new ItemReport(id, type, category, color, location, description, eventDate, createdAt);
        repository.save(report);
        return report;
    }
}
