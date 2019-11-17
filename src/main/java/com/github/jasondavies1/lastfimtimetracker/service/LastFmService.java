package com.github.jasondavies1.lastfimtimetracker.service;

import com.github.jasondavies1.lastfimtimetracker.domain.TrackDTO;

import java.util.List;

public interface LastFmService {

    List<TrackDTO> getAllTracks();

    List<TrackDTO> getAllTracksFromTimestamp(int timestamp);

    int getNumberOfPages(String url);

    void getAlbum();
}
