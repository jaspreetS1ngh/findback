package com.example.findback.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;
@Repository
public class InMemoryItemReportRepository implements ItemReportRepository {

    private final Map<UUID, ItemReport> reports = new ConcurrentHashMap<>();

    @Override
    public void save(ItemReport report) {
        Objects.requireNonNull(report, "report must not be null");
        reports.put(report.getId(), report);

    }

    @Override
    public Optional<ItemReport> findById(UUID id) {

        Objects.requireNonNull(id, "id must not be null");
        return Optional.ofNullable(reports.get(id));

    }

    @Override
    public List<ItemReport> findOpenFoundByCategory(ItemCategory category) {
        Objects.requireNonNull(category, "category must not be null");
        List<ItemReport> matches = new ArrayList<>();
        for (ItemReport report : reports.values()) {
            if (report.isOpen() && report.getType() == ReportType.FOUND && report.getCategory() == category) {
                matches.add(report);

            }
        }
        return matches;
    }

}
