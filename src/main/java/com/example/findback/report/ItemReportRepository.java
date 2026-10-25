package com.example.findback.report;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemReportRepository  {

    void save(ItemReport report);
    Optional<ItemReport> findById(UUID id);
    List<ItemReport> findOpenFoundByCategory(ItemCategory category);
    
}
