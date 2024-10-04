package com.banking.NttdataProyectoIAccountMs.model.api.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountListDto implements Serializable {
    private Long cuentaId;

    private String numeroCuenta;

    private Double saldo;

    private String tipoCuenta;

    // Información de las cuentas asociadas al cliente
    @Builder.Default
    private List<AccountClientDto> clientes = List.of();

    @JsonIgnore
    private Long totalRecords;
}
