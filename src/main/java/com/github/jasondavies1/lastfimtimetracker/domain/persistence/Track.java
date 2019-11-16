package com.github.jasondavies1.lastfimtimetracker.domain.persistence;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String artistName;
    private String albumName;
    private String trackName;
    private String durationInSeconds;

}
