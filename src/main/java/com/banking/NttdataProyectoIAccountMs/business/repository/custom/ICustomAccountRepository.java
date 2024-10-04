package com.banking.NttdataProyectoIAccountMs.business.repository.custom;

import com.banking.NttdataProyectoIAccountMs.model.api.account.ListAccountRequest;
import com.banking.NttdataProyectoIAccountMs.model.projection.AccountProjection;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import  com.banking.nttdataProyectoI.model.projection.AccountDetailProjection;
public interface ICustomAccountRepository {

    Flux<AccountProjection> listAccountsPaginated(ListAccountRequest request);

    Mono<AccountDetailProjection> findAccountDetailById(Long accountId);
}
