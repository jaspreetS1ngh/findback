package com.example.findback.matching.api;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.findback.matching.MatchResult;
import com.example.findback.matching.MatchSearchService;

@RestController
@RequestMapping("/api/reports")
public class MatchController {
    
    private final MatchSearchService matchSearchService;

    public MatchController(MatchSearchService matchSearchService) {
        this.matchSearchService = matchSearchService;
    }

    @GetMapping("/{id}/matches")
    public List<MatchResult> findMatches(@PathVariable("id") UUID id, @RequestParam(name = "limit", defaultValue = "5") int limit){

        return matchSearchService.findMatches(id, limit);

    }


}
