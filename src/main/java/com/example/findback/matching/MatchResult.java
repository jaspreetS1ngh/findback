package com.example.findback.matching;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class MatchResult {

    private final UUID candidateReportId;
    private final boolean eligible;
    private final double score;
    private final List<String> reasons;

    public MatchResult(UUID candidateReportId, boolean eligible, double score, List<String> reasons) {
        this.candidateReportId = Objects.requireNonNull(candidateReportId, "Can't be null");
        this.eligible = eligible;
      
        if (!eligible && score!=0.0) {
            throw new IllegalArgumentException();
        }
        this.score = score;
        if (!Double.isFinite(score) || score < 0 || score > 100) {
            throw new IllegalArgumentException();
        }
        this.reasons = List.copyOf(reasons);
    }

    public UUID getCandidateReportId() {
        return candidateReportId;
    }

    public boolean isEligible() {
        return eligible;
    }

    public double getScore() {
        return score;
    }

    public List<String> getReasons() {
        return reasons;
    }

}
