package com.bootcamp.passiveProduct.web;


import com.bootcamp.passiveProduct.domain.BankAccount;
import com.bootcamp.passiveProduct.domain.BankTransfer;
import com.bootcamp.passiveProduct.service.BankTransferService;
import com.bootcamp.passiveProduct.web.mapper.BankTransferMapper;
import com.bootcamp.passiveProduct.web.model.BankAccountModel;
import com.bootcamp.passiveProduct.web.model.BankTransferModel;
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
@RequestMapping("/v1/banktransfer")
public class BankTransferController {
    @Value("${spring.application.name}")
    String name;

    @Value("${server.port}")
    String port;

    @Autowired
    private BankTransferService bankTransferService;

    @Autowired
    private BankTransferMapper bankTransferMapper;

    @GetMapping()
    public Mono<ResponseEntity<Flux<BankTransferModel>>> getAll(){
        log.info("getAll executed");
        return Mono.just(ResponseEntity.ok()
                .body(bankTransferService.findAll()
                        .map(cc -> bankTransferMapper.entityToModel(cc))));
    }

    @PostMapping
    public Mono<ResponseEntity<BankTransferModel>> create(@Valid @RequestBody BankTransferModel request){
        log.info("create executed {}", request);
        return bankTransferService.create(bankTransferMapper.modelToEntity(request))
                .map(cc -> bankTransferMapper.entityToModel((BankTransfer) cc))
                .flatMap(c -> Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "BankTransfer", c.getId())))
                        .body(c)));

    }
}
