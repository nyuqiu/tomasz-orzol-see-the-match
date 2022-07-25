package com.tomaszorzol.seethematch.mapper;

import com.tomaszorzol.seethematch.domain.dto.TeamStatisticsDto;
import com.tomaszorzol.seethematch.domain.TeamStatistic;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeamStatisticMapper {
    public TeamStatistic mapToTeamStatistic(final TeamStatisticsDto teamStatisticsDto) {
        return new TeamStatistic(
                teamStatisticsDto.getTeam_id(),
                teamStatisticsDto.getName(),
                teamStatisticsDto.getTotalMatchesPlayed(),
                teamStatisticsDto.getGoalsFor(),
                teamStatisticsDto.getGoalsAgainst(),
                teamStatisticsDto.getGoalsAvg());
    }

    public TeamStatisticsDto mapToTeamStatisticDto(final TeamStatistic teamStatistic) {
        return new TeamStatisticsDto(
                teamStatistic.getTeam_id(),
                teamStatistic.getName(),
                teamStatistic.getTotalMatchesPlayed(),
                teamStatistic.getGoalsFor(),
                teamStatistic.getGoalsAgainst(),
                teamStatistic.getGoalsAvg());
    }

    public List<TeamStatisticsDto> mapToTeamStatisticDtoList(final List<TeamStatistic> teamStatistics){
        return teamStatistics.stream()
                .map(t -> new TeamStatisticsDto(t.getTeam_id(), t.getName(), t.getTotalMatchesPlayed(), t.getGoalsFor(), t.getGoalsAgainst(), t.getGoalsAvg()))
                .collect(Collectors.toList());
    }
}
