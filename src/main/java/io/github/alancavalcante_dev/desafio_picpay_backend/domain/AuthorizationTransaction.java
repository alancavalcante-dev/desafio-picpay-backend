package io.github.alancavalcante_dev.desafio_picpay_backend.domain;

import io.github.alancavalcante_dev.desafio_picpay_backend.controller.dto.AuthorizationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
public class AuthorizationTransaction {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String URL = "https://util.devi.tools/api/v2/authorize";

    public boolean authorized() {
        try {
            var response = restTemplate.getForObject(URL, AuthorizationResponse.class);
            return response != null
                    && "success".equalsIgnoreCase(response.status())
                    && Boolean.TRUE.equals(response.data().authorization());
        } catch (RestClientException e) {
            log.info("Erro ao chamar serviço autorizador: {}", e.getMessage());
            return false;
        }
    }
}


