package com.codingtest.account.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Reports {

    private List<Movement> movements;
    @Data
    public static class Movement{

        private String fecha;

        private String cliente;

        private String numeroCuenta;

        private String tipoCuenta;

        private BigDecimal saldoInicial;

        private Boolean estado;

        private BigDecimal movimiento;

        private BigDecimal saldoDisponible;
    }
}
