package com.github.jasondavies1.lastfimtimetracker.domain;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "timestamp")
public class TrackDTO {
    private String trackName;
    private String artist;
    private String albumName;
    private Integer playCount;
    private String timestamp;
}
