package com.footballai.ingestion.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "football.ingestion")
public class IngestionProperties {

    private List<Integer> leagues;

    private List<Integer> seasons;

    private Cron cron;
    
    private Integer statisticsBatchSize;
    
    private List<Integer> liveSeasons;

    public List<Integer> getLiveSeasons() {
        return liveSeasons;
    }

    public void setLiveSeasons(List<Integer> liveSeasons) {
        this.liveSeasons = liveSeasons;
    }

    public boolean isLiveSeason(Integer season) {
        return liveSeasons != null
                && liveSeasons.contains(season);
    }

    public Integer getStatisticsBatchSize() {
        return statisticsBatchSize;
    }

    public void setStatisticsBatchSize(Integer statisticsBatchSize) {
        this.statisticsBatchSize = statisticsBatchSize;
    }

    public static class Cron {

        private String fixtures;

        private String odds;

        private String standings;
        
        private String fixtureStatistics;

        public String getFixtureStatistics() {
            return fixtureStatistics;
        }

        public void setFixtureStatistics(String fixtureStatistics) {
            this.fixtureStatistics = fixtureStatistics;
        }

        public String getFixtures() {
            return fixtures;
        }

        public void setFixtures(String fixtures) {
            this.fixtures = fixtures;
        }

        public String getOdds() {
            return odds;
        }

        public void setOdds(String odds) {
            this.odds = odds;
        }

        public String getStandings() {
            return standings;
        }

        public void setStandings(String standings) {
            this.standings = standings;
        }
    }

    public List<Integer> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<Integer> leagues) {
        this.leagues = leagues;
    }

    public List<Integer> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Integer> seasons) {
        this.seasons = seasons;
    }

    public Cron getCron() {
        return cron;
    }

    public void setCron(Cron cron) {
        this.cron = cron;
    }
}