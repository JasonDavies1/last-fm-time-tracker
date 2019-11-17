package com.github.jasondavies1.lastfimtimetracker.service;

import com.github.jasondavies1.lastfimtimetracker.domain.TrackDTO;

import java.io.IOException;
import java.util.List;

public interface LastFmService {

    List<TrackDTO> getAllTracks() throws IOException;

    int getNumberOfPages() throws IOException;

    void getAlbum();
}
