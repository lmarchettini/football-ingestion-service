package com.footballai.ingestion.scheduler;

import com.footballai.ingestion.config.IngestionProperties;
import com.footballai.ingestion.service.OddsIngestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OddsIngestionScheduler {

    private final OddsIngestionService oddsIngestionService;
    private final IngestionProperties ingestionProperties;

    @Scheduled(cron = "${football.ingestion.cron.odds}")
    public void ingestOdds() {
    	
    	log.info("Running odds scheduler");

    	for (Integer league : ingestionProperties.getLeagues()) {

    	    for (Integer season : ingestionProperties.getSeasons()) {

    	        oddsIngestionService.ingestOdds(
    	                league,
    	                season
    	        );
    	    }
    	}
    }
}