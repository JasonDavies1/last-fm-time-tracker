package com.github.jasondavies1.lastfimtimetracker.repository;

import com.github.jasondavies1.lastfimtimetracker.domain.persistence.Scrobble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrobbleRepository extends JpaRepository<Scrobble, Integer> {
}
