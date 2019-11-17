package com.github.jasondavies1.lastfimtimetracker.service;

public interface PersistenceService {

    boolean noDataInDatabase();

    void persistAllTracks();

    void persistAllTracksFromTimestamp(int highestTimestamp);

    int getHighestTimestamp();

    int scrobbleCount();


}
