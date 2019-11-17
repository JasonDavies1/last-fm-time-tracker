package com.github.jasondavies1.lastfimtimetracker.service;

import com.github.jasondavies1.lastfimtimetracker.converter.TrackToScrobbleConverter;
import com.github.jasondavies1.lastfimtimetracker.domain.TrackDTO;
import com.github.jasondavies1.lastfimtimetracker.domain.persistence.Track;
import com.github.jasondavies1.lastfimtimetracker.repository.ScrobbleRepository;
import com.github.jasondavies1.lastfimtimetracker.repository.TrackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersistenceServiceImpl implements PersistenceService {

    private final TrackRepository trackRepository;
    private final ScrobbleRepository scrobbleRepository;
    private final ConversionService conversionService;
    private final TrackToScrobbleConverter trackToScrobbleConverter;
    private final LastFmService lastFmService;

    @Override
    public boolean noDataInDatabase() {
        return scrobbleRepository.findAll().isEmpty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void persistAllTracks() {
        try {
            final int totalPages = lastFmService.getNumberOfPages();
            IntStream.rangeClosed(1, totalPages)
                    .forEach(currentPage -> {
                        if (currentPage % 10 == 0) {
                            log.info("Initial store is on page {} of {}", currentPage, totalPages);
                        }
                        try {
                            final Map map = lastFmService.getPageOfScrobbles(currentPage);
                            final Map recentTracks = (Map) map.get("recenttracks");
                            final List tracks = (List) recentTracks.get("track");
                            tracks.forEach(t -> {
                                final Map trackMap = (Map) t;
                                final TrackDTO trackDTO
                                        = conversionService.convert(trackMap, TrackDTO.class);
                                final Track savedTrack =
                                        trackRepository.findByArtistNameAndTrackName(trackDTO.getArtist(), trackDTO.getTrackName())
                                                .orElseGet(() -> trackRepository.save(conversionService.convert(trackDTO, Track.class)));
                                scrobbleRepository.save(trackToScrobbleConverter.convert(trackDTO, savedTrack));
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (final IOException e) {
            System.out.println("IOException");
        }
    }

    @Override
    public int scrobbleCount() {
        return scrobbleRepository.findAll().size();
    }


}
