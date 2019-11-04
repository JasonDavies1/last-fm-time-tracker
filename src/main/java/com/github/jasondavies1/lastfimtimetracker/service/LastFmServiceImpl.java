package com.github.jasondavies1.lastfimtimetracker.service;

import com.github.jasondavies1.lastfimtimetracker.configuration.LastFmConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class LastFmServiceImpl implements LastFmService {

    private final RestTemplate restTemplate;
    private final LastFmConfigurationProperties lastFmConfigurationProperties;

    @Override
    public void getAllTracks() {
        final String apiKey = lastFmConfigurationProperties.getApiKey();
        final String url = trackUrl(apiKey, 1);
        final ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        System.out.println(forEntity.getBody());
    }

    private String trackUrl(
            final String apiKey,
            final int page){
        return "http://ws.audioscrobbler.com/2.0/" +
                "?method=user.getrecenttracks" +
                "&user=JasonDavies_" +
                "&api_key=" + apiKey +
                "&page=" + page +
                "&limit=200" +
                "&extended=1" +
                "&format=json";
    }

}
