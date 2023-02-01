package com.bootcamp.passiveProduct.web;

import com.bootcamp.passiveProduct.domain.DebitCard;
import com.bootcamp.passiveProduct.service.DebitCardService;
import com.bootcamp.passiveProduct.web.mapper.DebitCardMapper;
import com.bootcamp.passiveProduct.web.model.DebitCardModel;
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
@RequestMapping("/v1/debitcard")
public class DebitCardController {
    @Value("${spring.application.name}")
    String name;

    @Value("${server.port}")
    String port;

    @Autowired
    private DebitCardService debitCardService;

    @Autowired
    private DebitCardMapper debitCardMapper;

    @GetMapping()
    public Mono<ResponseEntity<Flux<DebitCardModel>>> getAll(){
        log.info("getAll executed");
        return Mono.just(ResponseEntity.ok()
                .body(debitCardService.findAll()
                        .map(cc -> debitCardMapper.entityToModel(cc))));
    }

    @PostMapping
    public Mono<ResponseEntity<DebitCardModel>> create(@Valid @RequestBody DebitCardModel request){
        log.info("create executed {}", request);
        return debitCardService.create(debitCardMapper.modelToEntity(request))
                .map(cc -> debitCardMapper.entityToModel((DebitCard) cc))
                .flatMap(c -> Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "BankTransfer", c.getId())))
                        .body(c)));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<DebitCardModel>> updateById(@PathVariable String id, @RequestBody String account) {
        log.info("updateById executed {}:{}", id, account);
        return debitCardService.add(id, account)
                .map(x -> debitCardMapper.entityToModel(x))
                .flatMap(c -> Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "DebitCard", c.getId())))
                        .body(c)))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
