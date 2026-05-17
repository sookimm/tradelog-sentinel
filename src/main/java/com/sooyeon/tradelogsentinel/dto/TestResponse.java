package com.sooyeon.tradelogsentinel.dto;

public class TestResponse {

    private String status;
    private String application;

    public TestResponse(String status, String application) {
        this.status = status;
        this.application = application;
    }

    public String getStatus() {
        return status;
    }

    public String getApplication() {
        return application;
    }
}