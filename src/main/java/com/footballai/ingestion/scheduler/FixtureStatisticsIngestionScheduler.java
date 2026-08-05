package com.footballai.ingestion.scheduler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.footballai.ingestion.service.FixtureStatisticsIngestionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(
        prefix = "football.ingestion.scheduling",
        name = "enabled",
        havingValue = "true"
)
public class FixtureStatisticsIngestionScheduler {

    private final FixtureStatisticsIngestionService service;

    @Scheduled(cron = "${football.ingestion.cron.fixture-statistics}")
    public void ingestFixtureStatistics() {

        log.info("Running fixture statistics scheduler");

        service.ingestFixtureStatistics();
    }
}