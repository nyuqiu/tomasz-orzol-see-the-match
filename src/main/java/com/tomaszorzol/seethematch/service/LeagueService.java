package com.tomaszorzol.seethematch.service;

import com.tomaszorzol.seethematch.domain.League;
import com.tomaszorzol.seethematch.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeagueService {

    @Autowired
    private LeagueRepository leagueRepository;

    public Optional<League> getLeague(final Long id) {
        return leagueRepository.findById(id);
    }

    public List<League> getAllLeagues() {
        return leagueRepository.findAll();
    }

    public League saveLeague(final League league) {
        return leagueRepository.save(league);
    }

    public void deleteLeague(final Long id) {
        leagueRepository.deleteById(id);
    }
}
