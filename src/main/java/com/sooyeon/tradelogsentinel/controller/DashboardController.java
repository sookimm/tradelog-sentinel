package com.sooyeon.tradelogsentinel.controller;

import com.sooyeon.tradelogsentinel.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final LogService logService;

    public DashboardController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("risk", logService.getRiskSummary());
        model.addAttribute("summary", logService.getLogSummary());
        model.addAttribute("alerts", logService.getSuspiciousLogs());
        model.addAttribute("logs", logService.getAllLogs());

        return "dashboard";
    }
}