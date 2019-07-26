package com.tomaszorzol.seethematch.football.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class FootballConfig {

    @Value("${rapid.api.app.key}")
    private String apiKey;

    @Value("${rapid.football.api.endpoint.prod}")
    private String apiFootballEndpoint;

    @Value("${rapid.football.api.content.type}")
    private String contentType;

    @Value("${rapid.football.api.host}")
    private String host;
}
