package com.footballai.ingestion.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.footballai.ingestion.dto.FixtureStatisticsIngestionRequest;
import com.footballai.ingestion.dto.IngestionResponse;
import com.footballai.ingestion.dto.LeagueSeasonIngestionRequest;
import com.footballai.ingestion.dto.OddsIngestionRequest;
import com.footballai.ingestion.service.ManualIngestionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/ingestion")
@RequiredArgsConstructor
@Validated
public class IngestionController {

	private final ManualIngestionService manualIngestionService;

	@PostMapping("/fixtures")
	public ResponseEntity<IngestionResponse> ingestFixtures(@Valid @RequestBody LeagueSeasonIngestionRequest request) {

		return ResponseEntity.ok(manualIngestionService.ingestFixtures(request.season(), request.leagueIds()));
	}

	@PostMapping("/standings")
	public ResponseEntity<IngestionResponse> ingestStandings(@Valid @RequestBody LeagueSeasonIngestionRequest request) {

		return ResponseEntity.ok(manualIngestionService.ingestStandings(request.season(), request.leagueIds()));
	}

	@PostMapping("/odds")
	public ResponseEntity<IngestionResponse> ingestOdds(@Valid @RequestBody OddsIngestionRequest request) {

		return ResponseEntity.ok(manualIngestionService.ingestOdds(request.season(), request.leagueIds()));
	}

	@PostMapping("/statistics")
	public ResponseEntity<IngestionResponse> ingestStatistics(
			@Valid @RequestBody FixtureStatisticsIngestionRequest request) {

		return ResponseEntity.ok(manualIngestionService.ingestStatistics(request.batchSize()));
	}
}