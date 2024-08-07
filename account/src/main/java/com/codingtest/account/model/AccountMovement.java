package com.codingtest.account.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "movimientos")
public class AccountMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cuenta")
    private Account account;

    @Column(name = "fecha")
    private Timestamp created;

    @Column(name = "tipo_movimiento")
    private String transactionType;

    @Column(name = "valor")
    private BigDecimal value;

    @Column(name = "saldo")
    private BigDecimal balance;

}
