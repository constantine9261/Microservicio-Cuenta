package com.banking.NttdataProyectoIAccountMs.business.repository;


import com.banking.NttdataProyectoIAccountMs.business.repository.custom.ICustomAccountRepository;
import com.banking.NttdataProyectoIAccountMs.model.entity.AccountEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IAccountRepository extends
        ReactiveCrudRepository<AccountEntity, Long>, ICustomAccountRepository {
}
