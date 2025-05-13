package io.github.alancavalcante_dev.desafio_picpay_backend.controller;

import io.github.alancavalcante_dev.desafio_picpay_backend.controller.dto.UserDTO;
import io.github.alancavalcante_dev.desafio_picpay_backend.domain.User;
import io.github.alancavalcante_dev.desafio_picpay_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<Void> registerUser(@RequestBody UserDTO data) {
        log.info("Adicionando usuário");

        User user = new User();

        user.setFirstName(data.firstName());
        user.setLastName(data.lastName());
        user.setCpfCnpj(data.cpfCnpj());
        user.setEmail(data.email());
        user.setPassword(BCrypt.hashpw(data.password(), BCrypt.gensalt()));
        user.setShopkeeper(data.isShopkeeper());
        user.setBalance(BigDecimal.valueOf(0));

        service.registerUser(user);

        return ResponseEntity.ok().build();
    }


}
