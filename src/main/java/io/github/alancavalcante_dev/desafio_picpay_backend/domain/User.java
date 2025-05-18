package io.github.alancavalcante_dev.desafio_picpay_backend.domain;


import io.github.alancavalcante_dev.desafio_picpay_backend.controller.dto.UserDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;


@Entity
@Data
@Table(name = "tbl_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, length = 14, nullable = false)
    private String cpfCnpj;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isShopkeeper;

    private BigDecimal balance;

    public User() {}

    public User(long id, String firstName, String lastName, String cpfCnpj, String email, String password, boolean isShopkeeper, BigDecimal bigDecimal) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.password = password;
        this.isShopkeeper = isShopkeeper;
        this.balance = bigDecimal;
    }

    public static User dtoToEntity(UserDTO dto) {
        User newUser = new User();

        newUser.setFirstName(dto.firstName());
        newUser.setLastName(dto.lastName());
        newUser.setCpfCnpj(dto.cpfCnpj());
        newUser.setEmail(dto.email());
        newUser.setPassword(dto.password());
        newUser.setShopkeeper(dto.isShopkeeper());
        newUser.setBalance(dto.balance());

        return newUser;
    }
}
