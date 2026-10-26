package com.example.findback.web;

import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.findback.matching.MatchSearchService;
import com.example.findback.report.ItemReport;
import com.example.findback.report.ReportNotFoundException;
import com.example.findback.report.ReportService;
import com.example.findback.report.api.CreateReportRequest;

@Controller
public class PageController {

    private final ReportService reportService;
    private final MatchSearchService matchSearchService;

    public PageController(ReportService reportService, MatchSearchService matchSearchService) {
        this.reportService = reportService;
        this.matchSearchService = matchSearchService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute(
                "reportForm",
                new CreateReportRequest(null, null, null, null, null, null));
        return "index";
    }

    @PostMapping("/reports")
    public String createReport(
            @Valid @ModelAttribute("reportForm") CreateReportRequest request,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "index";
        }

        ItemReport report = reportService.createReport(
                request.type(),
                request.category(),
                request.color(),
                request.location(),
                request.description(),
                request.eventDate());

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Report saved! ID: " + report.getId());

        return "redirect:/";
    }

    @GetMapping("/matches")
    public String findMatches(@RequestParam("id") UUID id, @RequestParam(name = "limit", defaultValue = "5") int limit,
            Model model) {

        model.addAttribute(
                "reportForm",
                new CreateReportRequest(null, null, null, null, null, null));
        model.addAttribute("lostReportId", id);
        model.addAttribute("limit", limit);

        try {
            model.addAttribute(
                    "matches",
                    matchSearchService.findMatches(id, limit));
        } catch (ReportNotFoundException | IllegalArgumentException exception) {
            model.addAttribute("matchError", exception.getMessage());
        }

        return "index";

    }
}