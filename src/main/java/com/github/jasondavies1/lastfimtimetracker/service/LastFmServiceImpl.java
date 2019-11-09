package com.github.jasondavies1.lastfimtimetracker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jasondavies1.lastfimtimetracker.configuration.LastFmConfigurationProperties;
import com.github.jasondavies1.lastfimtimetracker.domain.TrackDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
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

    private final LastFmUrlService urlService;
    private final ConversionService conversionService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final Map<TrackDTO, Integer> trackCollection = new HashMap<>();

    @Override
    public void getAllTracks() throws IOException {
        //TODO inline this variable if/when logging statement removed
        final int totalPages = totalPages();

        IntStream.rangeClosed(1, totalPages)
                .forEach(currentPage -> {
                    if (currentPage % 10 == 0) {
                        log.info("processing page {} of {}", currentPage, totalPages);
                    }
                    addTracksToCollection(currentPage);
                });
    }

    @Override
    public void getAlbum() {
        final String url = urlService.getAlbumsUrl("Sweet Valley", "Eternal Champ");
        final ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        System.out.println(forEntity.getBody());
    }

    private int totalPages() throws IOException {
        final String body = getPage(1);
        final Map map = objectMapper.readValue(body, Map.class);
        final Map recentTracks = (Map) map.get("recenttracks");
        final Map attributes = (Map) recentTracks.get("@attr");
        return Integer.valueOf((String) attributes.get("totalPages"));
    }

    private String getPage(final int pageNumber) {
        final String url = urlService.getTracksUrl(pageNumber);
        final ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        return forEntity.getBody();
    }

    @SuppressWarnings("unchecked")
    private void addTracksToCollection(final int page) {
        try {
            final Map map = objectMapper.readValue(getPage(page), Map.class);
            final Map recentTracks = (Map) map.get("recenttracks");
            final List track = (List) recentTracks.get("track");
            track.stream()
                    .map(t -> conversionService.convert(t, TrackDTO.class))
                    .forEach(trackDto -> Optional.ofNullable(trackCollection.get(trackDto))
                            .ifPresentOrElse(
                                    playCount -> trackCollection.put((TrackDTO) trackDto, (playCount + 1)),
                                    () -> trackCollection.put((TrackDTO) trackDto, 1)));
        } catch (final IOException e) {
            System.out.println("IOException");
        }
    }
}
