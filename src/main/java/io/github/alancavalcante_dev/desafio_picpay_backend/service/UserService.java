package io.github.alancavalcante_dev.desafio_picpay_backend.service;


import io.github.alancavalcante_dev.desafio_picpay_backend.domain.User;
import io.github.alancavalcante_dev.desafio_picpay_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository repository;


    public Optional<User> findUserById(Long id) {
        return repository.findById(id);
    }

    public void saveUser(User user) {
        repository.save(user);
    }


}
