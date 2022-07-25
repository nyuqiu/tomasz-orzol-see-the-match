package com.tomaszorzol.seethematch.domain.dto.teamstatistics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamStatisticsFromApiDto {
    @JsonProperty("team")
    private TeamFromApiDto team;
    @JsonProperty("fixtures")
    private FixturesFromApiDto fixtures;
    @JsonProperty("goals")
    private GoalsFromApiDto goals;
}
