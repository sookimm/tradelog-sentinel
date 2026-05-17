package com.sooyeon.tradelogsentinel.repository;

import com.sooyeon.tradelogsentinel.entity.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {
}