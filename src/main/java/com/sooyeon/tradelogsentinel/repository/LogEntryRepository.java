package com.sooyeon.tradelogsentinel.repository;

import com.sooyeon.tradelogsentinel.entity.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {

    long countByLevel(String level);

    long countByLevelIgnoreCase(String level);

    @Query("SELECT l FROM LogEntry l ORDER BY l.timestamp DESC NULLS LAST")
    List<LogEntry> findAllByOrderByTimestampDesc();
}