package com.sooyeon.tradelogsentinel.service;

import com.sooyeon.tradelogsentinel.dto.CreateLogRequest;
import com.sooyeon.tradelogsentinel.dto.RiskSummaryResponse;
import com.sooyeon.tradelogsentinel.entity.LogEntry;
import com.sooyeon.tradelogsentinel.repository.LogEntryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
        List<LogEntry> logs = logEntryRepository.findAll();

        int totalSeverityScore = 0;

        for (LogEntry log : logs) {
            String level = log.getLevel();

            if (level == null) {
                continue;
            }

            switch (level.toUpperCase()) {
                case "INFO":
                    totalSeverityScore += 1;
                    break;
                case "WARNING":
                    totalSeverityScore += 2;
                    break;
                case "ERROR":
                    totalSeverityScore += 3;
                    break;
                case "CRITICAL":
                    totalSeverityScore += 4;
                    break;
            }
        }

        String riskLevel;

        if (totalSeverityScore >= 15) {
            riskLevel = "CRITICAL";
        } else if (totalSeverityScore >= 10) {
            riskLevel = "HIGH";
        } else if (totalSeverityScore >= 5) {
            riskLevel = "MEDIUM";
        } else {
            riskLevel = "LOW";
        }

        return new RiskSummaryResponse(riskLevel, totalSeverityScore);
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

    public List<LogEntry> getRecentAlerts() {

        List<String> keywords = List.of(
                "unauthorized",
                "failed",
                "attack",
                "breach",
                "suspicious",
                "timeout"
        );

        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);

        return logEntryRepository.findAllByOrderByTimestampDesc()
                .stream()
                .filter(log ->
                        log.getTimestamp() != null &&
                                log.getTimestamp().isAfter(oneHourAgo)
                )
                .filter(log ->
                        log.getMessage() != null &&
                                keywords.stream().anyMatch(keyword ->
                                        log.getMessage().toLowerCase().contains(keyword)
                                )
                )
                .toList();
    }

    public List<LogEntry> uploadLogs(MultipartFile file) throws Exception {

        List<LogEntry> savedLogs = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream())
        )) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);

                if (parts.length == 2) {
                    String level = parts[0].trim();
                    String message = parts[1].trim();

                    LogEntry log = new LogEntry(level, message);
                    savedLogs.add(logEntryRepository.save(log));
                }
            }
        }

        return savedLogs;
    }
}