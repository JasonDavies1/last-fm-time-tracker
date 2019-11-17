package com.github.jasondavies1.lastfimtimetracker.domain.persistence;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String artistName;

    @Column(columnDefinition = "TEXT")
    private String albumName;

    @Column(columnDefinition = "TEXT")
    private String trackName;

    @Column(name = "duration")
    private String durationInSeconds;

}
