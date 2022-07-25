package com.tomaszorzol.seethematch.football.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.tomaszorzol.seethematch.domain.dto.LeagueDto;
import com.tomaszorzol.seethematch.domain.dto.league.LeagueArrayFromApiDto;
import com.tomaszorzol.seethematch.domain.dto.TeamStatisticsDto;
import com.tomaszorzol.seethematch.domain.dto.TeamDto;
import com.tomaszorzol.seethematch.domain.dto.teamstatistics.TeamStatisticsFromApiDto;
import com.tomaszorzol.seethematch.football.config.FootballConfig;
import com.tomaszorzol.seethematch.mapper.LeagueFromApiMapper;
import com.tomaszorzol.seethematch.mapper.TeamStatisticsFromApiMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FootballClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(FootballClient.class);

    @Autowired
    private FootballConfig footballConfig;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private LeagueFromApiMapper leagueFromApiMapper;

    @Autowired
    private TeamStatisticsFromApiMapper teamStatisticsFromApiMapper;

    public List<TeamDto> getTeamsFromApi(final Long leagueId, final Long season) throws UnirestException, IOException {
        List<TeamDto> result = new ArrayList<>();

        HttpResponse<JsonNode> response = linkToApi(footballConfig.getApiFootballEndpoint() +
                "/v3/teams?league=" + leagueId + "&season=" + season);
        try {
            for (Object team : findObjectsInResponse(response)) {
                result.add(readAndMapJsonTeam((JSONObject) team));
            }
            LOGGER.info("Fetched teams from API: " + result);
            return result;
        } catch (JSONException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public TeamDto getTeamFromApi(final Long leagueId, final Long season, final Long teamId)
            throws UnirestException, IOException {

        HttpResponse<JsonNode> response = linkToApi(footballConfig.getApiFootballEndpoint() +
                "/v3/teams?id=" + teamId + "&league=" + leagueId + "&season=" + season);
        try {
            TeamDto result = readAndMapJsonTeam((JSONObject) findObjectsInResponse(response).get(0));
            LOGGER.info("Fetched team from API: " + result);
            return result;
        } catch (JSONException e) {
            LOGGER.error(e.getMessage(), e);
            return new TeamDto();
        }
    }

    public List<LeagueDto> getLeaguesFromApi(String country, Long season) throws UnirestException, IOException {
        List<LeagueDto> result = new ArrayList<>();

        HttpResponse<JsonNode> response = linkToApi(footballConfig.getApiFootballEndpoint() + "/v3/leagues?country="
                + country + "&season=" + season);
        try {
            for (Object league : findObjectsInResponse(response)) {
                result.add(readAndMapJsonLeagueArray((JSONObject) league));
            }
            LOGGER.info("Fetched leagues from API: " + result);
            return result;
        } catch (JSONException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public LeagueDto getLeagueFromApi(final Long leagueId) throws UnirestException, IOException {

        HttpResponse<JsonNode> response = linkToApi(footballConfig.getApiFootballEndpoint() + "/v3/leagues?id=" + leagueId);
        try {
            LeagueDto result = readAndMapJsonLeagueArray(findObjectsInResponse(response).getJSONObject(0));
            LOGGER.info("Fetched league from API: " + result);
            return result;
        } catch (JSONException e) {
            LOGGER.error(e.getMessage(), e);
            return new LeagueDto();
        }
    }

    public TeamStatisticsDto getStatisticsFromApi(final Long leagueId, final Long season, final Long teamId)
            throws UnirestException, IOException {

        HttpResponse<JsonNode> response = linkToApi(footballConfig.getApiFootballEndpoint() +
                "/v3/teams/statistics?league=" + leagueId + "&season=" + season + "&team=" + teamId);
        try {
            TeamStatisticsDto result = readAndMapJsonTeamStatistics(
                    response.getBody().getObject().getJSONObject("response"));
            LOGGER.info("Fetched statistics from API: " + result);
            return result;
        } catch (JSONException e) {
            LOGGER.error(e.getMessage(), e);
            return new TeamStatisticsDto();
        }
    }

    private JSONArray findObjectsInResponse(HttpResponse<JsonNode> response) {
        return response.getBody().getObject().getJSONArray("response");
    }

    private TeamDto readAndMapJsonTeam(JSONObject jsonTeam) throws IOException {
        TeamDto result = mapper.readValue(jsonTeam.get("team").toString(), TeamDto.class);
        result.setCity(mapper.readValue(jsonTeam.get("venue").toString(), TeamDto.class).getCity());
        return result;
    }

    private LeagueDto readAndMapJsonLeagueArray(JSONObject jsonLeague) throws IOException {
        return leagueFromApiMapper.mapToLeagueDto(
                mapper.readValue(jsonLeague.toString(), LeagueArrayFromApiDto.class));
    }

    private TeamStatisticsDto readAndMapJsonTeamStatistics(JSONObject jsonTeamStatistics) throws IOException {
        return teamStatisticsFromApiMapper.mapToTeamStatisticsDto(
                mapper.readValue(jsonTeamStatistics.toString(), TeamStatisticsFromApiDto.class));
    }

    private HttpResponse<JsonNode> linkToApi(String link) throws UnirestException {
        return Unirest.get(link)
                .header("X-RapidAPI-Host", footballConfig.getHost())
                .header("X-RapidAPI-Key", footballConfig.getApiKey())
                .asJson();
    }
}
