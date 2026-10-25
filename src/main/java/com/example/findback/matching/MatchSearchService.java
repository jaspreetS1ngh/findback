package com.example.findback.matching;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.findback.report.ItemReport;
import com.example.findback.report.ItemReportRepository;
import com.example.findback.report.ReportService;
import com.example.findback.report.ReportStatus;
import com.example.findback.report.ReportType;

@Service
public class MatchSearchService {
    private final ReportService reportService;
    private final ItemReportRepository repository;
    private final MatchingService matchingService;

    public MatchSearchService(ReportService reportService, ItemReportRepository repository,
            MatchingService matchingService) {
        this.reportService = reportService;
        this.repository = repository;
        this.matchingService = matchingService;
    }

    public List<MatchResult> findMatches(UUID lostReportId, int limit) {

        if (limit <= 0 || limit > 50) {
            throw new IllegalArgumentException("limit must be between 1 and 50 ");

        }
        ItemReport lost = reportService.getReport(lostReportId);
        if (lost.getStatus() != ReportStatus.OPEN || lost.getType() != ReportType.LOST) {
            throw new IllegalArgumentException("Matching requires an open lost report");

        }
        List<ItemReport> candidates = repository.findOpenFoundByCategory(lost.getCategory());
        List<MatchResult> results = new ArrayList<>();
        for (ItemReport candidate : candidates) {
            MatchResult result = matchingService.compare(lost, candidate);
            if (result.isEligible() && result.getScore() > 0) {
                results.add(result);

            }

        }
        Collections.sort(results, (a, b) -> {
            int scoreComparison = Double.compare(a.getScore(), b.getScore());
            if (scoreComparison != 0) {
                return scoreComparison;
            } else {
                return a.getCandidateReportId().compareTo(b.getCandidateReportId());
            }

        });
        int endIndex = Math.min(limit, results.size());
        return new ArrayList<>(results.subList(0, endIndex));

    }

}
