package com.example.findback.matching;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.example.findback.report.CampusLocation;
import com.example.findback.report.ItemCategory;
import com.example.findback.report.ItemReport;
import com.example.findback.report.ReportType;

public class MatchingServiceTest {
    private final MatchingService tempservice = new MatchingService();

    @Test
    void tokenizeNormalizesAndDeduplicates() {

        Set<String> expected = Set.of("black", "sony", "headphones");
        Set<String> actual = tempservice.tokenize("Black, BLACK Sony headphones!");
        assertEquals(expected, actual);

    }

    @Test
    void identicalDescriptionsHaveFullSimilarity() {
        double actual = tempservice.descriptionSimilarity(
                "black sony headphones",
                "black sony headphones");

        assertEquals(1.0, actual, 0.000001);
    }

    @Test
    void normalizesDescriptionsBeforeComparing() {
        double actual = tempservice.descriptionSimilarity(
                "!Black, BLACK Sony headphones!",
                "sony HEADPHONES black");

        assertEquals(1.0, actual, 0.000001);
    }

    @Test
    void partiallyOverlappingDescriptionsHaveFractionalSimilarity() {
        double actual = tempservice.descriptionSimilarity(
                "sony wireless headphones",
                "black sony headphones");

        assertEquals(0.5, actual, 0.000001);
    }

    @Test
    void descriptionsWithoutSharedWordsHaveZeroSimilarity() {
        double actual = tempservice.descriptionSimilarity(
                "blue backpack",
                "silver keys");

        assertEquals(0.0, actual, 0.000001);
    }

    @Test
    void compareReturns80WhenColorLocationAndHalfTheWordsMatch() {
        UUID foundId = UUID.randomUUID();
        LocalDate eventDate = LocalDate.of(2026, 10, 15);
        Instant createdAt = Instant.parse("2026-10-15T12:00:00Z");

        ItemReport lost = new ItemReport(
                UUID.randomUUID(),
                ReportType.LOST,
                ItemCategory.ELECTRONICS,
                " black ",
                CampusLocation.LIBRARY,
                "sony wireless headphones",
                eventDate,
                createdAt);

        ItemReport found = new ItemReport(
                foundId,
                ReportType.FOUND,
                ItemCategory.ELECTRONICS,
                "BLACK",
                CampusLocation.LIBRARY,
                "black sony headphones",
                eventDate,
                createdAt);

                MatchResult result = tempservice.compare(lost, found);

                assertTrue(result.isEligible());
                assertEquals(foundId, result.getCandidateReportId());
                assertEquals(80.0, result.getScore(), 0.000001);
                assertTrue(result.getReasons().contains("Colour matches"));
                assertTrue(result.getReasons().contains("Location matches"));
    }

}
