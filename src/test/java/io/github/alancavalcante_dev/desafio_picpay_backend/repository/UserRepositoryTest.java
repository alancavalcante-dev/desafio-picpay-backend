package io.github.alancavalcante_dev.desafio_picpay_backend.repository;


import io.github.alancavalcante_dev.desafio_picpay_backend.controller.dto.UserDTO;
import io.github.alancavalcante_dev.desafio_picpay_backend.domain.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Deve trazer o usuário cadastrado")
    void findUserByCpfCnpjFound() {
        UserDTO data = new UserDTO("Alan", "Pereira Cavalcante", "12345678901","alan.cavalcante.dev@gmail.com",
                "SenhaPotente123$", true, new BigDecimal(10));

        this.registerUser(data);
        Optional<User> result = this.userRepository.findUserByCpfCnpj(data.cpfCnpj());
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Não deve trazer o usuário")
    void findUserByCpfCnpjNotFound() {
        Optional<User> result = this.userRepository.findUserByCpfCnpj("12345678901");
        assertThat(result.isEmpty()).isTrue();
    }

    private void registerUser(UserDTO data){
        this.entityManager.persist(User.dtoToEntity(data));
    }
}