package io.github.alancavalcante_dev.desafio_picpay_backend.service;

import io.github.alancavalcante_dev.desafio_picpay_backend.domain.TransactionNotificationError;
import io.github.alancavalcante_dev.desafio_picpay_backend.domain.User;
import io.github.alancavalcante_dev.desafio_picpay_backend.repository.TransactionNotificationErrorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


/**
 * Classe responsável por enviar notificações de transações por e-mail
 * através de uma API externa (https://util.devi.tools/api/v1/notify).
 *
 * A notificação contém o valor e uma mensagem personalizada.
 *
 * ⚠️ Importante:
 * A API de notificação pode falhar ou estar fora do ar. Quando isso ocorre,
 * o erro é capturado e salvo no banco de dados para monitoramento posterior,
 * usando o repositório TransactionNotificationErrorRepository.
 *
 * Toda falha é tratada com log e persistência para evitar que o erro passe despercebido.
 */


@Service
@Slf4j
public class TransactionNotificationSender {

    @Autowired
    private TransactionNotificationErrorRepository repository;

    private final RestTemplate restTemplate = new RestTemplate();


    public void sender(User user, String message, BigDecimal value) {
        HashMap<String, String> payload = new HashMap<>();
        payload.put("to", user.getEmail());
        payload.put("message", message + " De R$ " + value.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "https://util.devi.tools/api/v1/notify",
                    request,
                    String.class
            );

            if ( response.getStatusCode().is2xxSuccessful() ) {
                log.info("Notificação enviada para {}", user.getEmail());
            }
            throw new RestClientException("Falha ao enviar notificação.");

        } catch (RestClientException e) {
            log.info(e.getMessage());

            repository.save(new TransactionNotificationError(user));
            log.info("Erro de notificação salvada.");
        }
    }
}
