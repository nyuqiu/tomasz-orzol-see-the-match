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
                teamDto.getTeam_id(),
                teamDto.getName(),
                teamDto.getCountry(),
                teamDto.getVenueCity());
    }

    public TeamDto mapToTeamDto(final Team team) {
        return new TeamDto(
                team.getTeam_id(),
                team.getName(),
                team.getCountry(),
                team.getVenueCity());
    }

    public List<TeamDto> mapToTeamDtoList(final List<Team> teamList){
        return teamList.stream()
                .map(t -> new TeamDto(t.getTeam_id(), t.getName(), t.getCountry(), t.getVenueCity()))
                .collect(Collectors.toList());
    }
}
