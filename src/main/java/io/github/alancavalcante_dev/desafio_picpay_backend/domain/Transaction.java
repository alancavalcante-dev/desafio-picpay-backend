package io.github.alancavalcante_dev.desafio_picpay_backend.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Data
@Table(name = "tbl_transactions")
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private BigDecimal value;

    private Long payer;
    private Long payee;

    @CreatedDate
    private LocalDateTime dateTime;


    public Transaction(Long payer, Long payee, BigDecimal value) {
        this.payer = payer;
        this.payee = payee;
        this.value = value;
    }

}
