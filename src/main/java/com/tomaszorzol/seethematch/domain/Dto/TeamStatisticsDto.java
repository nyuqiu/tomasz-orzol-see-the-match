package com.tomaszorzol.seethematch.domain.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class TeamStatisticsDto {
    @Id
    @JsonProperty("id")
    private long team_id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("total_matches")
    private int totalMatchesPlayed;
    @JsonProperty("total_goals")
    private double goalsFor;
    @JsonProperty("goals_against")
    private double goalsAgainst;
    @JsonProperty("goal_average")
    private double goalsAvg;
}