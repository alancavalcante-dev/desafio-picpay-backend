package io.github.alancavalcante_dev.desafio_picpay_backend.service;

import io.github.alancavalcante_dev.desafio_picpay_backend.domain.Transaction;
import io.github.alancavalcante_dev.desafio_picpay_backend.domain.User;
import io.github.alancavalcante_dev.desafio_picpay_backend.repository.TransactionRepository;
import io.github.alancavalcante_dev.desafio_picpay_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


/**
 * Serviço responsável por processar transações financeiras entre usuários.
 *
 * Principais responsabilidades:
 * - Valida se os dados da transação são válidos.
 * - Verifica se o pagador tem saldo suficiente e se não é um lojista (que não pode pagar).
 * - Atualiza os saldos do pagador e do recebedor.
 * - Consulta um serviço externo para autorizar a transação.
 * - Salva a transação no banco de dados.
 * - Envia notificações para ambas as partes (pagador e recebedor).
 *
 * ⚠️ Importante:
 * - A autorização é feita por um serviço externo, que pode falhar; isso precisa ser tratado.
 * - Lojistas não estão autorizados a realizar pagamentos.
 * - Transações com valor zero ou entre o mesmo usuário são bloqueadas.
 *
 * Tudo ocorre dentro de uma transação (@Transactional), garantindo consistência no banco.
 */


@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final AuthorizationTransaction authorizationTransaction;
    private final TransactionNotificationSender transactionNotificationSender;

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

        transactionNotificationSender.sender(payer, "Pagamento realizado com sucesso!", value);
        transactionNotificationSender.sender(payee, "Você recebeu um pagamento!", value);
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
