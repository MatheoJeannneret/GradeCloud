package com.app_api_gateway.app_api_gateway.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

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
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Requête invalide : paramètres invalides");
        } catch (HttpClientErrorException e) {
            return ResponseEntity.badRequest().body(e.getStatusCode() + " : " + e.getResponseBodyAsString());
        }

    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession httpSession) {
        httpSession.invalidate();
        return ResponseEntity.ok("déconnecté");
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

    @GetMapping("/getinfo")
    public ResponseEntity<HashMap<String, String>> getInfosUser(@RequestParam String username) {
        String urlApi = URL_API_BASE + "/getinfo";

        try {
            HttpEntity<String> param = new HttpEntity<>(username);
            ResponseEntity<HashMap<String, String>> response = restTemplate.exchange(
                    urlApi,
                    HttpMethod.POST,
                    param,
                    new ParameterizedTypeReference<HashMap<String, String>>() {
                    });

            if (response.getStatusCode().is2xxSuccessful()) {
                HashMap<String, String> responseBody = response.getBody();
                if (responseBody != null) {
                    return ResponseEntity.ok(responseBody);
                }
            }
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Requête invalide : données manquantes");
        } catch (HttpClientErrorException e) {
            HashMap<String, String> error = new HashMap<>();
            error.put("status", e.getStatusCode().toString());
            error.put("message", e.getResponseBodyAsString());

            return ResponseEntity.badRequest().body(error);
        }
    }

}
