package com.app_api_gateway.app_api_gateway.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(path = "/note")
public class ControllerNote {

    public final static String URL_API_BASE = "http://localhost:8082";

    private final RestTemplate restTemplate;

    @Autowired
    public ControllerNote(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/getnotesbyeleve")
    public ResponseEntity<?> getMethodName(HttpSession httpSession,
            @RequestParam String username) {
        String urlApi = URL_API_BASE + "/getnotesbyeleve";

        try {
            if(httpSession.getAttribute("username").equals(username))
        } catch (HttpClientErrorException e) {
            return ResponseEntity.badRequest().body(e.getStatusCode() + " : " + e.getResponseBodyAsString());
        }
    }

}
