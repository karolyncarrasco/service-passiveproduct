package com.bootcamp.passiveProduct.service;

import com.bootcamp.passiveProduct.common.ErrorMessage;
import com.bootcamp.passiveProduct.common.FunctionalException;
import com.bootcamp.passiveProduct.domain.BankAccount;
import com.bootcamp.passiveProduct.domain.Client;
import com.bootcamp.passiveProduct.domain.CreditCard;
import com.bootcamp.passiveProduct.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.ReactiveTransaction;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    private final WebClient webClient = WebClient.builder().baseUrl("http://localhost:8081/v1/client").defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

    private final WebClient activeService = WebClient.builder().baseUrl("http://localhost:8082/v1/creditcard").defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

    public Mono<Client> getClientByIdentityNumber(String identityNumber) {
        return this.webClient.get().uri("/findByIdentityNumber/{identityNumber}", identityNumber)
                .retrieve().bodyToMono(Client.class);
    }

    public Mono<CreditCard> getCreditCardByClient(String identityNumber) {
        return this.activeService.get().uri("/findByClient/{identityNumber}", identityNumber)
                .retrieve().bodyToMono(CreditCard.class)
                .onErrorResume(WebClientResponseException.class,
                        ex -> ex.getRawStatusCode() == 404 ? Mono.empty() : Mono.error(ex));
    }

    public Flux<BankAccount> findAll(){
        log.debug("findAll executed");
        return bankAccountRepository.findAll();
    }

    public Mono<BankAccount> findById(String id){
        log.debug("findById executed {}", id);
        return bankAccountRepository.findById(id);
    }

    public Mono<Object> create(BankAccount bankAccount){
        log.debug("create executed {}", bankAccount);
        if(Double.isNaN(bankAccount.getOpenAmmount()))
            return Mono.error(new FunctionalException(ErrorMessage.OPENAMOUNT_RESTRICTION.getValue()));
        bankAccount.setBalance(bankAccount.getOpenAmmount());
        if (bankAccount.getAccountType().toUpperCase().equals("AHO")){
            bankAccount.setMaintenanceCommision(0d);
            return getClientByIdentityNumber(bankAccount.getOwners().get(0))
                    .flatMap(x-> {
                                if (x.getClientType().getDescription().toUpperCase().equals("PERSONAL")) {
                                    return bankAccountRepository.findTop1ByOwnersAndAccountType(bankAccount.getOwners(), "AHO")
                                            .flatMap(y -> Mono.error(new FunctionalException(ErrorMessage.PERSONAL_AHORRO_RESTRICTION.getValue())))
                                            .switchIfEmpty(Mono.defer(()-> {
                                                        if(bankAccount.getProfile().toUpperCase().equals("VIP")){
                                                            return getCreditCardByClient(x.getIdentityNumber())
                                                                    .flatMap(z-> bankAccountRepository.save(bankAccount))
                                                                    .switchIfEmpty(Mono.error(new FunctionalException(ErrorMessage.VIP_RESTRICTION.getValue())));
                                                        }
                                                        else
                                                            return bankAccountRepository.save(bankAccount);
                                                    })
                                                );
                                }
                                else
                                {
                                    return Mono.error(new FunctionalException(ErrorMessage.EMPRESARIAL_ACCOUNT_RESTRICTION.getValue()));
                                }
                            }
                    )
                        .switchIfEmpty(Mono.error(new FunctionalException(ErrorMessage.CLIENT_NOT_FOUND.getValue())));
        } else if (bankAccount.getAccountType().toUpperCase().equals("CTE")) {
            bankAccount.setMovementQuantity(-1);
            return getClientByIdentityNumber(bankAccount.getOwners().get(0))
                    .flatMap(x-> {
                                if (x.getClientType().getDescription().toUpperCase().equals("PERSONAL")) {
                                    return bankAccountRepository.findTop1ByOwnersAndAccountType(bankAccount.getOwners(), "CTE")
                                            .flatMap(y -> Mono.error(new FunctionalException(ErrorMessage.PERSONAL_CTE_RESTRICTION.getValue())))
                                            .switchIfEmpty(Mono.defer(()->bankAccountRepository.save(bankAccount)));
                                }
                                else
                                {
                                    if(bankAccount.getProfile().toUpperCase().equals("PYME")){
                                        bankAccount.setMaintenanceCommision(0d);
                                        return getCreditCardByClient(x.getIdentityNumber())
                                                .flatMap(z-> bankAccountRepository.save(bankAccount))
                                                .switchIfEmpty(Mono.error(new FunctionalException(ErrorMessage.PYME_RESTRICTION.getValue())));
                                    }
                                    else
                                        return bankAccountRepository.save(bankAccount);
                                }
                            }
                    )
                    .switchIfEmpty(Mono.error(new FunctionalException(ErrorMessage.CLIENT_NOT_FOUND.getValue())));
        }
        else if(bankAccount.getAccountType().toUpperCase().equals("FIJ")){
            bankAccount.setMaintenanceCommision(0d);
            bankAccount.setMovementQuantity(1);
            return getClientByIdentityNumber(bankAccount.getOwners().get(0))
                    .flatMap(x-> {
                                if (x.getClientType().getDescription().toUpperCase().equals("PERSONAL")) {
                                    return bankAccountRepository.findTop1ByOwnersAndAccountType(bankAccount.getOwners(), "FIJ")
                                            .flatMap(y -> Mono.error(new FunctionalException(ErrorMessage.PERSONAL_FIJO_RESTRICTION.getValue())))
                                            .switchIfEmpty(Mono.defer(()->bankAccountRepository.save(bankAccount)));
                                }
                                else
                                {
                                    return Mono.error(new FunctionalException(ErrorMessage.EMPRESARIAL_ACCOUNT_RESTRICTION.getValue()));
                                }
                            }
                    )
                    .switchIfEmpty(Mono.error(new FunctionalException(ErrorMessage.CLIENT_NOT_FOUND.getValue())));
        }
        else return Mono.error(new FunctionalException(ErrorMessage.ACCOUNTTYPE_NOT_FOUND.getValue()));

    }
}
