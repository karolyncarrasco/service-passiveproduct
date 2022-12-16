package com.bootcamp.passiveProduct.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "accountmovement")
public class AccountMovement {
    @Id
    private String id;

    @NotNull
    public String accountId;

    @NotNull
    public LocalDate movementDate;

    @NotNull
    public double amount;

    @NotNull
    public String type; //A: abono, D: Débito



}
