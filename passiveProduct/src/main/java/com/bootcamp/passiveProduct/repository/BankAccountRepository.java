package com.bootcamp.passiveProduct.repository;

import com.bootcamp.passiveProduct.domain.BankAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface BankAccountRepository extends ReactiveMongoRepository<BankAccount, String> {

    public Mono<BankAccount> findTop1ByOwnersAndAccountType(List<String> identity, String accountType);

    public Mono<BankAccount> findByAccountNumber(String accountNumber);

}
