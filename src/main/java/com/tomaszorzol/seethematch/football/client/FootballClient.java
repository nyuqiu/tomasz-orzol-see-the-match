package com.tomaszorzol.seethematch.football.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.tomaszorzol.seethematch.domain.Dto.LeagueDto;
import com.tomaszorzol.seethematch.domain.Dto.TeamStatisticsDto;
import com.tomaszorzol.seethematch.domain.Dto.TeamDto;
import com.tomaszorzol.seethematch.football.config.FootballConfig;
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

    public List<TeamDto> getTeamsFromApi(final Long leagueId, final Long season) throws UnirestException, IOException {
        List<TeamDto> result = new ArrayList<>();

        HttpResponse<JsonNode> response = Unirest.get(footballConfig.getApiFootballEndpoint() +
                        "/v3/teams?league=" + leagueId + "&season=" + season)
                .header("X-RapidAPI-Host", footballConfig.getHost())
                .header("X-RapidAPI-Key", footballConfig.getApiKey())
                .asJson();
        try {
            JSONArray jsonTeams = findObjectsInTeamResponse(response);
            for (int i = 0; i < jsonTeams.length(); i++) {
                result.add(createTeamFromJsonResponse(jsonTeams.getJSONObject(i)));
            }
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
            return createTeamFromJsonResponse(findObjectsInTeamResponse(response).getJSONObject(0));
        } catch (JSONException e) {
            LOGGER.error(e.getMessage(), e);
            return new TeamDto();
        }
    }

    public List<LeagueDto> getLeaguesFromApi() throws UnirestException, IOException {
        List<LeagueDto> result = new ArrayList<>();

        HttpResponse<JsonNode> response = Unirest.get(footballConfig.getApiFootballEndpoint() + "/v2/leagues")
                .header("X-RapidAPI-Host", footballConfig.getHost())
                .header("X-RapidAPI-Key", footballConfig.getApiKey())
                .asJson();
        try {
            for (Object league : findObjectsInLeagueResponse(response)) {
                LeagueDto leagueDto = mapper.readValue(league.toString(), LeagueDto.class);
                result.add(leagueDto);
            }
            return result;
        } catch (JSONException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public LeagueDto getLeagueFromApi(final Long leagueId) throws UnirestException, IOException {

        HttpResponse<JsonNode> response = Unirest.get(footballConfig.getApiFootballEndpoint() + "/v2/leagues/league/" + leagueId)
                .header("Content-Type", footballConfig.getContentType())
                .header("X-RapidAPI-Host", footballConfig.getHost())
                .header("X-RapidAPI-Key", footballConfig.getApiKey())
                .asJson();

        try {
            JSONObject league = findObjectsInLeagueResponse(response).getJSONObject(0);
            return mapper.readValue(league.toString(), LeagueDto.class);
        } catch (JSONException e) {
            LOGGER.error(e.getMessage(), e);
            return new LeagueDto();
        }
    }

    public TeamStatisticsDto getStatisticsFromApi(final Long leagueId, final Long teamId) throws UnirestException, IOException {

        HttpResponse<JsonNode> response = Unirest.get(footballConfig.getApiFootballEndpoint() + "/v2/statistics/" + leagueId + "/" + teamId)
                .header("X-RapidAPI-Host", footballConfig.getHost())
                .header("X-RapidAPI-Key", footballConfig.getApiKey())
                .asJson();
        try {
            JSONObject myObj = response.getBody().getObject();
            JSONObject api = myObj.getJSONObject("api");
            JSONObject statistics = api.getJSONObject("statistics");
            JSONObject matchs = statistics.getJSONObject("matchs");
            JSONObject matchsPlayed = matchs.getJSONObject("matchsPlayed");
            int totalMatchs = Integer.parseInt(mapper.readValue(matchsPlayed.getJSONObject("total").toString(), String.class));
            JSONObject goalsAvg = statistics.getJSONObject("goalsAvg");
            JSONObject goalsFor = goalsAvg.getJSONObject("goalsFor");
            double goalsForAvg = Double.parseDouble(mapper.readValue(goalsFor.getJSONObject("total").toString(), String.class));
            JSONObject goalsAgainst = goalsAvg.getJSONObject("goalsAgainst");
            double goalsAgainstAvg = Double.parseDouble(mapper.readValue(goalsAgainst.getJSONObject("total").toString(), String.class));
            double totalGoalsAvg = goalsForAvg + goalsAgainstAvg;


            return new TeamStatisticsDto(teamId, totalMatchs,
                    goalsForAvg, goalsAgainstAvg, totalGoalsAvg);
        } catch (JSONException e) {
            LOGGER.error(e.getMessage(), e);
            return new TeamStatisticsDto();
        }
    }

    private JSONArray findObjectsInTeamResponse(HttpResponse<JsonNode> response) {
        return response.getBody().getObject().getJSONArray("response");
    }

    private JSONArray findObjectsInLeagueResponse(HttpResponse<JsonNode> response) {
        JSONObject myObj = response.getBody().getObject();
        JSONObject api = myObj.getJSONObject("api");
        return api.getJSONArray("leagues");
    }

    private TeamDto mapperForTeam(JSONObject jsonTeam, String teamOrVenue) throws IOException {
        return mapper.readValue(jsonTeam.get(teamOrVenue).toString(), TeamDto.class);
    }

    private TeamDto createTeamFromJsonResponse(JSONObject jsonTeam) throws IOException {
        TeamDto result = mapperForTeam(jsonTeam, "team");
        result.setCity(mapperForTeam(jsonTeam, "venue").getCity());
        return result;
    }


}
