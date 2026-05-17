package com.sooyeon.tradelogsentinel.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "logs")
public class LogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String level;

    private String message;

    public LogEntry() {
    }

    public LogEntry(String level, String message) {
        this.level = level;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}