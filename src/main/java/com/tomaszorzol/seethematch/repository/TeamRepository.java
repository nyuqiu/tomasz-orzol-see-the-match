package com.tomaszorzol.seethematch.repository;

import com.tomaszorzol.seethematch.domain.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends CrudRepository<Team, Long> {

    List<Team> findAll();

    Team save(Team team);

    Optional<Team> findById(Long id);

}
