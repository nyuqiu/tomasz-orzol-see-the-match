package com.tomaszorzol.seethematch.service;

import com.tomaszorzol.seethematch.domain.Team;
import com.tomaszorzol.seethematch.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public Optional<Team> getTeam(final Long id) {
        return teamRepository.findById(id);
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team saveTeam(final Team team) {
        return teamRepository.save(team);
    }

    public void deleteTeam(final Long id) {
        teamRepository.deleteById(id);
    }
}
