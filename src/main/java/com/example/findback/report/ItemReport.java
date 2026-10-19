package com.example.findback.report;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class ItemReport {
    private static final int MAX_DESCRIPTION_LENGTH = 1000;

    private final UUID id;
    private final ReportType type;
    private final ItemCategory category;
    private final String color;
    private final CampusLocation location;
    private final String description;
    private final LocalDate eventDate;
    private final Instant createdAt;
    private ReportStatus status = ReportStatus.OPEN;

    public ItemReport(UUID id, ReportType type, ItemCategory category, String color, CampusLocation location,
            String description, LocalDate eventDate, Instant createdAt) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.type = Objects.requireNonNull(type, "type must not be null");
        this.category = Objects.requireNonNull(category, "category must not be null");
        this.color = color;
        this.location = Objects.requireNonNull(location, "location must not be null");
        this.description = Objects.requireNonNull(description, "description must not be null");
        if (description.isBlank() || description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("description must not be null or greater than 1000");
        }

        this.eventDate = Objects.requireNonNull(eventDate, "eventDate must not be null");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
    }

    public UUID getId() {
        return id;
    }

    public ReportType getType() {
        return type;
    }

    public ItemCategory getCategory() {
        return category;
    }

    public String getColor() {
        return color;
    }

    public CampusLocation getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public boolean isOpen() {
        return status == ReportStatus.OPEN;

    }

    public void resolve() {
        if (!isOpen()) {
            throw new IllegalStateException("Only an open report can be resolved");
        }
        status = ReportStatus.RESOLVED;
    }

    public void close() {
        if (!isOpen()) {
            throw new IllegalStateException("Only an open report can be closed");
        }
        status = ReportStatus.CLOSED;

    }

}
