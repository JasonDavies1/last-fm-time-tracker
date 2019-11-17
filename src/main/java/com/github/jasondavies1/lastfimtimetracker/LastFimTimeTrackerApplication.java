package com.github.jasondavies1.lastfimtimetracker;

import com.github.jasondavies1.lastfimtimetracker.configuration.LastFmConfigurationProperties;
import com.github.jasondavies1.lastfimtimetracker.service.PersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties({LastFmConfigurationProperties.class})
@RequiredArgsConstructor
public class LastFimTimeTrackerApplication implements CommandLineRunner {

    private final PersistenceService persistenceService;

    public static void main(String[] args) {
        SpringApplication.run(LastFimTimeTrackerApplication.class, args);
    }

    @Override
    public void run(final String... args) {
        if (persistenceService.noDataInDatabase()){
            log.info("No scrobbles recorded, retrieving and persisting all data for account");
            persistenceService.persistAllTracks();
        } else {
            final int preUpdateCount = persistenceService.scrobbleCount();
            final int highestTimestamp = persistenceService.getHighestTimestamp();
            log.info("Existing scrobbles found: {}", preUpdateCount);
            log.info("Updating from timestamp: {}...", highestTimestamp);
            persistenceService.persistAllTracksFromTimestamp(highestTimestamp);
            final int postUpdateCount = persistenceService.scrobbleCount();
            log.info("Successfully added {} scrobbles. Total scrobble count: {}",
                    (postUpdateCount - preUpdateCount),
                    postUpdateCount);
        }
    }
}
