package com.github.jasondavies1.lastfimtimetracker;

import com.github.jasondavies1.lastfimtimetracker.configuration.LastFmConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({LastFmConfigurationProperties.class})
public class LastFimTimeTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LastFimTimeTrackerApplication.class, args);
    }

}
