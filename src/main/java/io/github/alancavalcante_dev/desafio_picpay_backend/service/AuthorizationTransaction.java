package io.github.alancavalcante_dev.desafio_picpay_backend.service;

import io.github.alancavalcante_dev.desafio_picpay_backend.controller.dto.AuthorizationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
public class AuthorizationTransaction {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String URL = "https://util.devi.tools/api/v2/authorize";

    public boolean authorized() {
        var response = restTemplate.getForObject(URL, AuthorizationResponse.class);
        if ( response.status().equals("success") && response.data().authorization()) {
            return true;
        } else return false;
    }
}


