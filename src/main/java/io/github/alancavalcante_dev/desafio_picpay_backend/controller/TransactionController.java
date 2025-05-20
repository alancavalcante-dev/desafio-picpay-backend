package io.github.alancavalcante_dev.desafio_picpay_backend.controller;


import io.github.alancavalcante_dev.desafio_picpay_backend.controller.dto.TransactionDTO;
import io.github.alancavalcante_dev.desafio_picpay_backend.domain.Transaction;
import io.github.alancavalcante_dev.desafio_picpay_backend.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
@Tag(name = "Endpoint de Transação")
public class TransactionController {

    private final TransactionService service;

    @PostMapping
    @Operation(summary = "Registra uma nova transação")
    public ResponseEntity<Void> registerTransaction(@RequestBody TransactionDTO data) {
        log.info("Registrando uma transação");

        Transaction transaction = new Transaction(data.payer(), data.payee(), data.value());
        service.registerTransaction(transaction);
        return ResponseEntity.ok().build();
    }
}
