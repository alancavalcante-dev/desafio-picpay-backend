package io.github.alancavalcante_dev.desafio_picpay_backend.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;


public record UserDTO(
        @NotNull
        @NotBlank
        String firstName,

        @NotNull
        @NotBlank
        String lastName,

        @NotNull
        @NotBlank
        @Size(min = 11, max = 14)
        String cpfCnpj,

        @NotNull
        @NotBlank
        @Email
        String email,

        @NotNull
        @NotBlank
        String password,

        @NotNull
        Boolean isShopkeeper,

        @NotNull
        BigDecimal balance
) {}
