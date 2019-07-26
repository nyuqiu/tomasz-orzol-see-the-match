package com.tomaszorzol.seethematch.service;

import com.tomaszorzol.seethematch.domain.TeamStatistic;
import com.tomaszorzol.seethematch.repository.TeamStatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamStatisticService {

    @Autowired
    private TeamStatisticRepository teamStatisticRepository;

    public Optional<TeamStatistic> getTeamStatistic(final Long id) {
        return teamStatisticRepository.findById(id);
    }

    public List<TeamStatistic> getAllTeamStatistics() {
        return teamStatisticRepository.findAll();
    }

    public TeamStatistic saveTeamStatistic(final TeamStatistic teamStatistic) {
        return teamStatisticRepository.save(teamStatistic);
    }

    public void deleteTeamStatistic(final Long id) {
        teamStatisticRepository.deleteById(id);
    }
}
