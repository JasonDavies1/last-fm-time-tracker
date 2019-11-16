package com.github.jasondavies1.lastfimtimetracker.service;

import java.io.IOException;
import java.util.Map;

public interface LastFmService {

    void getAllTracks() throws IOException;

    int getNumberOfPages() throws IOException;

    Map getPageOfScrobbles(int pageNumber) throws IOException;

    void getAlbum();
}
