package com.sooyeon.tradelogsentinel.dto;

public class RiskSummaryResponse {

    private String riskLevel;
    private int totalSeverityScore;

    public RiskSummaryResponse(String riskLevel, int totalSeverityScore) {
        this.riskLevel = riskLevel;
        this.totalSeverityScore = totalSeverityScore;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public int getTotalSeverityScore() {
        return totalSeverityScore;
    }
}