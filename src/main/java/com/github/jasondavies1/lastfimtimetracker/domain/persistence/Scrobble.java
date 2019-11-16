package com.github.jasondavies1.lastfimtimetracker.domain.persistence;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Scrobble {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private Track track;
    private String timestamp;
}
