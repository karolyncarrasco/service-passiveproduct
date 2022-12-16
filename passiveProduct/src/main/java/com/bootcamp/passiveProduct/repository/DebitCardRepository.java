package com.bootcamp.passiveProduct.repository;

import com.bootcamp.passiveProduct.domain.DebitCard;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebitCardRepository extends ReactiveMongoRepository<DebitCard, String> {
}
