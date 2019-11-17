package com.github.jasondavies1.lastfimtimetracker.service;

public interface LastFmUrlService {

    String getTracksUrl(int page);

    String getRecentTracksUrl(
            int page,
            int timestampFrom);

    String getAlbumsUrl(String artistNAme, String albumName);

}
