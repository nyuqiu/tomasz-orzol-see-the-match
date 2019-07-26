package com.tomaszorzol.seethematch.repository;

import com.tomaszorzol.seethematch.domain.League;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LeagueRepository extends CrudRepository<League, Long> {

    List<League> findAll();

    League save(League league);

    Optional<League> findById(Long id);

}
