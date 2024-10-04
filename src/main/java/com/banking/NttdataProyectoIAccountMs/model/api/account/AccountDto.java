package com.banking.NttdataProyectoIAccountMs.model.api.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto {

    private Long id;
    private String numeroCuenta;
    private Double saldo;
    private String tipoCuenta;
    private Long clienteId;

    private String errorMessage;

}
