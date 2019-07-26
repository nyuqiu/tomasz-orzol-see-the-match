package com.tomaszorzol.seethematch.domain.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TeamStatisticsDto {
    @Id
    private long team_id;
    private int totalMatchesPlayed;
    private double goalsFor;
    private double goalsAgainst;
    private double goalsAvg;
}