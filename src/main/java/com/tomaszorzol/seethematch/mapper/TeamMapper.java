package com.tomaszorzol.seethematch.mapper;

import com.tomaszorzol.seethematch.domain.Dto.TeamDto;
import com.tomaszorzol.seethematch.domain.Team;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeamMapper {
    public Team mapToTeam(final TeamDto teamDto) {
        return new Team(
                teamDto.getId(),
                teamDto.getName(),
                teamDto.getCountry(),
                teamDto.getCity());
    }

    public TeamDto mapToTeamDto(final Team team) {
        return new TeamDto(
                team.getId(),
                team.getName(),
                team.getCountry(),
                team.getCity());
    }

    public List<TeamDto> mapToTeamDtoList(final List<Team> teamList){
        return teamList.stream()
                .map(t -> new TeamDto(t.getId(), t.getName(), t.getCountry(), t.getCity()))
                .collect(Collectors.toList());
    }
}
