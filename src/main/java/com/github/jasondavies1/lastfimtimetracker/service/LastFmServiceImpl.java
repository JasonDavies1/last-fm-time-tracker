package com.github.jasondavies1.lastfimtimetracker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jasondavies1.lastfimtimetracker.domain.TrackDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class LastFmServiceImpl implements LastFmService {

    private final LastFmUrlService urlService;
    private final ConversionService conversionService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public List<TrackDTO> getAllTracks() {
        final List<TrackDTO> allTracks = new ArrayList<>();
        IntStream.rangeClosed(1, getNumberOfPages(urlService.getRecentTracksUrl(1)))
                .forEach(currentPage -> {
                    final String currentUrl = urlService.getRecentTracksUrl(currentPage);
                    addTracksToCollection(allTracks, currentUrl);
                });
        return allTracks;
    }

    @Override
    public List<TrackDTO> getAllTracksFromTimestamp(int timestamp) {
        final List<TrackDTO> allTracks = new ArrayList<>();
        IntStream.rangeClosed(1, getNumberOfPages(urlService.getRecentTracksUrlFromTimestamp(1, timestamp)))
                .forEach(currentPage -> {
                    final String currentUrl = urlService.getRecentTracksUrlFromTimestamp(currentPage, timestamp);
                    addTracksToCollection(allTracks, currentUrl);
                });
        return allTracks;
    }

    @Override
    public int getNumberOfPages(final String url) {
        final String body = getPage(url);
        try {
            final Map map = objectMapper.readValue(body, Map.class);
            final Map recentTracks = (Map) map.get("recenttracks");
            final Map attributes = (Map) recentTracks.get("@attr");
            return Integer.valueOf((String) attributes.get("totalPages"));
        } catch (final IOException e) {
            return 0;
        }
    }

    @Override
    public void getAlbum() {
        final String url = urlService.getAlbumsUrl("Sweet Valley", "Eternal Champ");
        final ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        System.out.println(forEntity.getBody());
    }

    @SuppressWarnings("unchecked")
    private void addTracksToCollection(
            final List<TrackDTO> allTracks,
            final String currentUrl) {
        try {
            final Map map = objectMapper.readValue(getPage(currentUrl), Map.class);
            final Map recentTracks = (Map) map.get("recenttracks");
            final List track = (List) recentTracks.get("track");
            track.forEach(t -> allTracks.add(conversionService.convert(t, TrackDTO.class)));
        } catch (final IOException e) {
            System.out.println("IOException");
        }
    }

    private String getPage(final String url) {
        return restTemplate.getForEntity(url, String.class).getBody();
    }

}
