package com.tomaszorzol.seethematch.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.tomaszorzol.seethematch.domain.Dto.LeagueDto;
import com.tomaszorzol.seethematch.domain.Dto.TeamStatisticsDto;
import com.tomaszorzol.seethematch.domain.Dto.TeamDto;
import com.tomaszorzol.seethematch.football.client.FootballClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/api")
@CrossOrigin("*")
public class FootballController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FootballController.class);

    @Autowired
    private FootballClient footballClient;

    @GetMapping()
    public List<LeagueDto> fetchLeagues() throws UnirestException, IOException {
        return footballClient.getLeaguesFromApi();
    }

    @GetMapping(value = "/league/{id}")
    public LeagueDto fetchLeague(@PathVariable("id") Long leagueId) throws UnirestException, IOException {
        return footballClient.getLeagueFromApi(leagueId);
    }

    @GetMapping(value = "/fromleague/{id}/{season}")
    public List<TeamDto> fetchTeamsFromLeagues(@PathVariable("id") Long leagueId, @PathVariable("season") Long season)
            throws UnirestException, IOException {
        return footballClient.getTeamsFromApi(leagueId, season);
    }

    @GetMapping(value = "/team/{id}")
    public TeamDto fetchTeam(@PathVariable("id") Long leagueId) throws UnirestException, IOException {
        return footballClient.getTeamFromApi(leagueId);
    }

    @GetMapping(value = "/{leagueId}/{teamId}")
    public TeamStatisticsDto fetchTeamStatistics(@PathVariable("leagueId") Long leagueId, @PathVariable("teamId") Long teamId) throws UnirestException, IOException {
        return footballClient.getStatisticsFromApi(leagueId, teamId);
    }


}
