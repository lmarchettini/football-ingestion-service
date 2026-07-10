package com.footballai.ingestion.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.footballai.ingestion.client.ApiFootballClient;
import com.footballai.ingestion.entity.RawApiResponse;
import com.footballai.ingestion.repository.RawApiResponseRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FixturesIngestionService {

    private final ApiFootballClient apiFootballClient;
    private final RawApiResponseRepository rawApiResponseRepository;

    public void ingestFixtures(Integer league, Integer season) {
    	
    	String requestParams =
                "league=" + league + "&season=" + season;

        boolean exists =
                rawApiResponseRepository
                        .existsByEndpointAndRequestParams(
                                "/fixtures",
                                requestParams
                        );

        if (exists) {

            log.info("Fixtures raw already exists {}",
                    requestParams);

            return;
        }

        log.info("Starting fixtures ingestion league={} season={}",
                league,
                season);

        String responseJson =
                apiFootballClient.getFixtures(league, season);

        RawApiResponse raw = RawApiResponse.builder()
                .endpoint("/fixtures")
                .requestParams(
                        "league=" + league + "&season=" + season
                )
                .responseJson(responseJson)
                .processed(false)
                .processingStatus("PENDING")
                .fetchedAt(LocalDateTime.now())
                .build();

        rawApiResponseRepository.save(raw);

        log.info("Fixtures raw response saved");
    }
}