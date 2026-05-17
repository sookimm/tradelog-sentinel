package com.sooyeon.tradelogsentinel.controller;

import com.sooyeon.tradelogsentinel.dto.CreateLogRequest;
import com.sooyeon.tradelogsentinel.dto.RiskSummaryResponse;
import com.sooyeon.tradelogsentinel.entity.LogEntry;
import com.sooyeon.tradelogsentinel.service.LogService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping
    public LogEntry createLog(@RequestBody CreateLogRequest request) {
        return logService.createLog(request);
    }

    @GetMapping
    public List<LogEntry> getAllLogs() {
        return logService.getAllLogs();
    }

    @GetMapping("/risk")
    public RiskSummaryResponse getRiskSummary() {
        return logService.getRiskSummary();
    }

    @GetMapping("/summary")
    public Map<String, Long> getLogSummary() {
        return logService.getLogSummary();
    }

    @GetMapping("/alerts")
    public List<LogEntry> getSuspiciousLogs() {
        return logService.getSuspiciousLogs();
    }

    @GetMapping("/severity-score")
    public Map<String, Integer> getSeverityScore() {
        return logService.getSeverityScore();
    }

    @GetMapping("/recent-alerts")
    public List<LogEntry> getRecentAlerts() {
        return logService.getRecentAlerts();
    }

    @PostMapping("/upload")
    public List<LogEntry> uploadLogs(@RequestParam("file") MultipartFile file) throws Exception {
        return logService.uploadLogs(file);
    }
}