package com.github.jasondavies1.lastfimtimetracker.service;

import com.github.jasondavies1.lastfimtimetracker.domain.TrackDTO;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface LastFmService {

    List<TrackDTO> getAllTracks() throws IOException;

    int getNumberOfPages() throws IOException;

    Map getPageOfScrobbles(int pageNumber) throws IOException;

    void getAlbum();
}
