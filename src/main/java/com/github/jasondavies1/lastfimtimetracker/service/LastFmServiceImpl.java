package com.github.jasondavies1.lastfimtimetracker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jasondavies1.lastfimtimetracker.configuration.LastFmConfigurationProperties;
import com.github.jasondavies1.lastfimtimetracker.domain.TrackDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LastFmServiceImpl implements LastFmService {

    private final RestTemplate restTemplate;
    private final LastFmConfigurationProperties lastFmConfigurationProperties;

    @SuppressWarnings("unchecked")
    private static <T> T get(final Map map,
                             final String key) {
        return (T) map.get(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getAllTracks() throws IOException {

        final ObjectMapper mapper = new ObjectMapper();

        final String apiKey = lastFmConfigurationProperties.getApiKey();
        final String url = trackUrl(apiKey, 1);
        final ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        final String body = forEntity.getBody();

        final Map map = mapper.readValue(body, Map.class);
        final Map recentTracks = get(map, "recenttracks");
        final List track = get(recentTracks, "track");
        final Map<TrackDTO, Integer> trackCollection = new HashMap<>();

        track.forEach(t -> {
            final Map trackMap = (Map) t;
            final Map artist = get(trackMap, "artist");
            final Map album = get(trackMap, "album");
            final TrackDTO build = TrackDTO.builder()
                    .artist(get(artist, "name"))
                    .albumName(get(album, "#text"))
                    .trackName(get(trackMap, "name"))
                    .build();

            Optional.ofNullable(trackCollection.get(build))
                    .ifPresentOrElse(
                            i -> trackCollection.put(build, (i + 1)),
                            () -> trackCollection.put(build, 1)
                    );
        });
        System.out.println(body);
    }

    @Override
    public void getAlbum() {
        final String apiKey = lastFmConfigurationProperties.getApiKey();
        final String url = albumUrl(apiKey, "Sweet Valley", "Eternal Champ");
        final ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        System.out.println(forEntity.getBody());
    }

    private String trackUrl(
            final String apiKey,
            final int page) {
        return "http://ws.audioscrobbler.com/2.0/" +
                "?method=user.getrecenttracks" +
                "&user=JasonDavies_" +
                "&api_key=" + apiKey +
                "&page=" + page +
                "&limit=200" +
                "&extended=1" +
                "&format=json";
    }

    private String albumUrl(
            final String apiKey,
            final String artist,
            final String album) {
        return "http://ws.audioscrobbler.com/2.0/" +
                "?method=album.getinfo" +
                "&api_key=" + apiKey +
                "&artist=" + artist +
                "&album=" + album +
                "&format=json";
    }

}
