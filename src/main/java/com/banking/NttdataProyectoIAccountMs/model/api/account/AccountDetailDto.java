package com.banking.NttdataProyectoIAccountMs.model.api.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailDto {

    private Long cuentaId; // ID de la cuenta
    private String numeroCuenta; // Número de cuenta
    private Double saldo; // Saldo de la cuenta
    private String tipoCuenta; // Tipo de cuenta
    private List<AccountClientDto> clientes;
}
