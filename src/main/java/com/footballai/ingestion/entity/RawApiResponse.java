package com.footballai.ingestion.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "raw_api_responses",
        indexes = {
                @Index(name = "idx_endpoint", columnList = "endpoint"),
                @Index(name = "idx_processed", columnList = "processed"),
                @Index(name = "idx_fetched_at", columnList = "fetched_at")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RawApiResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 255)
    private String endpoint;
    @Column(name = "request_url", columnDefinition = "TEXT")
    private String requestUrl;
    @Column(name = "request_params", columnDefinition = "TEXT")
    private String requestParams;
    @Column(name = "response_json", columnDefinition = "JSON")
    private String responseJson;
    @Column(name = "http_status")
    private Integer httpStatus;
    @Column(name = "response_time_ms")
    private Long responseTimeMs;
    @Column(length = 50)
    private String source;
    @Column(nullable = false)
    private Boolean processed = false;
    private LocalDateTime processedAt;
    @Column(name = "fetched_at")
    private LocalDateTime fetchedAt;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    private String processingStatus;

    @Column(columnDefinition = "TEXT")
    private String errorMessage;

    @PrePersist
    public void prePersist() {

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }

        if (fetchedAt == null) {
            fetchedAt = LocalDateTime.now();
        }

        if (processed == null) {
            processed = false;
        }
    }
}