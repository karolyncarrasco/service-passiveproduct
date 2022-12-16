package com.bootcamp.passiveProduct.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@ToString
@EqualsAndHashCode(of = {"accountId"})
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "account")
public class BankAccount {
    @Id
    private String id;

    @NotNull
    private String accountType; /*AHO, CTE, FIJ*/

    /*@NotNull
    private String modalityAccount;*/

    @NotNull
    @Indexed(unique = true)
    private String accountNumber;

    @NotNull
    @Indexed(unique = true)
    private String cci;

    /*@NotNull
    private Double availableBalance;*/

    @NotNull
    private Double balance;

    @NotNull
    private Double openAmmount;

    @NotNull
    private String currency;

    @NotNull
    private Double maintenanceCommision;

    @NotNull
    private Integer movementQuantity;

    @NotNull
    private Integer movementFrecuency;

    @NotNull
    private List<String> owners;

    private List<String> signatories;

    @NotNull
    private LocalDate openingDate;

    @NotNull
    private LocalDate creationDate;

    @NotNull
    private String creationUser;

    @NotNull
    private LocalDate modifiedDate;

    @NotNull
    private String modifiedUser;
}
