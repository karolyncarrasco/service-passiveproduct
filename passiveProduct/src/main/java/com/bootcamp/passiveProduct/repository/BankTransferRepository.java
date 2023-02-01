package com.bootcamp.passiveProduct.repository;

import com.bootcamp.passiveProduct.domain.BankTransfer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BankTransferRepository extends ReactiveMongoRepository<BankTransfer, String> {
}
