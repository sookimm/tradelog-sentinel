package com.sooyeon.tradelogsentinel.controller;

import com.sooyeon.tradelogsentinel.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/dashboard/upload")
    public String uploadLogsFromDashboard(@RequestParam("file") MultipartFile file) throws Exception {
        logService.uploadLogs(file);
        return "redirect:/dashboard";
    }
}