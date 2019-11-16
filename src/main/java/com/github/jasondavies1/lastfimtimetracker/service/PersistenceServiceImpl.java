package com.github.jasondavies1.lastfimtimetracker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jasondavies1.lastfimtimetracker.converter.TrackToScrobbleConverter;
import com.github.jasondavies1.lastfimtimetracker.domain.TrackDTO;
import com.github.jasondavies1.lastfimtimetracker.domain.persistence.Scrobble;
import com.github.jasondavies1.lastfimtimetracker.domain.persistence.Track;
import com.github.jasondavies1.lastfimtimetracker.repository.ScrobbleRepository;
import com.github.jasondavies1.lastfimtimetracker.repository.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PersistenceServiceImpl implements PersistenceService {

    private final TrackRepository trackRepository;
    private final ScrobbleRepository scrobbleRepository;
    private final ConversionService conversionService;
    private final TrackToScrobbleConverter trackToScrobbleConverter;
    private final LastFmService lastFmService;

    @Override
    @SuppressWarnings("unchecked")
    public void persistTracks() {
        try {
            final int page = 1;
            final Map map = lastFmService.getPageOfScrobbles(page);
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
        } catch (final IOException e) {
            System.out.println("IOException");
        }
    }


}
