package com.github.jasondavies1.lastfimtimetracker.service;

public interface LastFmUrlService {

    String getTracksUrl(int page);

    String getAlbumsUrl(String artistNAme, String albumName);

}
