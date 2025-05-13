package io.github.alancavalcante_dev.desafio_picpay_backend.repository;

import io.github.alancavalcante_dev.desafio_picpay_backend.domain.TransactionNotificationError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionNotificationErrorRepository extends JpaRepository<TransactionNotificationError, Long> {
}
