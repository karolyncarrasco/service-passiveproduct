package com.bootcamp.passiveProduct.service;


import com.bootcamp.passiveProduct.common.ErrorMessage;
import com.bootcamp.passiveProduct.common.FunctionalException;
import com.bootcamp.passiveProduct.domain.AccountMovement;
import com.bootcamp.passiveProduct.repository.AccountMovementRepository;
import com.bootcamp.passiveProduct.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.stream.Collectors;

import static com.bootcamp.passiveProduct.common.ErrorMessage.LIMIT_ACCOUNT;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountMovementService {
    @Autowired
    private AccountMovementRepository accountMovementRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;
    /*
    private final WebClient webClient = WebClient.builder().baseUrl("http://localhost:8081/v1/client").defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

    public Mono<Client> getClientByIdentityNumber(String identityNumber) {
        return this.webClient.get().uri("/findByIdentityNumber/{identityNumber}", identityNumber)
                .retrieve().bodyToMono(Client.class);
    }
    */
    public Flux<AccountMovement> findAll(){
        log.debug("findAll executed");
        return accountMovementRepository.findAll();
    }

    public Mono<AccountMovement> findById(String id){
        log.debug("findById executed {}", id);
        return accountMovementRepository.findById(id);
    }

    public Mono<AccountMovement> create(AccountMovement accountMovement){
        if(accountMovement.getType().toUpperCase().equals("D"))
            accountMovement.setAmount(accountMovement.getAmount()*-1);
        return bankAccountRepository.findById(accountMovement.getAccountId())
                .flatMap(x-> {
                    LocalDate _start = LocalDate.of(accountMovement.getMovementDate().getYear(), accountMovement.getMovementDate().getMonthValue(), 1);
                    LocalDate _end = LocalDate.of(accountMovement.getMovementDate().getYear(), accountMovement.getMovementDate().getMonthValue(), accountMovement.getMovementDate().lengthOfMonth());
                            return accountMovementRepository.countByAccountIdAndMovementDateBetween(x.getId(), _start, _end)
                                    .flatMap(m -> {
                                        double comission = 0;
                                        if(m>=x.getMaxTransaction())
                                            comission = x.getComissionTransaction();
                                        accountMovement.setComission(comission);
                                        double newAmount = (x.getBalance() + accountMovement.getAmount() - comission);
                                        x.setBalance(newAmount);
                                        if (accountMovement.getType().toUpperCase().equals("D")) {
                                            if (newAmount >= 0)
                                                return bankAccountRepository.save(x)
                                                        .then(accountMovementRepository.save(accountMovement));
                                            else
                                                return Mono.error(new FunctionalException(LIMIT_ACCOUNT.getValue()));
                                        }
                                        else {
                                            if ( newAmount >= 0D)
                                                return bankAccountRepository.save(x)
                                                    .then(accountMovementRepository.save(accountMovement));
                                            else return Mono.error(new FunctionalException(LIMIT_ACCOUNT.getValue()));
                                        }
                                    });

                        }
                )
                .switchIfEmpty(Mono.error(new FunctionalException(ErrorMessage.ACCOUNT_NOT_FOUND.getValue())));
    }
}
