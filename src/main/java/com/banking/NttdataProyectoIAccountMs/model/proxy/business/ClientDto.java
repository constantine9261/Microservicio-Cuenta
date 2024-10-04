package com.banking.NttdataProyectoIAccountMs.model.proxy.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
}
