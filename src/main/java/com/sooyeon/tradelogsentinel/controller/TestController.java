package com.sooyeon.tradelogsentinel.controller;

import com.sooyeon.tradelogsentinel.dto.TestResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public TestResponse testApi() {
        return new TestResponse(
                "running",
                "TradeLog Sentinel"
        );
    }
}