package com.github.jasondavies1.lastfimtimetracker.service;

import java.io.IOException;

public interface LastFmService {

    void getAllTracks() throws IOException;

    void getAlbum();
}
