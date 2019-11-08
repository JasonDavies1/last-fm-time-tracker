package com.github.jasondavies1.lastfimtimetracker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jasondavies1.lastfimtimetracker.configuration.LastFmConfigurationProperties;
import com.github.jasondavies1.lastfimtimetracker.domain.TrackDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class LastFmServiceImpl implements LastFmService {

    private final RestTemplate restTemplate;
    private final LastFmConfigurationProperties lastFmConfigurationProperties;

    private final Map<TrackDTO, Integer> trackCollection = new HashMap<>();

    @SuppressWarnings("unchecked")
    private static <T> T get(final Map map,
                             final String key) {
        return (T) map.get(key);
    }

    private int totalPages() throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final String body = getPage(1);
        final Map map = mapper.readValue(body, Map.class);
        final Map recentTracks = get(map, "recenttracks");
        final Map attributes = get(recentTracks, "@attr");
        return Integer.valueOf(get(attributes, "totalPages"));
    }

    private String getPage(final int pageNumber) {
        final String apiKey = lastFmConfigurationProperties.getApiKey();
        final String url = trackUrl(apiKey, pageNumber);
        final ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        return forEntity.getBody();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getAllTracks() throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final int totalPages = totalPages();

        IntStream.rangeClosed(1, totalPages)
                .forEach(i -> {
                    if (i % 10 == 0) {
                        log.info("processing page {} of {}", i, totalPages);
                    }

                    final String body = getPage(i);
                    try {
                        final Map map = mapper.readValue(body, Map.class);
                        final Map recentTracks = get(map, "recenttracks");
                        final List track = get(recentTracks, "track");

                        track.stream()
                                .map(t -> {
                                    final Map trackMap = (Map) t;
                                    final Map artist = get(trackMap, "artist");
                                    final Map album = get(trackMap, "album");
                                    return TrackDTO.builder()
                                            .artist(get(artist, "name"))
                                            .albumName(get(album, "#text"))
                                            .trackName(get(trackMap, "name"))
                                            .build();
                                })
                                .forEach(trackDto -> Optional.ofNullable(trackCollection.get(trackDto))
                                        .ifPresentOrElse(
                                                playCount -> trackCollection.put((TrackDTO) trackDto, (playCount + 1)),
                                                () -> trackCollection.put((TrackDTO) trackDto, 1)));
                    } catch (final IOException e) {
                        System.out.println("IOException");
                    }
                });

        System.out.println("ok");

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
