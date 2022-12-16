package com.bootcamp.passiveProduct.web;

import com.bootcamp.passiveProduct.domain.AccountMovement;
import com.bootcamp.passiveProduct.domain.BankAccount;
import com.bootcamp.passiveProduct.service.AccountMovementService;
import com.bootcamp.passiveProduct.web.mapper.AccountMovementMapper;
import com.bootcamp.passiveProduct.web.model.AccountMovementModel;
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
@RequestMapping("/v1/accountmovement")
public class AccountMovementController {
    @Value("${spring.application.name}")
    String name;

    @Value("${server.port}")
    String port;

    @Autowired
    private AccountMovementService accountMovementService;

    @Autowired
    private AccountMovementMapper accountMovementMapper;


    @GetMapping()
    public Mono<ResponseEntity<Flux<AccountMovementModel>>> getAll(){
        log.info("getAll executed");
        return Mono.just(ResponseEntity.ok()
                .body(accountMovementService.findAll()
                        .map(cc -> accountMovementMapper.entityToModel(cc))));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<AccountMovementModel>> getById(@PathVariable String id){
        log.info("getById executed {}", id);
        Mono<AccountMovement> response = accountMovementService.findById(id);
        return response
                .map(cc -> accountMovementMapper.entityToModel(cc))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<AccountMovementModel>> create(@Valid @RequestBody AccountMovementModel request){
        log.info("create executed {}", request);
        return accountMovementService.create(accountMovementMapper.modelToEntity(request))
                .map(cc -> accountMovementMapper.entityToModel((AccountMovement) cc))
                .flatMap(c -> Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "Client", c.getId())))
                        .body(c)));

    }
}
