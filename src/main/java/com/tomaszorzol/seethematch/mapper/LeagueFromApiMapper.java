package com.tomaszorzol.seethematch.mapper;

import com.tomaszorzol.seethematch.domain.Dto.LeagueDto;
import com.tomaszorzol.seethematch.domain.Dto.league.LeagueArrayFromApiDto;
import com.tomaszorzol.seethematch.domain.League;
import org.springframework.stereotype.Component;

@Component
public class LeagueFromApiMapper {
    public League mapToLeague(final LeagueArrayFromApiDto leagueArrayFromApi) {
        return new League(
                leagueArrayFromApi.getLeague().getId(),
                leagueArrayFromApi.getLeague().getName(),
                leagueArrayFromApi.getCountry().getCountry(),
                leagueArrayFromApi.getSeasons().getSeasons().get(0).getYear(),
                leagueArrayFromApi.getSeasons().getSeasons().get(0).getStart(),
                leagueArrayFromApi.getSeasons().getSeasons().get(0).getEnd());
    }
    public LeagueDto mapToLeagueDto(final LeagueArrayFromApiDto leagueArrayFromApi) {
        return new LeagueDto(
                leagueArrayFromApi.getLeague().getId(),
                leagueArrayFromApi.getLeague().getName(),
                leagueArrayFromApi.getCountry().getCountry(),
                leagueArrayFromApi.getSeasons().getSeasons().get(0).getYear(),
                leagueArrayFromApi.getSeasons().getSeasons().get(0).getStart(),
                leagueArrayFromApi.getSeasons().getSeasons().get(0).getEnd());
    }
}
