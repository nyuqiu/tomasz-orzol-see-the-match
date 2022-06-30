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
public class League {
    @Id
    @Column
    private long league_id;
    @Column
    private String name;
    @Column
    private String country;
    @Column
    private String season;
    @Column(name = "season_start")
    private String seasonStart;
    @Column(name = "season_end")
    private String seasonEnd;

}