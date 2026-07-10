package com.footballai.ingestion.service;

import com.footballai.ingestion.client.ApiFootballClient;
import com.footballai.ingestion.entity.RawApiResponse;
import com.footballai.ingestion.repository.RawApiResponseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class StandingsIngestionService {

    private final ApiFootballClient apiFootballClient;
    private final RawApiResponseRepository rawApiResponseRepository;

    public void ingestStandings(Integer league, Integer season) {
    	
    	String requestParams =
    	        "league=" + league + "&season=" + season;

    	boolean exists =
    	        rawApiResponseRepository
    	                .existsByEndpointAndRequestParams(
    	                        "/standings",
    	                        requestParams
    	                );

    	if (exists) {

    	    log.info("Standings raw already exists {}",
    	            requestParams);

    	    return;
    	}

        String responseJson =
                apiFootballClient.getStandings(league, season);

        RawApiResponse raw = RawApiResponse.builder()
                .endpoint("/standings")
                .requestParams(
                        "league=" + league + "&season=" + season
                )
                .responseJson(responseJson)
                .processed(false)
                .processingStatus("PENDING")
                .fetchedAt(LocalDateTime.now())
                .build();

        rawApiResponseRepository.save(raw);

        log.info("Standings raw response saved");
    }
}