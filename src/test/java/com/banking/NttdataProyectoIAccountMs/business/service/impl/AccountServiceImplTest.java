package com.banking.NttdataProyectoIAccountMs.business.service.impl;

import com.banking.NttdataProyectoIAccountMs.business.repository.IAccountRepository;
import com.banking.NttdataProyectoIAccountMs.business.repository.custom.ICustomAccountRepository;
import com.banking.NttdataProyectoIAccountMs.business.service.IAccountService;
import com.banking.NttdataProyectoIAccountMs.model.api.account.*;
import com.banking.NttdataProyectoIAccountMs.model.entity.AccountEntity;
import com.banking.NttdataProyectoIAccountMs.model.projection.AccountProjection;
import com.banking.nttdataProyectoI.model.projection.AccountDetailProjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;



class AccountServiceImplTest {


    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private IAccountRepository accountRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void listAccountPaginated() {
        ListAccountRequest request = new ListAccountRequest();
        when(accountRepository.listAccountsPaginated(request)).thenReturn(Flux.just(new AccountProjection())); // Mock response

        Mono<ListAccountResponse> result = accountService.listAccountPaginated(request);

        verify(accountRepository, times(1)).listAccountsPaginated(request);
    }

    @Test
    void getAccountDetailById() {
        Long accountId = 1L; // Example account ID
        when(accountRepository.findAccountDetailById(accountId)).thenReturn(Mono.just(new AccountDetailProjection())); // Mock response

        Mono<AccountDetailDto> result = accountService.getAccountDetailById(accountId);

        verify(accountRepository, times(1)).findAccountDetailById(accountId);
    }
}