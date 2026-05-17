package com.sooyeon.tradelogsentinel.controller;

import com.sooyeon.tradelogsentinel.dto.CreateLogRequest;
import com.sooyeon.tradelogsentinel.dto.RiskSummaryResponse;
import com.sooyeon.tradelogsentinel.entity.LogEntry;
import com.sooyeon.tradelogsentinel.repository.LogEntryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final LogEntryRepository logEntryRepository;

    public LogController(LogEntryRepository logEntryRepository) {
        this.logEntryRepository = logEntryRepository;
    }

    @PostMapping
    public LogEntry createLog(@RequestBody CreateLogRequest request) {
        LogEntry log = new LogEntry(
                request.getLevel(),
                request.getMessage()
        );

        return logEntryRepository.save(log);
    }

    @GetMapping
    public List<LogEntry> getAllLogs() {
        return logEntryRepository.findAllByOrderByTimestampDesc();
    }

    @GetMapping("/risk")
    public RiskSummaryResponse getRiskSummary() {
        long errorCount = logEntryRepository.countByLevel("ERROR");

        String riskLevel;

        if (errorCount >= 3) {
            riskLevel = "HIGH";
        } else if (errorCount >= 1) {
            riskLevel = "MEDIUM";
        } else {
            riskLevel = "LOW";
        }

        return new RiskSummaryResponse(riskLevel, errorCount);
    }

    @GetMapping("/summary")
    public Map<String, Long> getLogSummary() {

        Map<String, Long> summary = new HashMap<>();

        summary.put("INFO",
                logEntryRepository.countByLevelIgnoreCase("INFO"));

        summary.put("WARNING",
                logEntryRepository.countByLevelIgnoreCase("WARNING"));

        summary.put("ERROR",
                logEntryRepository.countByLevelIgnoreCase("ERROR"));

        summary.put("CRITICAL",
                logEntryRepository.countByLevelIgnoreCase("CRITICAL"));

        return summary;
    }

    @GetMapping("/alerts")
    public List<LogEntry> getSuspiciousLogs() {
        return logEntryRepository.findByMessageContainingIgnoreCaseOrderByTimestampDesc("unauthorized");
    }
}