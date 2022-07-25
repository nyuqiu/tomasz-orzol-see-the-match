
package com.tomaszorzol.seethematch.controller;

import com.tomaszorzol.seethematch.domain.dto.TeamDto;
import com.tomaszorzol.seethematch.exception.TeamNotFoundException;
import com.tomaszorzol.seethematch.mapper.TeamMapper;
import com.tomaszorzol.seethematch.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/team/")
@CrossOrigin("*")
public class TeamController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FootballController.class);

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamMapper teamMapper;

    @GetMapping()
    public List<TeamDto> getTeams() {
        return teamMapper.mapToTeamDtoList(teamService.getAllTeams());
    }

    @GetMapping(value = "{id}")
    public TeamDto getTeam(@PathVariable("id") Long teamId) throws TeamNotFoundException {
       return teamMapper.mapToTeamDto(teamService.getTeam(teamId).orElseThrow(TeamNotFoundException::new));
    }

    @PostMapping(value = "{id}")
    public void createTeam(@RequestBody TeamDto teamDto) {
        teamService.saveTeam(teamMapper.mapToTeam(teamDto));
    }

    @DeleteMapping(value = "{id}")
    public void deleteTeam(@PathVariable("id") Long teamId) {
        teamService.deleteTeam(teamId);
    }


}
