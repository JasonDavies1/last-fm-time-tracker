package com.github.jasondavies1.lastfimtimetracker.controller;

import com.github.jasondavies1.lastfimtimetracker.service.LastFmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ApplicationController {

    private final LastFmService lastFmService;

    @GetMapping
    @ResponseBody
    public ResponseEntity get() {
        lastFmService.getAllTracks();
        lastFmService.getAlbum();
        return ResponseEntity.ok("hello world");
    }

}
