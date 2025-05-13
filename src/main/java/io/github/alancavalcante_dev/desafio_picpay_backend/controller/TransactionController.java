package io.github.alancavalcante_dev.desafio_picpay_backend.controller;


import io.github.alancavalcante_dev.desafio_picpay_backend.controller.dto.TransactionDTO;
import io.github.alancavalcante_dev.desafio_picpay_backend.domain.Transaction;
import io.github.alancavalcante_dev.desafio_picpay_backend.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransactionController {

    private TransactionService service;

    @PostMapping
    public ResponseEntity<Void> registerTransaction(@RequestBody TransactionDTO data) {
        Transaction transaction = new Transaction();

        transaction.setPayee(data.payee());
        transaction.setPayer(data.payer());
        transaction.setValue(data.value());

        service.registerTransaction(transaction);
        return ResponseEntity.ok().build();
    }

}
