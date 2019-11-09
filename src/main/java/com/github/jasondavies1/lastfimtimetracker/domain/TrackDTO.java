package com.github.jasondavies1.lastfimtimetracker.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackDTO {
    private String trackName;
    private String artist;
    private String albumName;
    private Integer playCount;
}
