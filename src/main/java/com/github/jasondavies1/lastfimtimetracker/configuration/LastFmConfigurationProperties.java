package com.github.jasondavies1.lastfimtimetracker.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "lastfm")
public class LastFmConfigurationProperties {
    private String apiKey;
}
