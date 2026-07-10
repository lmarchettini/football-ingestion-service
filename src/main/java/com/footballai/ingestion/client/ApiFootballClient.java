package com.footballai.ingestion.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class ApiFootballClient {

    private final RestClient apiFootballRestClient;

    public String getFixtures(Integer league, Integer season) {

        return apiFootballRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/fixtures")
                        .queryParam("league", league)
                        .queryParam("season", season)
                        .build())
                .retrieve()
                .body(String.class);
    }

    public String getStandings(Integer league, Integer season) {

        return apiFootballRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/standings")
                        .queryParam("league", league)
                        .queryParam("season", season)
                        .build())
                .retrieve()
                .body(String.class);
    }

    public String getFixtureStatistics(Long fixtureId) {

        return apiFootballRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/fixtures/statistics")
                        .queryParam("fixture", fixtureId)
                        .build())
                .retrieve()
                .body(String.class);
    }

    public String getOdds(Integer league, Integer season) {

        return apiFootballRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/odds")
                        .queryParam("league", league)
                        .queryParam("season", season)
                        .build())
                .retrieve()
                .body(String.class);
    }
}