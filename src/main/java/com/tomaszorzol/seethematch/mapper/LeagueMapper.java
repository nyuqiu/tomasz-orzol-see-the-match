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
                leagueDto.getLeague_id(),
                leagueDto.getName(),
                leagueDto.getCountry(),
                leagueDto.getSeason(),
                leagueDto.getSeasonStart(),
                leagueDto.getSeasonEnd());
    }

    public LeagueDto mapToLeagueDto(final League league) {
        return new LeagueDto(
                league.getId(),
                league.getName(),
                league.getCountry(),
                league.getSeason(),
                league.getSeasonStart(),
                league.getSeasonEnd());
    }

    public List<LeagueDto> mapToLeagueDtoList(final List<League> leagueList){
        return leagueList.stream()
                .map(t -> new LeagueDto(t.getId(), t.getName(), t.getCountry(), t.getSeason(), t.getSeasonStart(), t.getSeasonEnd()))
                .collect(Collectors.toList());
    }

}

