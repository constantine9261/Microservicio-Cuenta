package com.banking.nttdataProyectoI.model.projection;

import com.banking.NttdataProyectoIAccountMs.model.api.account.AccountClientDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailProjection {

    private Long cuentaId; // ID de la cuenta
    private String numeroCuenta; // Número de cuenta
    private Double saldo; // Saldo de la cuenta
    private String tipoCuenta; // Tipo de cuenta


    private List<AccountClientDto> clientes;



}
