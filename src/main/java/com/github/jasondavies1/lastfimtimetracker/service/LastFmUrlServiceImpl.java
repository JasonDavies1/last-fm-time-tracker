package com.github.jasondavies1.lastfimtimetracker.service;

import com.github.jasondavies1.lastfimtimetracker.configuration.LastFmConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LastFmUrlServiceImpl implements LastFmUrlService {

    private final LastFmConfigurationProperties lastFmConfigurationProperties;

    @Override
    public String getTracksUrl(int page) {
        return "http://ws.audioscrobbler.com/2.0/" +
                "?method=user.getrecenttracks" +
                "&user=JasonDavies_" +
                "&api_key=" + lastFmConfigurationProperties.getApiKey() +
                "&page=" + page +
                "&limit=200" +
                "&extended=1" +
                "&format=json";

    }

    @Override
    public String getAlbumsUrl(
            final String artistName,
            final String albumName) {
        return "http://ws.audioscrobbler.com/2.0/" +
                "?method=album.getinfo" +
                "&api_key=" + lastFmConfigurationProperties.getApiKey() +
                "&artist=" + artistName +
                "&album=" + albumName +
                "&format=json";

    }
}
