package io.github.alancavalcante_dev.desafio_picpay_backend.controller.dto;

public record AuthorizationResponse(String status, Data data) {
    public record Data(Boolean authorization) {}
}

