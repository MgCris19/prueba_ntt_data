package com.codingtest.account.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.codingtest.account.mapper.CharToBooleanConverter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "cuentas")
@AllArgsConstructor
@NoArgsConstructor
@Builder
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