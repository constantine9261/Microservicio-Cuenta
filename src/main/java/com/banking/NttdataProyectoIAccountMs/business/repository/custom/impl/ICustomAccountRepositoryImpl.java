package com.banking.NttdataProyectoIAccountMs.business.repository.custom.impl;

import com.banking.NttdataProyectoIAccountMs.business.repository.custom.ICustomAccountRepository;
import com.banking.NttdataProyectoIAccountMs.model.api.account.AccountClientDto;
import com.banking.NttdataProyectoIAccountMs.model.api.account.ListAccountRequest;
import com.banking.NttdataProyectoIAccountMs.model.projection.AccountProjection;
import com.banking.NttdataProyectoIAccountMs.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import  com.banking.nttdataProyectoI.model.projection.AccountDetailProjection;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;


public  class ICustomAccountRepositoryImpl implements ICustomAccountRepository {

    @Autowired
    private DatabaseClient client;

    @Override
    public Flux<AccountProjection> listAccountsPaginated(ListAccountRequest request) {
        String query = "SELECT " +
                "    COUNT(*) OVER() AS totalRecords, " +
                "    A.id AS cuenta_id, " +
                "    A.numero_cuenta, " +
                "    A.saldo, " +
                "    A.tipo_cuenta, " +
                "    C.id AS cliente_id, " +
                "    C.nombre AS nombre_cliente, " +
                "    C.apellido AS apellido_cliente, " +
                "    C.dni AS dni_cliente, " +
                "    C.email AS email_cliente " +
                "FROM " +
                "    cuentas A " +
                "LEFT JOIN " +
                "    clientes C ON C.id = A.cliente_id " +
                "ORDER BY " +
                "    A.id " +
                "OFFSET :offset LIMIT :pageSize";

        return client.sql(query)
                .bind("offset", Util.avoidNulls(request.getOffset()))
                .bind("pageSize", Util.avoidNulls(request.getPageSize()))
                .map((row, metadata) -> {
                    AccountProjection projection = new AccountProjection();
                    projection.buildAccountProjection(row); // Debes implementar este método
                    return projection;
                })
                .all();
    }

    @Override
    public Mono<AccountDetailProjection> findAccountDetailById(Long accountId) {
        String query = "SELECT " +
                "    cu.id AS cuenta_id, " +
                "    cu.numero_cuenta, " +
                "    cu.saldo, " +
                "    cu.tipo_cuenta, " +
                "    c.id AS cliente_id, " +
                "    c.nombre AS nombre_cliente, " +
                "    c.apellido AS apellido_cliente, " +
                "    c.dni AS dni_cliente, " +
                "    c.email AS email_cliente " +
                "FROM " +
                "    cuentas cu " +
                "LEFT JOIN " +
                "    clientes c ON c.id = cu.cliente_id " +
                "WHERE " +
                "    cu.id = :accountId";

        return client.sql(query)
                .bind("accountId", accountId)
                .map((row, metadata) -> {
                    Long clienteId = row.get("cliente_id", Long.class);
                    String nombre = row.get("nombre_cliente", String.class);
                    String apellido = row.get("apellido_cliente", String.class);
                    String dni = row.get("dni_cliente", String.class);
                    String email = row.get("email_cliente", String.class);

                    AccountClientDto cliente = (clienteId != null) ? AccountClientDto.builder()
                            .clienteId(clienteId)
                            .nombre(nombre)
                            .apellido(apellido)
                            .dni(dni)
                            .email(email)
                            .build() : null;

                    return AccountDetailProjection.builder()
                            .cuentaId(row.get("cuenta_id", Long.class))
                            .numeroCuenta(row.get("numero_cuenta", String.class))
                            .saldo(row.get("saldo", Double.class))
                            .tipoCuenta(row.get("tipo_cuenta", String.class))
                            .clientes(cliente != null ? List.of(cliente) : Collections.emptyList())
                            .build();
                })
                .one()
                .switchIfEmpty(Mono.error(new RuntimeException("Cuenta no encontrada con ID: " + accountId)));
    }



}
