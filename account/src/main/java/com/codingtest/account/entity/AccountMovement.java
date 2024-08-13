package com.codingtest.account.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "movimientos")
@AllArgsConstructor
@NoArgsConstructor
@Builder
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