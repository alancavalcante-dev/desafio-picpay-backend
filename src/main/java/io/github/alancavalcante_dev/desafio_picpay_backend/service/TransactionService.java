package io.github.alancavalcante_dev.desafio_picpay_backend.service;

import io.github.alancavalcante_dev.desafio_picpay_backend.domain.Transaction;
import io.github.alancavalcante_dev.desafio_picpay_backend.domain.User;
import io.github.alancavalcante_dev.desafio_picpay_backend.repository.TransactionRepository;
import io.github.alancavalcante_dev.desafio_picpay_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final AuthorizationTransaction authorizationTransaction;
    private final NotificationTransactionSender notificationTransactionSender;

    @Transactional
    public void registerTransaction(Transaction transaction) {
        validatorTransaction(transaction);

        User payer = userRepository.findById(transaction.getPayer())
                .orElseThrow(() -> new RuntimeException("Pagador não encontrado"));

        User payee = userRepository.findById(transaction.getPayee())
                .orElseThrow(() -> new RuntimeException("Recebedor não encontrado"));

        if (payer.isShopkeeper()) {
            throw new RuntimeException("O lojista não pode fazer pagamento");
        }

        if (payer.getBalance().compareTo(transaction.getValue()) < 0) {
            throw new RuntimeException("O pagador não tem saldo suficiente");
        }

        BigDecimal value = transaction.getValue();

        payer.setBalance(payer.getBalance().subtract(value));
        payee.setBalance(payee.getBalance().add(value));

        userRepository.save(payer);
        userRepository.save(payee);

        if (!authorizationTransaction.authorized()) {
            throw new RuntimeException("Sistema externo não autorizado.");
        }

        transactionRepository.save(transaction);

        notificationTransactionSender.sender(payer, "Pagamento realizado com sucesso!", value);
        notificationTransactionSender.sender(payee, "Você recebeu um pagamento!", value);
    }

    public void validatorTransaction(Transaction transaction) {
        validateFields(transaction.getValue());
        validateUsersEquals(transaction.getPayer(), transaction.getPayee());
    }

    public void validateFields(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Tem que passar algum valor!");
        }
    }

    public void validateUsersEquals(Long idPayer, Long idPayee) {
        if (idPayer.equals(idPayee)) {
            throw new RuntimeException("Usuários iguais");
        }
    }
}
