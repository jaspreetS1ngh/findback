package com.example.findback.report.api;

import java.time.LocalDate;

import com.example.findback.report.CampusLocation;
import com.example.findback.report.ItemCategory;
import com.example.findback.report.ReportType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateReportRequest(
    
    @NotNull(message = "type must not be null") ReportType type,
    @NotNull(message = "category must not be null") ItemCategory category,
   String color,
    @NotNull(message = "location must not be null") CampusLocation location,
    @NotBlank(message = "description must not be null") @Size(max = 1000) String description,
    @NotNull(message = "eventDate must not be null") LocalDate eventDate
) {}
