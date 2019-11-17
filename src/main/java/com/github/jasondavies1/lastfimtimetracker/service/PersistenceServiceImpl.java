package com.github.jasondavies1.lastfimtimetracker.service;

import com.github.jasondavies1.lastfimtimetracker.converter.TrackToScrobbleConverter;
import com.github.jasondavies1.lastfimtimetracker.domain.TrackDTO;
import com.github.jasondavies1.lastfimtimetracker.domain.persistence.Scrobble;
import com.github.jasondavies1.lastfimtimetracker.domain.persistence.Track;
import com.github.jasondavies1.lastfimtimetracker.repository.ScrobbleRepository;
import com.github.jasondavies1.lastfimtimetracker.repository.TrackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
    public void persistAllTracks() {
        try {
            final List<TrackDTO> allTracks = lastFmService.getAllTracks();
            allTracks.forEach(t -> {
                final Track savedTrack = trackRepository.findByArtistNameAndTrackName(t.getArtist(), t.getTrackName())
                        .orElseGet(() -> trackRepository.save(conversionService.convert(t, Track.class)));
                scrobbleRepository.save(trackToScrobbleConverter.convert(t, savedTrack));
            });
        } catch (final IOException e) {
            System.out.println("IOException");
        }
    }

    @Override
    public int getHighestTimestamp(){
        return scrobbleRepository.findAll().stream()
                .map(Scrobble::getTimestamp)
                .map(Integer::valueOf)
                .collect(Collectors.summarizingInt(Integer::intValue)).getMax();
    }

    @Override
    public int scrobbleCount() {
        return scrobbleRepository.findAll().size();
    }

}
