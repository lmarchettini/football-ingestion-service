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
public class OddsIngestionService {

    private final ApiFootballClient apiFootballClient;
    private final RawApiResponseRepository rawApiResponseRepository;

    public void ingestOdds(Integer league, Integer season) {

        String requestParams =
                "league=" + league + "&season=" + season;

        boolean exists =
                rawApiResponseRepository.existsByEndpointAndRequestParams(
                        "/odds",
                        requestParams
                );

        if (exists) {
            log.info("Odds raw already exists {}", requestParams);
            return;
        }

        String responseJson =
                apiFootballClient.getOdds(league, season);

        RawApiResponse raw = RawApiResponse.builder()
                .endpoint("/odds")
                .requestParams(requestParams)
                .responseJson(responseJson)
                .processed(false)
                .processingStatus("PENDING")
                .fetchedAt(LocalDateTime.now())
                .build();

        rawApiResponseRepository.save(raw);

        log.info("Odds raw response saved {}", requestParams);
    }
}