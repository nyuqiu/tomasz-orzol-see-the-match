package com.tomaszorzol.seethematch.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.tomaszorzol.seethematch.domain.Dto.LeagueDto;
import com.tomaszorzol.seethematch.domain.Dto.TeamDto;
import com.tomaszorzol.seethematch.exception.LeagueNotFoundException;
import com.tomaszorzol.seethematch.mapper.LeagueMapper;
import com.tomaszorzol.seethematch.mapper.TeamMapper;
import com.tomaszorzol.seethematch.service.LeagueService;
import com.tomaszorzol.seethematch.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/league/")
@CrossOrigin("*")
public class LeagueController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FootballController.class);

    @Autowired
    private TeamService teamService;

    @Autowired
    private LeagueService leagueService;

    @Autowired
    private FootballController footballController;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private LeagueMapper leagueMapper;

    @GetMapping()
    public List<LeagueDto> getLeagues() {
        return leagueMapper.mapToLeagueDtoList(leagueService.getAllLeagues());
    }

    @GetMapping(value = "{id}")
    public LeagueDto getLeague(@PathVariable("id") Long leagueId) throws LeagueNotFoundException {
        return leagueMapper.mapToLeagueDto(leagueService.getLeague(leagueId).orElseThrow(LeagueNotFoundException::new));
    }

    @PostMapping(value = "teams/{id}")
    public void saveTeamsFromLeagues(@PathVariable("id") Long leagueId) throws UnirestException, IOException {
        List<TeamDto> teams = footballController.fetchTeamsFromLeagues(leagueId);
        teams.forEach(n-> teamService.saveTeam(teamMapper.mapToTeam(n)));
    }

    @PostMapping()
    public void saveLeagues() throws UnirestException, IOException {
        List<LeagueDto> leagues = footballController.fetchLeagues();
        leagues.forEach(n-> leagueService.saveLeague(leagueMapper.mapToLeague(n)));
    }

    @PostMapping(value = "{id}")
    public void createLeague(@RequestBody LeagueDto leagueDto) {
        leagueService.saveLeague(leagueMapper.mapToLeague(leagueDto));
    }

    @DeleteMapping(value = "{id}")
    public void deleteLeague(@PathVariable("id") Long leagueId) {
        leagueService.deleteLeague(leagueId);
    }
}
