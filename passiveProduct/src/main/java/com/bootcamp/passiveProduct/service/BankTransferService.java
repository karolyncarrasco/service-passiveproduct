package com.bootcamp.passiveProduct.service;

import com.bootcamp.passiveProduct.common.ErrorMessage;
import com.bootcamp.passiveProduct.common.FunctionalException;
import com.bootcamp.passiveProduct.domain.AccountMovement;
import com.bootcamp.passiveProduct.domain.BankTransfer;
import com.bootcamp.passiveProduct.repository.AccountMovementRepository;
import com.bootcamp.passiveProduct.repository.BankAccountRepository;
import com.bootcamp.passiveProduct.repository.BankTransferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.bootcamp.passiveProduct.common.ErrorMessage.LIMIT_ACCOUNT;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BankTransferService {

    @Autowired
    private BankTransferRepository bankTransferRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private AccountMovementService accountMovementService;

    public Flux<BankTransfer> findAll() {
        log.debug("findAll executed");
        return bankTransferRepository.findAll();
    }

    public Mono<BankTransfer> create(BankTransfer bankTransfer) {
        return bankAccountRepository.findByAccountNumber(bankTransfer.getSourceAccount())
                .flatMap( source -> {
                        return bankAccountRepository.findByAccountNumber(bankTransfer.getTargetAccount())
                                .flatMap(target -> {
                                    //source
                                    AccountMovement m = new AccountMovement();
                                    m.setAccountId(source.getId());
                                    m.setAmount(bankTransfer.getAmount());
                                    m.setSummary("Transfer to other account");
                                    m.setMovementDate(bankTransfer.getTransferDate());
                                    m.setRecipientAccount(bankTransfer.getTargetAccount());
                                    m.setType("D");
                                    m.setComission(0D);
                                    return accountMovementService.create(m)
                                            .flatMap(movement -> {
                                                    //target
                                                    AccountMovement m2 = new AccountMovement();
                                                    m2.setAccountId(target.getId());
                                                    m2.setAmount(bankTransfer.getAmount());
                                                    m2.setSummary("Transfer from other account");
                                                    m2.setMovementDate(bankTransfer.getTransferDate());
                                                    m2.setType("A");
                                                    m2.setComission(0D);
                                                    return accountMovementService.create(m2)
                                                            .flatMap(am -> bankTransferRepository.save(bankTransfer))
                                                            .doOnError(error -> {
                                                                throw new RuntimeException(error.getMessage());
                                                            });
                                            })
                                            .doOnError(error -> {
                                                throw new RuntimeException(error.getMessage());
                                            });
                                })
                                .switchIfEmpty(Mono.error(new FunctionalException("Target: " + ErrorMessage.ACCOUNT_NOT_FOUND.getValue())));
                    }
                )
                .switchIfEmpty(Mono.error(new FunctionalException("Source: " + ErrorMessage.ACCOUNT_NOT_FOUND.getValue())));
    }
}
