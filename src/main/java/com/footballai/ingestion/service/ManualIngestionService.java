package com.footballai.ingestion.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.footballai.ingestion.dto.IngestionResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManualIngestionService {

	private final FixturesIngestionService fixturesIngestionService;

	private final StandingsIngestionService standingsIngestionService;

	private final OddsIngestionService oddsIngestionService;

	private final FixtureStatisticsIngestionService fixtureStatisticsIngestionService;

	public IngestionResponse ingestFixtures(Integer season, List<Integer> leagueIds) {

		LocalDateTime startedAt = LocalDateTime.now();

		List<String> errors = new ArrayList<>();

		int successful = 0;

		for (Integer leagueId : leagueIds) {

			try {
				fixturesIngestionService.ingestFixtures(leagueId, season);

				successful++;

			} catch (Exception exception) {

				String error = "league=" + leagueId + ": " + exception.getMessage();

				errors.add(error);

				log.error("Manual fixtures ingestion failed " + "league={} season={}", leagueId, season, exception);
			}
		}

		return response("FIXTURES", leagueIds.size(), successful, errors, startedAt);
	}

	public IngestionResponse ingestStandings(Integer season, List<Integer> leagueIds) {

		LocalDateTime startedAt = LocalDateTime.now();

		List<String> errors = new ArrayList<>();

		int successful = 0;

		for (Integer leagueId : leagueIds) {

			try {
				standingsIngestionService.ingestStandings(leagueId, season);

				successful++;

			} catch (Exception exception) {

				errors.add("league=" + leagueId + ": " + exception.getMessage());

				log.error("Manual standings ingestion failed " + "league={} season={}", leagueId, season, exception);
			}
		}

		return response("STANDINGS", leagueIds.size(), successful, errors, startedAt);
	}

	public IngestionResponse ingestOdds(Integer season, List<Integer> leagueIds) {

		LocalDateTime startedAt = LocalDateTime.now();

		List<String> errors = new ArrayList<>();

		int successful = 0;

		for (Integer leagueId : leagueIds) {

			try {
				oddsIngestionService.ingestOdds(leagueId, season);

				successful++;

			} catch (Exception exception) {

				errors.add("league=" + leagueId + ": " + exception.getMessage());

				log.error("Manual odds ingestion failed " + "league={} season={}", leagueId, season, exception);
			}
		}

		return response("ODDS", leagueIds.size(), successful, errors, startedAt);
	}

	public IngestionResponse ingestStatistics(Integer batchSize) {

		LocalDateTime startedAt = LocalDateTime.now();

		try {
			int saved = fixtureStatisticsIngestionService.ingestFixtureStatistics(batchSize);

			return new IngestionResponse("FIXTURE_STATISTICS", "COMPLETED", batchSize, saved, 0, startedAt,
					LocalDateTime.now(), List.of());

		} catch (Exception exception) {

			log.error("Manual statistics ingestion failed", exception);

			return new IngestionResponse("FIXTURE_STATISTICS", "FAILED", batchSize, 0, 1, startedAt,
					LocalDateTime.now(), List.of(exception.getMessage()));
		}
	}

	private IngestionResponse response(String operation, int requested, int successful, List<String> errors,
			LocalDateTime startedAt) {

		int failed = errors.size();

		String status = failed == 0 ? "COMPLETED" : successful > 0 ? "PARTIAL_SUCCESS" : "FAILED";

		return new IngestionResponse(operation, status, requested, successful, failed, startedAt, LocalDateTime.now(),
				List.copyOf(errors));
	}
}