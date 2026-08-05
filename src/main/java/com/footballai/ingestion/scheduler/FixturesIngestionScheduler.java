package com.footballai.ingestion.scheduler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.footballai.ingestion.config.IngestionProperties;
import com.footballai.ingestion.service.FixturesIngestionService;

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
public class FixturesIngestionScheduler {

    private final FixturesIngestionService fixturesIngestionService;
    private final IngestionProperties ingestionProperties;

    @Scheduled(cron = "${football.ingestion.cron.fixtures}")
    public void ingestFixtures() {

        log.info("Running fixtures scheduler");

        for (Integer league : ingestionProperties.getLeagues()) {

            for (Integer season : ingestionProperties.getSeasons()) {

                fixturesIngestionService.ingestFixtures(
                        league,
                        season
                );
            }
        }
    }
}