package io.github.alancavalcante_dev.desafio_picpay_backend.service;

import io.github.alancavalcante_dev.desafio_picpay_backend.controller.dto.AuthorizationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * Serviço responsável por realizar a verificação de autorização de transações
 * através de uma API externa (https://util.devi.tools/api/v2/authorize).
 *
 * A API retorna um JSON contendo um status e um campo booleano indicando se a
 * transação foi autorizada ou não. Contudo, **essa API é instável**, podendo:
 *
 * - Retornar falha de autorização mesmo para transações válidas.
 * - Estar fora do ar ou indisponível em certos momentos.
 *
 * Por isso, **é fundamental que o método `authorized()` seja tratado com cautela**,
 */


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


