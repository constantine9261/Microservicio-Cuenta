package com.banking.NttdataProyectoIAccountMs.model.api.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAccountRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotNull(message = "El ID del cliente es obligatorio")
  private Long clienteId; // ID del cliente para asociar la cuenta

  @NotNull(message = "El tipo de cuenta es obligatorio")
  private String tipoCuenta; // 'AHORROS' o 'CORRIENTE'


}
