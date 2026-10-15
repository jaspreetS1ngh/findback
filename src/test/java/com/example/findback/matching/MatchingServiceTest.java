package com.example.findback.matching;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class MatchingServiceTest {
     MatchingService tempservice= new MatchingService();

    @Test 
    void tokenizeNormalizesAndDeduplicates(){
       
        Set<String> expected = Set.of("black", "sony", "headphones");
      Set<String> actual= tempservice.tokenize("Black, BLACK Sony headphones!");
      assertEquals(expected, actual);

    }

    @Test
    void identicalDescriptionsHaveFullSimilarity() {
        double actual = tempservice.descriptionSimilarity(
            "black sony headphones",
            "black sony headphones"
        );

        assertEquals(1.0, actual, 0.000001);
    }
    @Test
    void normalizesDescriptionsBeforeComparing() {
        double actual = tempservice.descriptionSimilarity(
            "!Black, BLACK Sony headphones!",
            "sony HEADPHONES black"
        );

        assertEquals(1.0, actual, 0.000001);
    }
    @Test
    void partiallyOverlappingDescriptionsHaveFractionalSimilarity() {
        double actual = tempservice.descriptionSimilarity(
            "sony wireless headphones",
            "black sony headphones"
        );

        assertEquals(0.5, actual, 0.000001);
    }

    @Test
    void descriptionsWithoutSharedWordsHaveZeroSimilarity() {
        double actual = tempservice.descriptionSimilarity(
            "blue backpack",
            "silver keys"
        );

        assertEquals(0.0, actual, 0.000001);
    }

    
        
}
