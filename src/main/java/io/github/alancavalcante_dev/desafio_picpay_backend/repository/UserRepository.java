package io.github.alancavalcante_dev.desafio_picpay_backend.repository;


import io.github.alancavalcante_dev.desafio_picpay_backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
