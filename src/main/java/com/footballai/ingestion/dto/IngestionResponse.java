package com.footballai.ingestion.dto;

import java.time.LocalDateTime;
import java.util.List;

public record IngestionResponse(

        String operation,
        String status,
        int requestedItems,
        int successfulItems,
        int failedItems,
        LocalDateTime startedAt,
        LocalDateTime completedAt,
        List<String> errors

) {
}