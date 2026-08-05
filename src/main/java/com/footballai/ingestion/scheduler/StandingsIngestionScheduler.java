package com.footballai.ingestion.scheduler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.footballai.ingestion.config.IngestionProperties;
import com.footballai.ingestion.service.StandingsIngestionService;

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
public class StandingsIngestionScheduler {

    private final StandingsIngestionService standingsIngestionService;
    private final IngestionProperties ingestionProperties;

    @Scheduled(cron = "${football.ingestion.cron.standings}")
    public void ingestStandings() {

    	log.info("Running standings scheduler");
    	
    	for (Integer league : ingestionProperties.getLeagues()) {

    	    for (Integer season : ingestionProperties.getSeasons()) {

    	        standingsIngestionService.ingestStandings(
    	                league,
    	                season
    	        );
    	    }
    	}
    }
}