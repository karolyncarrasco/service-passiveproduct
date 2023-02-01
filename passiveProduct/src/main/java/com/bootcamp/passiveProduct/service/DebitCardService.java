package com.bootcamp.passiveProduct.service;

import com.bootcamp.passiveProduct.common.ErrorMessage;
import com.bootcamp.passiveProduct.common.FunctionalException;
import com.bootcamp.passiveProduct.domain.AccountMovement;
import com.bootcamp.passiveProduct.domain.BankAccount;
import com.bootcamp.passiveProduct.domain.DebitCard;
import com.bootcamp.passiveProduct.repository.BankAccountRepository;
import com.bootcamp.passiveProduct.repository.DebitCardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DebitCardService {
    @Autowired
    private DebitCardRepository debitCardRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    public Flux<DebitCard> findAll() {
        log.debug("findAll executed");
        return debitCardRepository.findAll();
    }

    public Mono<DebitCard> create(DebitCard debitCard) {
        return bankAccountRepository.findByAccountNumber(debitCard.getMainAccount())
                .flatMap(a -> {
                    if (a.getAccountType().toUpperCase().equals("AHO") || a.getAccountType().toUpperCase().equals("CTE")){
                        return debitCardRepository.save(debitCard);
                    }
                    else
                        return Mono.error(new FunctionalException(ErrorMessage.DEBIT_CARD_RESTRICTION.getValue()));
                })
                .switchIfEmpty(Mono.error(new FunctionalException(ErrorMessage.ACCOUNT_NOT_FOUND.getValue())));
    }

    public Mono<DebitCard> add(String debitCardId, String account) {
        return bankAccountRepository.findByAccountNumber(account)
                .flatMap(a -> {
                    if (a.getAccountType().toUpperCase().equals("AHO") || a.getAccountType().toUpperCase().equals("CTE")){
                        return debitCardRepository.findById(debitCardId)
                                .flatMap(x -> {
                                    ArrayList<String> aux = x.getAccounts();
                                    if(x.getAccounts().stream().filter(m -> m.equals(account)).count() == 0)
                                    {
                                        aux.add(account);
                                        x.setAccounts(aux);
                                        return debitCardRepository.save(x);
                                    }
                                    else
                                        return Mono.error(new FunctionalException(ErrorMessage.DEBIT_CARD_ACCOUNT.getValue()));
                                })
                                .switchIfEmpty(Mono.error(new FunctionalException(ErrorMessage.DEBIT_CARD_NOT_FOUND.getValue())));
                    }
                    else
                        return Mono.error(new FunctionalException(ErrorMessage.DEBIT_CARD_RESTRICTION.getValue()));
                })
                .switchIfEmpty(Mono.error(new FunctionalException(ErrorMessage.ACCOUNT_NOT_FOUND.getValue())));
    }

    /*public Mono<AccountMovement> takeOut(AccountMovement accountMovement){
        debitCardRepository.findById(accountMovement.getDebitCardId())
                .flatMap(dc -> {
                    ArrayList<String> list = new ArrayList<>();
                    list.add(dc.getMainAccount());
                    list.addAll(dc.getAccounts());
                    AccountMovement ac = accountMovement;
                    double resto = 0;
                    int i=0;
                    while (accountMovement.getAmount()>resto){
                        bankAccountRepository.findByAccountNumber(list.get(i));
                    }
                })
                .switchIfEmpty(Mono.error(new FunctionalException(ErrorMessage.DEBIT_CARD_NOT_FOUND.getValue())));
    }*/
}
