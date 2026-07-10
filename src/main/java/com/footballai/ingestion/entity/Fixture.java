package com.footballai.ingestion.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fixtures")
@Getter
@Setter
public class Fixture {

    @Id
    private Long id;

    private String status;
}