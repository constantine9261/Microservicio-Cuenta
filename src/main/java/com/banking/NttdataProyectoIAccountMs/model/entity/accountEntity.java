package com.banking.NttdataProyectoIAccountMs.model.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(value = "cuentas")
public class AccountEntity implements Serializable {

    private static final long serialVersionUID = 7982069006616095393L;


    @Id
    @Column("id")
    private Long id;

    @Column("numero_cuenta")
    private String numeroCuenta;

    @Column("saldo")
    private Double saldo;

    @Column("tipo_cuenta")
    private String tipoCuenta;

    @Column("cliente_id")
    private Long clienteId;



}
