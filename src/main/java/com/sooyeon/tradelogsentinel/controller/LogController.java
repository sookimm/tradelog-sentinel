package com.sooyeon.tradelogsentinel.controller;

import com.sooyeon.tradelogsentinel.dto.CreateLogRequest;
import com.sooyeon.tradelogsentinel.entity.LogEntry;
import com.sooyeon.tradelogsentinel.repository.LogEntryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return logEntryRepository.findAll();
    }
}