package com.github.jasondavies1.lastfimtimetracker.service;

public interface LastFmUrlService {

    String getRecentTracksUrl(int page);

    String getRecentTracksUrlFromTimestamp(int page,
                                           int timestampFrom);

    String getAlbumsUrl(String artistNAme, String albumName);

}
