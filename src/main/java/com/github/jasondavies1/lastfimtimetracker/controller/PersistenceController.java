package com.github.jasondavies1.lastfimtimetracker.controller;

import com.github.jasondavies1.lastfimtimetracker.service.PersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persist")
@RequiredArgsConstructor
public class PersistenceController {

    private final PersistenceService persistenceService;

    @GetMapping
    @ResponseBody
    public ResponseEntity persistTracks() {
        persistenceService.persistTracks();
        return ResponseEntity.ok("persisted");
    }

}
