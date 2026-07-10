package com.footballai.ingestion.service;

import com.footballai.ingestion.client.ApiFootballClient;
import com.footballai.ingestion.entity.Fixture;
import com.footballai.ingestion.entity.RawApiResponse;
import com.footballai.ingestion.repository.FixtureRepository;
import com.footballai.ingestion.repository.RawApiResponseRepository;
import com.footballai.ingestion.config.IngestionProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FixtureStatisticsIngestionService {

    private final FixtureRepository fixtureRepository;
    private final ApiFootballClient apiFootballClient;
    private final RawApiResponseRepository rawApiResponseRepository;
    private final IngestionProperties properties;

    public void ingestFixtureStatistics() {

        List<Fixture> fixtures =
                fixtureRepository.findFixturesWithoutStatisticsRaw(
                        PageRequest.of(
                                0,
                                properties.getStatisticsBatchSize()
                        )
                );

        log.info(
                "Found {} fixtures without statistics raw",
                fixtures.size()
        );

        for (Fixture fixture : fixtures) {

            try {
                String requestParams = "fixture=" + fixture.getId();

                if (rawApiResponseRepository.existsByEndpointAndRequestParams(
                        "/fixtures/statistics",
                        requestParams
                )) {
                    continue;
                }

                String responseJson =
                        apiFootballClient.getFixtureStatistics(fixture.getId());

                RawApiResponse raw =
                        RawApiResponse.builder()
                                .endpoint("/fixtures/statistics")
                                .requestParams(requestParams)
                                .responseJson(responseJson)
                                .processed(false)
                                .processingStatus("PENDING")
                                .fetchedAt(LocalDateTime.now())
                                .build();

                rawApiResponseRepository.save(raw);

                log.info("Saved statistics raw fixtureId={}", fixture.getId());

                Thread.sleep(500); // circa 120 richieste/minuto

            } catch (Exception e) {
                log.error(
                        "Failed statistics raw fixtureId={}",
                        fixture.getId(),
                        e
                );
                break;
            }
        }
    }
}