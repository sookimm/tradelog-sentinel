package com.sooyeon.tradelogsentinel.service;

import com.sooyeon.tradelogsentinel.dto.CreateLogRequest;
import com.sooyeon.tradelogsentinel.dto.RiskSummaryResponse;
import com.sooyeon.tradelogsentinel.entity.LogEntry;
import com.sooyeon.tradelogsentinel.repository.LogEntryRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LogService {

    private final LogEntryRepository logEntryRepository;

    public LogService(LogEntryRepository logEntryRepository) {
        this.logEntryRepository = logEntryRepository;
    }

    public LogEntry createLog(CreateLogRequest request) {
        LogEntry log = new LogEntry(
                request.getLevel(),
                request.getMessage()
        );

        return logEntryRepository.save(log);
    }

    public List<LogEntry> getAllLogs() {
        return logEntryRepository.findAllByOrderByTimestampDesc();
    }

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

    public Map<String, Long> getLogSummary() {
        Map<String, Long> summary = new HashMap<>();

        summary.put("INFO", logEntryRepository.countByLevelIgnoreCase("INFO"));
        summary.put("WARNING", logEntryRepository.countByLevelIgnoreCase("WARNING"));
        summary.put("ERROR", logEntryRepository.countByLevelIgnoreCase("ERROR"));
        summary.put("CRITICAL", logEntryRepository.countByLevelIgnoreCase("CRITICAL"));

        return summary;
    }

    public List<LogEntry> getSuspiciousLogs() {
        List<String> keywords = List.of(
                "unauthorized",
                "failed",
                "attack",
                "breach",
                "suspicious",
                "timeout"
        );

        return logEntryRepository.findAllByOrderByTimestampDesc()
                .stream()
                .filter(log -> log.getMessage() != null &&
                        keywords.stream().anyMatch(keyword ->
                                log.getMessage().toLowerCase().contains(keyword)
                        ))
                .toList();
    }

    public Map<String, Integer> getSeverityScore() {
        List<LogEntry> logs = logEntryRepository.findAll();

        int totalScore = 0;

        for (LogEntry log : logs) {
            switch (log.getLevel().toUpperCase()) {
                case "INFO":
                    totalScore += 1;
                    break;
                case "WARNING":
                    totalScore += 2;
                    break;
                case "ERROR":
                    totalScore += 3;
                    break;
                case "CRITICAL":
                    totalScore += 4;
                    break;
            }
        }

        Map<String, Integer> response = new HashMap<>();
        response.put("totalSeverityScore", totalScore);

        return response;
    }
}