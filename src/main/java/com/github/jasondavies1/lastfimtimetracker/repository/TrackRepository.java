package com.github.jasondavies1.lastfimtimetracker.repository;

import com.github.jasondavies1.lastfimtimetracker.domain.persistence.Track;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrackRepository extends JpaRepository<Track, Integer> {

    Optional<Track> findByArtistNameAndTrackName(String artistName,
                                                 String trackName);

}
