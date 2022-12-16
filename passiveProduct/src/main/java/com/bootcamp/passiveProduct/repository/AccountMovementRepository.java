package com.bootcamp.passiveProduct.repository;

import com.bootcamp.passiveProduct.domain.AccountMovement;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AccountMovementRepository extends ReactiveMongoRepository<AccountMovement, String> {

    public Flux<AccountMovement> findByAccountId(String accountId);
}
