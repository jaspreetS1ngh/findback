package com.example.findback.report.persistence;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.example.findback.report.CampusLocation;
import com.example.findback.report.ItemCategory;
import com.example.findback.report.ReportStatus;
import com.example.findback.report.ReportType;

@Entity
@Table(name = "item_reports")
public class ItemReportEntity {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemCategory category;

    @Column(nullable = true)
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CampusLocation location;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status;

    protected ItemReportEntity() {
    }
}