package com.footballai.ingestion.repository;

import com.footballai.ingestion.entity.Fixture;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FixtureRepository extends JpaRepository<Fixture, Long> {

    @Query("""
        SELECT f
        FROM Fixture f
        WHERE f.status = 'FT'
          AND NOT EXISTS (
              SELECT 1
              FROM RawApiResponse r
              WHERE r.endpoint = '/fixtures/statistics'
                AND r.requestParams = CONCAT('fixture=', f.id)
          )
        ORDER BY f.id ASC
    """)
    List<Fixture> findFixturesWithoutStatisticsRaw(Pageable pageable);
}