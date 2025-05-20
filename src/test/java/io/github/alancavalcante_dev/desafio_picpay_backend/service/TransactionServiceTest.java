package io.github.alancavalcante_dev.desafio_picpay_backend.service;


import io.github.alancavalcante_dev.desafio_picpay_backend.controller.dto.TransactionDTO;
import io.github.alancavalcante_dev.desafio_picpay_backend.domain.Transaction;
import io.github.alancavalcante_dev.desafio_picpay_backend.domain.User;
import io.github.alancavalcante_dev.desafio_picpay_backend.repository.TransactionRepository;
import io.github.alancavalcante_dev.desafio_picpay_backend.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;


@SpringBootTest
@ActiveProfiles("test")
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private TransactionRepository repository;

    @MockitoBean
    private AuthorizationTransaction authService;

    @MockitoBean
    private TransactionNotificationSender notificationService;

    @Test
    @DisplayName("A transação deve ser criada com sucesso quando tudo estiver OK")
    void registerTransactionOk() {
        User sender = new User(1L, "Pedro", "Teste", "12345678901", "pedro@gmail.com", "123456", false, new BigDecimal(10));
        User receiver = new User(2L, "Flavio", "Teste", "12345678902", "flavio@gmail.com", "123456", true, new BigDecimal(20));

        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepository.findById(2L)).thenReturn(Optional.of(receiver));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(authService.authorized()).thenReturn(true);

        TransactionDTO request = new TransactionDTO(new BigDecimal(10), 1L, 2L);
        transactionService.registerTransaction(Transaction.dtoToEntity(request));

        verify(repository, times(1)).save(any());
        verify(userRepository, times(2)).save(any(User.class));

        verify(notificationService, times(1)).sender(sender, "Pagamento realizado com sucesso!", request.value());
        verify(notificationService, times(1)).sender(receiver, "Você recebeu um pagamento!", request.value());
    }


    @Test
    @DisplayName("Deve lançar uma exceção quando a transação não for autorizada")
    void registerTransactionError() throws Exception {
        User sender = new User(1L, "Pedro", "Teste", "12345678901", "pedro@gmail.com", "123456", false, new BigDecimal(10));
        User receiver = new User(2L, "Flavio", "Teste", "12345678902", "flavio@gmail.com", "123456", true, new BigDecimal(20));

        when(userRepository.findById(sender.getId())).thenReturn(Optional.of(sender));
        when(userRepository.findById(receiver.getId())).thenReturn(Optional.of(receiver));
        when(authService.authorized()).thenReturn(false);

        TransactionDTO request = new TransactionDTO(new BigDecimal(10), 1L, 2L);

        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            transactionService.registerTransaction(Transaction.dtoToEntity(request));
        });

        Assertions.assertEquals("Sistema externo não autorizado.", thrown.getMessage());
    }
}
