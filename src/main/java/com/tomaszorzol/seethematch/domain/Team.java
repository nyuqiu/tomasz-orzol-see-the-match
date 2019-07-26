package com.tomaszorzol.seethematch.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;

@AllArgsConstructor
@Getter
@Entity
public class Team {
    @Id
    @Column
    private long team_id;
    @Column
    private String name;
    @Column
    private String country;
    @Column
    private String venueCity;
}