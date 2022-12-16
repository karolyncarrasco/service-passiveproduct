package com.bootcamp.passiveProduct.web;

import com.bootcamp.passiveProduct.domain.BankAccount;
import com.bootcamp.passiveProduct.service.BankAccountService;
import com.bootcamp.passiveProduct.web.mapper.BankAccountMapper;
import com.bootcamp.passiveProduct.web.model.BankAccountModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/bankaccount")
public class BankAccountController {
    @Value("${spring.application.name}")
    String name;

    @Value("${server.port}")
    String port;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountMapper bankAccountMapper;


    @GetMapping()
    public Mono<ResponseEntity<Flux<BankAccountModel>>> getAll(){
        log.info("getAll executed");
        return Mono.just(ResponseEntity.ok()
                .body(bankAccountService.findAll()
                        .map(cc -> bankAccountMapper.entityToModel(cc))));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<BankAccountModel>> getById(@PathVariable String id){
        log.info("getById executed {}", id);
        Mono<BankAccount> response = bankAccountService.findById(id);
        return response
                .map(cc -> bankAccountMapper.entityToModel(cc))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<BankAccountModel>> create(@Valid @RequestBody BankAccountModel request){
        log.info("create executed {}", request);
        return bankAccountService.create(bankAccountMapper.modelToEntity(request))
                .map(cc -> bankAccountMapper.entityToModel((BankAccount) cc))
                .flatMap(c -> Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "Client", c.getId())))
                        .body(c)));

    }
}
