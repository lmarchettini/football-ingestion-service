package com.footballai.ingestion.repository;

import com.footballai.ingestion.entity.RawApiResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawApiResponseRepository extends JpaRepository<RawApiResponse, Long> {
	
	boolean existsByEndpointAndRequestParams(
	        String endpoint,
	        String requestParams
	);
}