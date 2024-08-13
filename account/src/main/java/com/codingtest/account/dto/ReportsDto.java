package com.codingtest.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReportsDto {

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
