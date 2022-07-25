package com.tomaszorzol.seethematch.mapper;

import com.tomaszorzol.seethematch.domain.TeamStatistic;
import com.tomaszorzol.seethematch.domain.dto.TeamStatisticsDto;
import com.tomaszorzol.seethematch.domain.dto.teamstatistics.TeamStatisticsFromApiDto;
import org.springframework.stereotype.Component;

@Component
public class TeamStatisticsFromApiMapper {
    public TeamStatistic mapToTeamStatistics(final TeamStatisticsFromApiDto teamStatisticsFromApiDto) {
        return new TeamStatistic(
                teamStatisticsFromApiDto.getTeam().getId(),
                teamStatisticsFromApiDto.getTeam().getName(),
                teamStatisticsFromApiDto.getFixtures().getTotalMatchesPlayed().getTotal(),
                teamStatisticsFromApiDto.getGoals().getGoalsFor().getAverageGoalsFromApiDto().getTotal(),
                teamStatisticsFromApiDto.getGoals().getGoalsAgainst().getAverageGoalsFromApiDto().getTotal(),
                teamStatisticsFromApiDto.getGoals().getGoalsFor().getAverageGoalsFromApiDto().getTotal()
                        + teamStatisticsFromApiDto.getGoals().getGoalsAgainst().getAverageGoalsFromApiDto().getTotal());
    }

    public TeamStatisticsDto mapToTeamStatisticsDto(final TeamStatisticsFromApiDto teamStatisticsFromApiDto) {
        return new TeamStatisticsDto(
                teamStatisticsFromApiDto.getTeam().getId(),
                teamStatisticsFromApiDto.getTeam().getName(),
                teamStatisticsFromApiDto.getFixtures().getTotalMatchesPlayed().getTotal(),
                teamStatisticsFromApiDto.getGoals().getGoalsFor().getAverageGoalsFromApiDto().getTotal(),
                teamStatisticsFromApiDto.getGoals().getGoalsAgainst().getAverageGoalsFromApiDto().getTotal(),
                teamStatisticsFromApiDto.getGoals().getGoalsFor().getAverageGoalsFromApiDto().getTotal()
                        + teamStatisticsFromApiDto.getGoals().getGoalsAgainst().getAverageGoalsFromApiDto().getTotal());
    }
}
