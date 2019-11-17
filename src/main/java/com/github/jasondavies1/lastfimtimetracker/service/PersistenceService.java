package com.github.jasondavies1.lastfimtimetracker.service;

public interface PersistenceService {

    boolean noDataInDatabase();

    void persistAllTracks();

    int getHighestTimestamp();

    int scrobbleCount();


}
