package com.tomaszorzol.seethematch.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class TeamStatistic {
    @Id
    @Column
    private long team_id;
    @Column
    private int totalMatchesPlayed;
    @Column
    private double goalsFor;
    @Column
    private double goalsAgainst;
    @Column
    private double goalsAvg;
}
