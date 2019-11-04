package com.github.jasondavies1.lastfimtimetracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ApplicationController {

    @GetMapping
    @ResponseBody
    public ResponseEntity get() {
        return ResponseEntity.ok("hello world");
    }

}
