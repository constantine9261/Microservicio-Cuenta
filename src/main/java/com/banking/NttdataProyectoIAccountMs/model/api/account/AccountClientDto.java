package com.banking.NttdataProyectoIAccountMs.model.api.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountClientDto implements Serializable {

    private Long clienteId;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;

}

