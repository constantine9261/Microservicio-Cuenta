package com.banking.NttdataProyectoIAccountMs.business.service;


import com.banking.NttdataProyectoIAccountMs.model.api.account.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface IAccountService {

    Mono<ListAccountResponse> listAccountPaginated(
            ListAccountRequest request);

    Mono<AccountDetailDto> getAccountDetailById(Long accountId);

    Mono<AccountDto> createAcountbyClient(CreateAccountRequest request);

    Mono<AccountDto> depositoryByClient(Long cuentaId , Double monto);

    Mono<AccountDto> withdrawByClient(Long cuentaId , Double monto);

   Mono<AccountDto> deleteAcount(Long clientId);

    Mono<AccountDto> actualizarSaldo(Integer id, BigDecimal nuevoSaldo);

}