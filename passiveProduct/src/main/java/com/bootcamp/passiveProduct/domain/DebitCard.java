package com.bootcamp.passiveProduct.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "debitcard")
public class DebitCard {

    @Id
    private String id;

    @NotNull
    @Indexed(unique = true)
    private String cardNumber;

    @NotNull
    private LocalDate openingDate;

    @NotNull
    private LocalDate expiryDate;

    @NotNull
    private String customerId;

    private ArrayList<String> accounts;

    @NotNull
    private String mainAccount;

}
