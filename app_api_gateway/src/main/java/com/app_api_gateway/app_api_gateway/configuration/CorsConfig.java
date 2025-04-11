package com.app_api_gateway.app_api_gateway.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Indique à Spring que cette classe contient une configuration
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    // Surcharge la méthode qui permet de configurer les règles CORS de l'application
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Applique les règles CORS à toutes les URL de l'application (/**)
        registry.addMapping("/**")
            // Autorise les requêtes provenant de http://localhost:5500
            // → à adapter selon le domaine de ton frontend (en prod par ex : https://nom.emf-informatique.ch)
            .allowedOrigins("https://darazsj.emf-informatique.ch") //pour life server
            // Autorise uniquement ces méthodes HTTP dans les requêtes cross-origin
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            // Autorise l'envoi de cookies et d'en-têtes d'authentification (ex: Authorization, JSESSIONID)
            .allowCredentials(true);
    }
}