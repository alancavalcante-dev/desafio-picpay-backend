package io.github.alancavalcante_dev.desafio_picpay_backend.controller.dto;

import java.math.BigDecimal;


public record TransactionDTO(
        BigDecimal value,
        Long payer,
        Long payee
) {}
