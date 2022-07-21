package com.tomaszorzol.seethematch.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.tomaszorzol.seethematch.domain.Dto.TeamStatisticsDto;
import com.tomaszorzol.seethematch.exception.TeamStatisticNotFoundException;
import com.tomaszorzol.seethematch.mapper.TeamStatisticMapper;
import com.tomaszorzol.seethematch.service.TeamStatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/teamstatistic/")
@CrossOrigin("*")
public class TeamStatisticController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FootballController.class);

    @Autowired
    private TeamStatisticService teamStatisticService;

    @Autowired
    private TeamStatisticMapper teamStatisticMapper;

    @Autowired
    private FootballController footballController;

    @GetMapping()
    public List<TeamStatisticsDto> getTeamStatistics() {
        return teamStatisticMapper.mapToTeamStatisticDtoList(teamStatisticService.getAllTeamStatistics());
    }

    @GetMapping(value = "{id}")
    public TeamStatisticsDto getTeamStatistic(@PathVariable("id") Long teamStatisticId) throws TeamStatisticNotFoundException {
        return teamStatisticMapper.mapToTeamStatisticDto(teamStatisticService.getTeamStatistic(teamStatisticId)
                .orElseThrow(TeamStatisticNotFoundException::new));
    }

    @PostMapping(value = "{id}")
    public void createTeamStatistic(@RequestBody TeamStatisticsDto teamStatisticsDto) {
        teamStatisticService.saveTeamStatistic(teamStatisticMapper.mapToTeamStatistic(teamStatisticsDto));
    }

    @PostMapping(value = "/{leagueId}/{season}/{teamStatisticId}")
    public void saveStatisticsFromApi(@PathVariable("leagueId") Long leagueId,@PathVariable("season") Long season,
                                      @PathVariable("teamStatisticId") Long teamStatisticId)
            throws UnirestException, IOException {
        TeamStatisticsDto teamStatistics = footballController.fetchTeamStatistics(leagueId , season,teamStatisticId);
        teamStatisticService.saveTeamStatistic(teamStatisticMapper.mapToTeamStatistic(teamStatistics));
    }

    @DeleteMapping(value = "{id}")
    public void deleteTeamStatistic(@PathVariable("id") Long teamStatisticId) {
        teamStatisticService.deleteTeamStatistic(teamStatisticId);
    }
}
