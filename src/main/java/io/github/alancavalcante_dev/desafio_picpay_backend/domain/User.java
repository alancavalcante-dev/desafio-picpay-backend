package io.github.alancavalcante_dev.desafio_picpay_backend.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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


}
