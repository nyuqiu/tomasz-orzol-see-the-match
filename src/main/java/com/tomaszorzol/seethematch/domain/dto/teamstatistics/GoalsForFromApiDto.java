package com.tomaszorzol.seethematch.domain.dto.teamstatistics;

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
public class GoalsForFromApiDto {
    @JsonProperty("average")
    private AverageGoalsFromApiDto averageGoalsFromApiDto;
}
