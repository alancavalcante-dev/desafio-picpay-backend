package io.github.alancavalcante_dev.desafio_picpay_backend.repository;

import io.github.alancavalcante_dev.desafio_picpay_backend.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
