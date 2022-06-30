package com.tomaszorzol.seethematch.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Team {
    @Id
    @Column
    private long id;
    @Column
    private String name;
    @Column
    private String country;
    @Column
    private String city;
}