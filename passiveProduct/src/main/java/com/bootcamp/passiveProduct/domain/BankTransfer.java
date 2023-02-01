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
@Document(value = "bankTransfer")
public class BankTransfer {
    @Id
    private String id;

    @NotNull
    private String sourceAccount;

    @NotNull
    private String targetAccount;

    @NotNull
    private LocalDate transferDate;

    @NotNull
    private double amount;

}
