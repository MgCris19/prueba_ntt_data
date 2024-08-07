package com.codingtest.account.model;

import com.codingtest.account.converter.CharToBooleanConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "cuentas")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Client client;

    @Column(name = "numero_cuenta")
    private String accountNumber;

    @Column(name = "tipo_cuenta")
    private String accountType;

    @Column(name = "saldo_inicial")
    private BigDecimal initialBalance;

    @Column(name = "saldo_disponible")
    private BigDecimal availableBalance;

    @Convert(converter = CharToBooleanConverter.class)
    @Column(name = "estado")
    private Boolean status;
}
