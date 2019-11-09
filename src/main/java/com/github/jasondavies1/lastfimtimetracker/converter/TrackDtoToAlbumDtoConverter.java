package com.github.jasondavies1.lastfimtimetracker.converter;

import com.github.jasondavies1.lastfimtimetracker.domain.AlbumDTO;
import com.github.jasondavies1.lastfimtimetracker.domain.TrackDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class TrackDtoToAlbumDtoConverter implements Converter<TrackDTO, AlbumDTO> {

    @Override
    public AlbumDTO convert(final TrackDTO trackDTO) {
    return AlbumDTO.builder()
            .artistName(trackDTO.getArtist())
            .albumName(trackDTO.getAlbumName())
            .build();
    }
}
