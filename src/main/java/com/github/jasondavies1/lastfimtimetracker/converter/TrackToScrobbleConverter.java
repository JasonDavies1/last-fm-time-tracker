package com.github.jasondavies1.lastfimtimetracker.converter;

import com.github.jasondavies1.lastfimtimetracker.domain.TrackDTO;
import com.github.jasondavies1.lastfimtimetracker.domain.persistence.Scrobble;
import com.github.jasondavies1.lastfimtimetracker.domain.persistence.Track;
import org.springframework.stereotype.Service;

@Service
public class TrackToScrobbleConverter {

    public Scrobble convert(
            final TrackDTO trackDTO,
            final Track track) {
        final Scrobble scrobble = new Scrobble();
        scrobble.setTrack(track);
        scrobble.setTimestamp(trackDTO.getTimestamp());
        return scrobble;
    }

}
