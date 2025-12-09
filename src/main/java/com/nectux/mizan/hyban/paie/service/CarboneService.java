package com.nectux.mizan.hyban.paie.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nectux.mizan.hyban.paie.dto.BulletinPaieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class CarboneService {

    private final RestTemplate restTemplate;

    @Value("${carbone.url}") // Exemple : http://localhost:4000
    private String carboneUrl;

    public CarboneService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public byte[] generateBulletin(Map<String, Object> data, String format) throws Exception {

        // Charger le template depuis resources
        InputStream templateStream = getClass()
                .getResourceAsStream("/templates/Bulletin_de_paie_Type10_optionA.docx");

        if (templateStream == null) {
            throw new FileNotFoundException("Template non trouvé dans /templates !");
        }

        byte[] templateBytes = templateStream.readAllBytes();
        String templateBase64 = Base64.getEncoder().encodeToString(templateBytes);

        // Préparer le payload pour Carbone
        Map<String, Object> payload = new HashMap<>();
        payload.put("template", "base64:" + templateBase64);// template en base64
        payload.put("data", data);              // les données du bulletin
        payload.put("convertTo", format);       // "pdf" ou "docx"

        // Appel REST POST
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                carboneUrl + "/render", request, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            // Carbone renvoie "content" encodé en base64
            String base64Content = (String) response.getBody().get("content");
            return Base64.getDecoder().decode(base64Content);
        } else {
            throw new RuntimeException("Erreur Carbone : " + response.getStatusCode());
        }
    }
}

