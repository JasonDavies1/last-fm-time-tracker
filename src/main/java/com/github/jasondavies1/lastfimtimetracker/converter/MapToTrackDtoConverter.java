package com.github.jasondavies1.lastfimtimetracker.converter;

import com.github.jasondavies1.lastfimtimetracker.domain.TrackDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MapToTrackDtoConverter implements Converter<Map, TrackDTO> {

    @Override
    public TrackDTO convert(final Map trackDetails) {
        final Map artist = (Map) trackDetails.get("artist");
        final Map album = (Map) trackDetails.get("album");
        return TrackDTO.builder()
                .artist((String) artist.get("name"))
                .albumName((String) album.get("#text"))
                .trackName((String) trackDetails.get("name"))
                .build();
    }
}
