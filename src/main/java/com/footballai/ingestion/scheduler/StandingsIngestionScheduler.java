package com.footballai.ingestion.scheduler;

import com.footballai.ingestion.config.IngestionProperties;
import com.footballai.ingestion.service.StandingsIngestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
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