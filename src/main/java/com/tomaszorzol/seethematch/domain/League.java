package com.tomaszorzol.seethematch.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class League {
    @Id
    @Column
    private long id;
    @Column
    private String name;
    @Column
    private String country;
    @Column
    private String year;
    @Column
    private String start;
    @Column
    private String end;

}