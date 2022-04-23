package com.mendel.mendelchallenge.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthcheckController {

    @GetMapping("/ping")
    public String Healthcheck() {
        return "pong";
    }
}