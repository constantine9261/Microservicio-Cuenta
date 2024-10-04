package com.banking.NttdataProyectoIAccountMs.business.repository;


import com.banking.NttdataProyectoIAccountMs.business.repository.custom.ICustomAccountRepository;
import com.banking.NttdataProyectoIAccountMs.model.entity.accountEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IAccountRepository extends
        ReactiveCrudRepository<accountEntity, Long>, ICustomAccountRepository {
}
