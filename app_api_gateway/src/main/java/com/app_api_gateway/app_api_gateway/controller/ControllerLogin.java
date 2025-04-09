package com.app_api_gateway.app_api_gateway.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping(path = "/auth")
public class ControllerLogin {

    public final static String URL_API_BASE = "http://localhost:8081/user";

    private final RestTemplate restTemplate;

    @Autowired
    public ControllerLogin(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(HttpSession httpSession, @RequestParam String username,
            @RequestParam String password) {
        String urlApi = URL_API_BASE + "/checkuser";

        Map<String, String> credentials = new HashMap<String, String>();
        credentials.put("username", username);
        credentials.put("password", password);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(urlApi, credentials, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                String responseBody = response.getBody();
                if (responseBody != null && responseBody.contains("User correct")) {
                    httpSession.setAttribute("username", username);
                    return ResponseEntity.ok("Connecté");
                }
            }
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Requête invalide : données manquantes");
        } catch (HttpClientErrorException e) {
            return ResponseEntity.badRequest().body(e.getStatusCode() + " : " + e.getResponseBodyAsString());
        }

    }

    @PostMapping("/isadmin")
    public ResponseEntity<String> postMethodName(@RequestParam String username) {
        String urlApi = URL_API_BASE + "/isadmin";

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(urlApi, username, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                String responseBody = response.getBody();
                if (responseBody != null && responseBody.contains("User is admin")) {
                    return ResponseEntity.ok("User is admin");
                }
            }
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Requête invalide : données manquantes");
        } catch (HttpClientErrorException e) {
            return ResponseEntity.badRequest().body(e.getStatusCode() + " : " + e.getResponseBodyAsString());
        }

    }

    // faire le get infos

}
