package com.footballai.ingestion.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "api-football")
public record ApiFootballProperties(
        String baseUrl,
        String apiKey
) {
}