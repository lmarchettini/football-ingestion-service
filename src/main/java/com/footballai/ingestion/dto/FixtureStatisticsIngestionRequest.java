package com.footballai.ingestion.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record FixtureStatisticsIngestionRequest(

        @Min(1)
        @Max(500)
        Integer batchSize

) {
}