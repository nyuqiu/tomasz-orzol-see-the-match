package com.tomaszorzol.seethematch.domain.Dto;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.tomaszorzol.seethematch.domain.Dto.league.LeagueFromApiDto;
import com.tomaszorzol.seethematch.football.client.FootballClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamJsonTest {
    @Autowired
    private FootballClient footballClient;

    @Test
    public void test1() throws UnirestException, JsonMappingException, IOException {
        List<LeagueDto> leaguesDtos = footballClient.getLeaguesFromApi();
        System.out.println(leaguesDtos.toString());
        System.out.println(leaguesDtos.size());
        LeagueFromApiDto leagueDto = footballClient.getLeagueFromApi(2L);
        System.out.println(leagueDto.toString());
    }
}