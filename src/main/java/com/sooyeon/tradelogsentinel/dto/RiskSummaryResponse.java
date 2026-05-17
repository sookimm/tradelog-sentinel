package com.sooyeon.tradelogsentinel.dto;

public class RiskSummaryResponse {

    private String riskLevel;
    private long errorCount;

    public RiskSummaryResponse(String riskLevel, long errorCount) {
        this.riskLevel = riskLevel;
        this.errorCount = errorCount;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public long getErrorCount() {
        return errorCount;
    }
}