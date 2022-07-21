package com.tomaszorzol.seethematch.football.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.tomaszorzol.seethematch.domain.Dto.LeagueDto;
import com.tomaszorzol.seethematch.domain.Dto.league.LeagueArrayFromApiDto;
import com.tomaszorzol.seethematch.domain.Dto.league.LeagueFromApiDto;
import com.tomaszorzol.seethematch.domain.Dto.TeamStatisticsDto;
import com.tomaszorzol.seethematch.domain.Dto.TeamDto;
import com.tomaszorzol.seethematch.football.config.FootballConfig;
import com.tomaszorzol.seethematch.mapper.LeagueFromApiMapper;
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

    public List<TeamDto> getTeamsFromApi(final Long leagueId, final Long season) throws UnirestException, IOException {
        List<TeamDto> result = new ArrayList<>();

        HttpResponse<JsonNode> response = Unirest.get(footballConfig.getApiFootballEndpoint() +
                        "/v3/teams?league=" + leagueId + "&season=" + season)
                .header("X-RapidAPI-Host", footballConfig.getHost())
                .header("X-RapidAPI-Key", footballConfig.getApiKey())
                .asJson();
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

        HttpResponse<JsonNode> response = Unirest.get(footballConfig.getApiFootballEndpoint() +
                        "/v3/teams?id=" + teamId + "&league=" + leagueId + "&season=" + season)
                .header("X-RapidAPI-Host", footballConfig.getHost())
                .header("X-RapidAPI-Key", footballConfig.getApiKey())
                .asJson();

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

        HttpResponse<JsonNode> response = Unirest.get(footballConfig.getApiFootballEndpoint() + "/v3/leagues?country="
                        + country + "&season=" + season)
                .header("X-RapidAPI-Host", footballConfig.getHost())
                .header("X-RapidAPI-Key", footballConfig.getApiKey())
                .asJson();
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

        HttpResponse<JsonNode> response = Unirest.get(footballConfig.getApiFootballEndpoint() + "/v3/leagues?id=" + leagueId)
                .header("X-RapidAPI-Host", footballConfig.getHost())
                .header("X-RapidAPI-Key", footballConfig.getApiKey())
                .asJson();

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

        HttpResponse<JsonNode> response = Unirest.get(footballConfig.getApiFootballEndpoint() +
                        "/v3/teams/statistics?league=" + leagueId + "&season=" + season + "&team=" + teamId)
                .header("X-RapidAPI-Host", footballConfig.getHost())
                .header("X-RapidAPI-Key", footballConfig.getApiKey())
                .asJson();
        try {
            JSONObject api = response.getBody().getObject().getJSONObject("response");
            String name = api.getJSONObject("team").getJSONObject("name").getString("full");
            int totalMatchs = Integer.parseInt(mapper.readValue(api.getJSONObject("fixtures")
                    .getJSONObject("played").getJSONObject("total").toString(), String.class));
            double goalsForAvg = Double.parseDouble(mapper.readValue(api.getJSONObject("for")
                    .getJSONObject("average").getJSONObject("total").toString(), String.class));
            double goalsAgainstAvg = Double.parseDouble(mapper.readValue(api.getJSONObject("against")
                    .getJSONObject("average").getJSONObject("total").toString(), String.class));
            double totalGoalsAvg = goalsForAvg + goalsAgainstAvg;

            TeamStatisticsDto result = new TeamStatisticsDto(teamId, name, totalMatchs,
                    goalsForAvg, goalsAgainstAvg, totalGoalsAvg);
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

    private TeamDto mapperForTeam(JSONObject jsonTeam, String objectsInJson) throws IOException {
        return mapper.readValue(jsonTeam.get(objectsInJson).toString(), TeamDto.class);
    }

    private TeamDto readAndMapJsonTeam(JSONObject jsonTeam) throws IOException {
        TeamDto result = mapperForTeam(jsonTeam, "team");
        result.setCity(mapperForTeam(jsonTeam, "venue").getCity());
        return result;
    }

    private LeagueDto readAndMapJsonLeagueArray(JSONObject jsonLeague) throws IOException {
        LeagueArrayFromApiDto leagueArrayFromApiDto = mapper.readValue(jsonLeague.toString(), LeagueArrayFromApiDto.class);
        return leagueFromApiMapper.mapToLeagueDto(leagueArrayFromApiDto);
    }
}
