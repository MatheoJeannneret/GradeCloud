package com.app_api_gateway.app_api_gateway.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

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
    public ResponseEntity<?> getNotesByEleve(
            HttpSession session,
            @RequestParam String username) {

        try {
            // 🔐 Vérification session
            String sessionUser = (String) session.getAttribute("username");
            String isAdmin = (String) session.getAttribute("isAdmin");

            if (sessionUser == null || isAdmin == null || !sessionUser.equals(username) || !"false".equals(isAdmin)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Pas connecté ou pas les droits"));
            }

            // 🔗 Construction de l'URL avec le paramètre username
            String fullUrl = URL_API_BASE + "/getnotesbyeleve?username="
                    + URLEncoder.encode(username, StandardCharsets.UTF_8);

            // 📡 Appel à l'API distante
            ResponseEntity<List<HashMap<String, Object>>> response = restTemplate.exchange(
                    fullUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    });

            // ✅ Retour si OK
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return ResponseEntity.ok(response.getBody());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "Erreur lors de la récupération des notes"));
            }

        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(Map.of("error", e.getStatusCode().toString(), "message", e.getResponseBodyAsString()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Exception inconnue", "message", e.getMessage()));
        }
    }

    @GetMapping("/getexamensbyclasse")
    public ResponseEntity<?> getExamensByClasse(HttpSession session, @RequestParam String classe) {
        try {
            // 🔐 Vérification session
            String sessionUser = (String) session.getAttribute("username");
            String isAdmin = (String) session.getAttribute("isAdmin");

            if (sessionUser == null || isAdmin == null || !"true".equals(isAdmin)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Pas connecté ou pas les droits"));
            }

            // 🔗 Construction de l'URL avec le paramètre username
            String fullUrl = URL_API_BASE + "/getexamensbyclasse?classNom="
                    + URLEncoder.encode(classe, StandardCharsets.UTF_8);

            // 📡 Appel à l'API distante
            ResponseEntity<List<Object>> response = restTemplate.exchange(
                    fullUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    });

            // ✅ Retour si OK
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return ResponseEntity.ok(response.getBody());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "Erreur lors de la récupération des notes"));
            }

        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(Map.of("error", e.getStatusCode().toString(), "message", e.getResponseBodyAsString()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Exception inconnue", "message", e.getMessage()));
        }
    }

    @PostMapping("/createexamen")
    public ResponseEntity<?> createExamen(HttpSession session, @RequestParam String nom,
            @RequestParam String description,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
            @RequestParam Integer brancheId, @RequestParam Integer classeId) {
        try {
            // 🔐 Vérification session
            String sessionUser = (String) session.getAttribute("username");
            String isAdmin = (String) session.getAttribute("isAdmin");

            if (sessionUser == null || isAdmin == null || !"true".equals(isAdmin)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Pas connecté ou pas les droits"));
            }

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("nom", nom);
            params.add("description", description);
            params.add("date", date.toString()); // Format ISO-8601
            params.add("brancheId", brancheId.toString());
            params.add("classeId", classeId.toString());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            ResponseEntity<Object> response = restTemplate.postForEntity(URL_API_BASE + "/createexamen", request,
                    Object.class);

            // ✅ Retour si OK
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return ResponseEntity.ok(response.getBody());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "Erreur lors de la récupération des notes"));
            }

        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(Map.of("error", e.getStatusCode().toString(), "message", e.getResponseBodyAsString()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Exception inconnue", "message", e.getMessage()));
        }
    }

}
