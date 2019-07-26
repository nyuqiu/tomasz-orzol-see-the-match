package com.tomaszorzol.seethematch.repository;


import com.tomaszorzol.seethematch.domain.TeamStatistic;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TeamStatisticRepository extends CrudRepository<TeamStatistic, Long> {

    List<TeamStatistic> findAll();

    TeamStatistic save(TeamStatistic teamStatistic);

    Optional<TeamStatistic> findById(Long id);
}
