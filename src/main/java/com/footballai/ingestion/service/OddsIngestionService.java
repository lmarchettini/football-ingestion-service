package com.footballai.ingestion.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.footballai.ingestion.client.ApiFootballClient;
import com.footballai.ingestion.entity.RawApiResponse;
import com.footballai.ingestion.repository.RawApiResponseRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OddsIngestionService {

    private static final String ODDS_ENDPOINT = "/odds";

    private final ApiFootballClient apiFootballClient;
    private final RawApiResponseRepository rawApiResponseRepository;
    private final ObjectMapper objectMapper;

    public void ingestOdds(
            Integer league,
            Integer season
    ) {

        int currentPage = 1;
        int totalPages;

        do {
            String responseJson =
                    apiFootballClient.getOdds(
                            league,
                            season,
                            currentPage
                    );

            totalPages = extractTotalPages(responseJson);

            saveRawResponse(
                    league,
                    season,
                    currentPage,
                    responseJson
            );

            log.info(
                    "Odds raw response saved league={} season={} page={}/{}",
                    league,
                    season,
                    currentPage,
                    totalPages
            );

            currentPage++;

        } while (currentPage <= totalPages);
    }

    private int extractTotalPages(
            String responseJson
    ) {

        try {
            JsonNode root =
                    objectMapper.readTree(responseJson);

            int totalPages =
                    root.path("paging")
                            .path("total")
                            .asInt(1);

            return Math.max(totalPages, 1);

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Unable to read odds pagination",
                    e
            );
        }
    }

    private void saveRawResponse(
            Integer league,
            Integer season,
            Integer page,
            String responseJson
    ) {

        String requestParams =
                "league=" + league
                + "&season=" + season
                + "&page=" + page;

        RawApiResponse raw =
                RawApiResponse.builder()
                        .endpoint(ODDS_ENDPOINT)
                        .requestParams(requestParams)
                        .responseJson(responseJson)
                        .processed(false)
                        .processingStatus("PENDING")
                        .fetchedAt(LocalDateTime.now())
                        .source("API_FOOTBALL")
                        .httpStatus(200)
                        .build();

        rawApiResponseRepository.save(raw);
    }
}