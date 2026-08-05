package com.footballai.ingestion.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OddsIngestionRequest(

        @NotNull
        @Positive
        Integer season,

        @NotEmpty
        List<@NotNull @Positive Integer> leagueIds

) {
}