package io.github.alancavalcante_dev.desafio_picpay_backend.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_transaction_notification_errors")
@Data
@EntityListeners(AuditingEntityListener.class)
public class TransactionNotificationError {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @CreatedDate
    private LocalDateTime dateTime;

    public TransactionNotificationError(User user) {
        this.user = user;
    }
}
