package com.tomaszorzol.seethematch.mapper;


import com.tomaszorzol.seethematch.domain.Dto.LeagueDto;
import com.tomaszorzol.seethematch.domain.League;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LeagueMapper {
    public League mapToLeague(final LeagueDto leagueDto) {
        return new League(
                leagueDto.getId(),
                leagueDto.getName(),
                leagueDto.getCountry(),
                leagueDto.getYear(),
                leagueDto.getStart(),
                leagueDto.getEnd());
    }

    public LeagueDto mapToLeagueDto(final League league) {
        return new LeagueDto(
                league.getId(),
                league.getName(),
                league.getCountry(),
                league.getYear(),
                league.getStart(),
                league.getEnd());
    }

    public List<LeagueDto> mapToLeagueDtoList(final List<League> leagueList){
        return leagueList.stream()
                .map(t -> new LeagueDto(t.getId(), t.getName(), t.getCountry(), t.getYear(), t.getStart(), t.getEnd()))
                .collect(Collectors.toList());
    }

}

