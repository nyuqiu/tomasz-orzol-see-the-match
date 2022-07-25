package com.tomaszorzol.seethematch.domain.dto.league;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeasonFromApiDto {
    @JsonProperty("year")
    private String year;
    @JsonProperty("start")
    private String start;
    @JsonProperty("end")
    private String end;
}
