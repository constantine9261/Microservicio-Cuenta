package com.banking.NttdataProyectoIAccountMs.model.projection;

import io.r2dbc.spi.Readable;
import io.r2dbc.spi.Row;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@lombok.Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountProjection implements Serializable {

    private Long clienteId;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private Long cuentaId;
    private String numeroCuenta;
    private Double saldo;
    private String tipoCuenta;


    public void buildClientProjection(Readable row) {
        this.clienteId = row.get("cliente_id", Long.class);
        this.nombre = row.get("nombre", String.class);
        this.apellido = row.get("apellido", String.class);
        this.dni = row.get("dni", String.class);
        this.email = row.get("email", String.class);
        this.cuentaId = row.get("cuenta_id", Long.class);
        this.numeroCuenta = row.get("numero_cuenta", String.class);
        this.saldo = row.get("saldo", Double.class);
        this.tipoCuenta = row.get("tipo_cuenta", String.class);
    }


    public void buildAccountProjection(Row row) {
        this.cuentaId = row.get("cuenta_id", Long.class);
        this.numeroCuenta = row.get("numero_cuenta", String.class);
        this.saldo = row.get("saldo", Double.class);
        this.tipoCuenta = row.get("tipo_cuenta", String.class);
        this.clienteId = row.get("cliente_id", Long.class);
        this.nombre = row.get("nombre_cliente", String.class);
        this.apellido = row.get("apellido_cliente", String.class);
        this.dni = row.get("dni_cliente", String.class);
        this.email = row.get("email_cliente", String.class);
    }


}
