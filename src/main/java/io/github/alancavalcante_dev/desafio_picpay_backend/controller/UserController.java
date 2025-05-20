package io.github.alancavalcante_dev.desafio_picpay_backend.controller;

import io.github.alancavalcante_dev.desafio_picpay_backend.controller.dto.UserDTO;
import io.github.alancavalcante_dev.desafio_picpay_backend.domain.User;
import io.github.alancavalcante_dev.desafio_picpay_backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Endpoint de Usuário")
public class UserController {

    private final UserService service;

    @PostMapping
    @Operation(summary = "Registra um novo usuário")
    public ResponseEntity<Void> registerUser(@RequestBody UserDTO data) {
        log.info("Adicionando usuário");

        User user = User.dtoToEntity(data);
        user.setPassword(BCrypt.hashpw(data.password(), BCrypt.gensalt()));
        service.saveUser(user);

        return ResponseEntity.ok().build();
    }
}
