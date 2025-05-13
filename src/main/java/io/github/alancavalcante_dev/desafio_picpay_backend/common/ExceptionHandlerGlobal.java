package io.github.alancavalcante_dev.desafio_picpay_backend.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionHandlerGlobal {

    /** Classe feita para nõo passar erros para o usuário que esteja consultando.
     * Assim não passa erros que possam gerar uma falha de segurança.
     * */

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException ex) {
        return ResponseEntity.unprocessableEntity().build();
    }

}