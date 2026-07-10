package com.footballai.ingestion.scheduler;

import com.footballai.ingestion.config.IngestionProperties;
import com.footballai.ingestion.service.FixturesIngestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
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