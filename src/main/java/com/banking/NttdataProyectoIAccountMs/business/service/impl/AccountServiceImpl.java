package com.banking.NttdataProyectoIAccountMs.business.service.impl;

import com.banking.NttdataProyectoIAccountMs.business.repository.IAccountRepository;
import com.banking.NttdataProyectoIAccountMs.business.service.IAccountService;
import com.banking.NttdataProyectoIAccountMs.model.api.account.*;
import com.banking.NttdataProyectoIAccountMs.model.entity.AccountEntity;
import com.banking.NttdataProyectoIAccountMs.model.projection.AccountProjection;
import com.banking.NttdataProyectoIAccountMs.model.proxy.business.ClientDto;
import com.banking.NttdataProyectoIAccountMs.util.CustomException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private final IAccountRepository bankAccountRepository;

    private final WebClient webClient;


        @Autowired
        public AccountServiceImpl(IAccountRepository bankAccountRepository) {
            this.bankAccountRepository = bankAccountRepository;
            this.webClient = WebClient.builder()
                    .baseUrl("http://localhost:8080")
                    .build();
        }

        @Override
        public Mono<AccountDto> createAcountbyClient(CreateAccountRequest request) {
            return webClient.get()
                    .uri("/clientes/{clienteId}", request.getClienteId())
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() && status.value() == 404,
                            clientResponse -> Mono.error(new RuntimeException("Cliente con ID " + request.getClienteId() + " no encontrado"))
                    )
                    .bodyToMono(ClientDto.class)
                    .doOnNext(clientDto -> System.out.println("Cliente encontrado: " + clientDto))
                    .doOnError(error -> System.err.println("Error llamando al servicio de cliente: " + error.getMessage()))
                    .flatMap(clientDto -> {
                        AccountEntity bankAccount = AccountEntity.builder()
                                .numeroCuenta(generateAccountNumber())
                                .saldo(0.0)
                                .tipoCuenta(request.getTipoCuenta())
                                .clienteId(request.getClienteId())
                                .build();

                        return bankAccountRepository.save(bankAccount)
                                .map(savedAccount -> AccountDto.builder()  // Conversión directa aquí
                                        .id(savedAccount.getId())
                                        .numeroCuenta(savedAccount.getNumeroCuenta())
                                        .saldo(savedAccount.getSaldo())
                                        .tipoCuenta(savedAccount.getTipoCuenta())
                                        .clienteId(savedAccount.getClienteId())
                                        .build());
                    })
                    .onErrorResume(error -> {
                        if (error instanceof RuntimeException && error.getMessage().contains("Cliente con ID")) {
                            return Mono.error(new RuntimeException("Cliente no encontrado: " + request.getClienteId()));
                        }
                        return Mono.error(new RuntimeException("Error inesperado: " + error.getMessage()));
                    });
        }

    @Override
    public Mono<AccountDto> depositoryByClient(Long cuentaId, Double monto) {
        return bankAccountRepository.findById(cuentaId)
                .flatMap(account -> {
                    account.setSaldo(account.getSaldo() + monto);
                    return bankAccountRepository.save(account);
                })
                .map(savedAccount -> AccountDto.builder()
                        .id(savedAccount.getId())
                        .numeroCuenta(savedAccount.getNumeroCuenta())
                        .saldo(savedAccount.getSaldo())
                        .tipoCuenta(savedAccount.getTipoCuenta())
                        .clienteId(savedAccount.getClienteId())
                        .build())
                .switchIfEmpty(Mono.error(new RuntimeException("Cuenta no encontrada con ID: " + cuentaId)));
    }

    @Override
    public Mono<AccountDto> withdrawByClient(Long cuentaId, Double monto) {
        return bankAccountRepository.findById(cuentaId)
                .flatMap(account -> validateWithdrawal(account, monto)
                        .flatMap(validatedAccount -> performWithdrawal(validatedAccount, monto))
                        .map(accountDto -> {
                            accountDto.setErrorMessage(null);
                            return accountDto;
                        })
                )
                .switchIfEmpty(Mono.just(AccountDto.builder()
                        .errorMessage("Cuenta no encontrada con ID: " + cuentaId)
                        .build()))
                .onErrorResume(CustomException.class, ex -> {
                    return Mono.just(AccountDto.builder()
                            .errorMessage(ex.getMessage())
                            .build());
                })
                .onErrorResume(ex -> {
                    return Mono.just(AccountDto.builder()
                            .errorMessage("Ocurrió un error inesperado")
                            .build());
                });
    }

    @Override
    public Mono<AccountDto> deleteAcount(Long cuentaId) {
        return bankAccountRepository.findById(cuentaId)
                .flatMap(accountEntity -> bankAccountRepository.delete(accountEntity)
                        .then(Mono.just(AccountDto.builder()
                                .id(accountEntity.getId())
                                .numeroCuenta(accountEntity.getNumeroCuenta())
                                .saldo(accountEntity.getSaldo())
                                .tipoCuenta(accountEntity.getTipoCuenta())
                                .build()))
                )
                .onErrorResume(e -> Mono.error(new RuntimeException("Error al eliminar la cuenta: " + e.getMessage())));
    }

    private Mono<AccountEntity> validateWithdrawal(AccountEntity account, Double monto) {
        if ("AHORROS".equals(account.getTipoCuenta()) && account.getSaldo() - monto < 0) {
            return Mono.error(new CustomException("Saldo insuficiente para cuentas de ahorro", 400));
        }
        else if ("CORRIENTE".equals(account.getTipoCuenta()) && account.getSaldo() - monto < -500) {
            return Mono.error(new CustomException("El sobregiro máximo permitido para cuentas corrientes es de -500", 400));
        }
        return Mono.just(account);
    }

    private Mono<AccountDto> performWithdrawal(AccountEntity account, Double monto) {
        account.setSaldo(account.getSaldo() - monto);
        return bankAccountRepository.save(account)
                .map(savedAccount -> AccountDto.builder()
                        .id(savedAccount.getId())
                        .numeroCuenta(savedAccount.getNumeroCuenta())
                        .saldo(savedAccount.getSaldo())
                        .tipoCuenta(savedAccount.getTipoCuenta())
                        .clienteId(savedAccount.getClienteId())
                        .build());
    }

    private String generateAccountNumber() {
        return "00" + String.format("%08d", (int) (Math.random() * 100000000));
    }


    @Override
    public Mono<ListAccountResponse> listAccountPaginated(ListAccountRequest request) {
        return bankAccountRepository.listAccountsPaginated(request)
                .collectList()
                .map(accountProjections -> {
                    Map<Long, List<AccountProjection>> groupedByAccountId = accountProjections.stream()
                            .collect(Collectors.groupingBy(AccountProjection::getCuentaId));

                    List<AccountListDto> accountListDtos = groupedByAccountId.values().stream()
                            .map(accountGroup -> {
                                AccountProjection accountData = accountGroup.get(0);

                                return AccountListDto.builder()
                                        .cuentaId(accountData.getCuentaId())
                                        .numeroCuenta(accountData.getNumeroCuenta())
                                        .saldo(accountData.getSaldo())
                                        .tipoCuenta(accountData.getTipoCuenta())
                                        .clientes(accountGroup.stream()
                                                .map(accountProjection -> AccountClientDto.builder()
                                                        .clienteId(accountProjection.getClienteId())
                                                        .nombre(accountProjection.getNombre())
                                                        .apellido(accountProjection.getApellido())
                                                        .dni(accountProjection.getDni())
                                                        .email(accountProjection.getEmail())
                                                        .build())
                                                .collect(Collectors.toList()))
                                        .build();
                            })
                            .collect(Collectors.toList());

                    return ListAccountResponse.builder()
                            .totalRecords((long) accountProjections.size())
                            .accounts(accountListDtos)
                            .build();
                });
    }

    @Override
    public Mono<AccountDetailDto> getAccountDetailById(Long cuentaId) {
        return bankAccountRepository.findAccountDetailById(cuentaId)
                .map(accountDetailProjection -> {
                    AccountClientDto cliente = AccountClientDto.builder()
                            .clienteId(accountDetailProjection.getClientes().get(0).getClienteId())
                            .nombre(accountDetailProjection.getClientes().get(0).getNombre())
                            .apellido(accountDetailProjection.getClientes().get(0).getApellido())
                            .dni(accountDetailProjection.getClientes().get(0).getDni())
                            .email(accountDetailProjection.getClientes().get(0).getEmail())
                            .build();

                    return AccountDetailDto.builder()
                            .cuentaId(accountDetailProjection.getCuentaId())
                            .numeroCuenta(accountDetailProjection.getNumeroCuenta())
                            .saldo(accountDetailProjection.getSaldo())
                            .tipoCuenta(accountDetailProjection.getTipoCuenta())
                            .clientes(Collections.singletonList(cliente))
                            .build();
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Cuenta no encontrada con ID: " + cuentaId)));
    }

    public Mono<AccountDto> actualizarSaldo(Integer id, BigDecimal nuevoSaldo) {
        return bankAccountRepository.findById(Long.valueOf(id))
                .flatMap(cuenta -> {
                    cuenta.setSaldo(nuevoSaldo.doubleValue()); // Actualiza el saldo
                    return bankAccountRepository.save(cuenta); // Guarda la cuenta actualizada
                })
                .map(this::convertirADto); // Convierte la entidad a DTO
    }

    private AccountDto convertirADto(AccountEntity accountEntity) {
        return AccountDto.builder()
                .id(accountEntity.getId()) // Mapea el ID de la entidad al DTO
                .numeroCuenta(accountEntity.getNumeroCuenta()) // Mapea el número de cuenta
                .saldo(accountEntity.getSaldo()) // Mapea el saldo actual
                .tipoCuenta(accountEntity.getTipoCuenta()) // Mapea el tipo de cuenta
                .clienteId(accountEntity.getClienteId()) // Mapea el ID del cliente asociado
                .build(); // Crea y devuelve el DTO
    }

}
