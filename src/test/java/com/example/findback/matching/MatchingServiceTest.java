package com.example.findback.matching;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class MatchingServiceTest {

    @Test 
    void tokenizeNormalizesAndDeduplicates(){
        MatchingService tempservice= new MatchingService();
        Set<String> expected = Set.of("black", "sony", "headphones");
      Set<String> actual= tempservice.tokenize("Black, BLACK Sony headphones!");
      assertEquals(expected, actual);

    }
    
}
