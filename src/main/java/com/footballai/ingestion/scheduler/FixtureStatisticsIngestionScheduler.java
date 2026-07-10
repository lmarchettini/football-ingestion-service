package com.footballai.ingestion.scheduler;

import com.footballai.ingestion.service.FixtureStatisticsIngestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FixtureStatisticsIngestionScheduler {

    private final FixtureStatisticsIngestionService service;

    @Scheduled(cron = "${football.ingestion.cron.fixture-statistics}")
    public void ingestFixtureStatistics() {

        log.info("Running fixture statistics scheduler");

        service.ingestFixtureStatistics();
    }
}