package com.github.jasondavies1.lastfimtimetracker.converter;

import com.github.jasondavies1.lastfimtimetracker.domain.TrackDTO;
import com.github.jasondavies1.lastfimtimetracker.domain.persistence.Track;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class TrackDtoToTrackConverter implements Converter<TrackDTO, Track> {
    @Override
    public Track convert(final TrackDTO trackDTO) {
        final Track track = new Track();
        track.setTrackName(trackDTO.getTrackName());
        track.setArtistName(trackDTO.getArtist());
        track.setAlbumName(trackDTO.getAlbumName());
        return track;
    }
}
