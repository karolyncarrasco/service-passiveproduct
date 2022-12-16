package com.bootcamp.passiveProduct.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@ToString
@EqualsAndHashCode(of = {"cardId"})
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "debitcard")
public class DebitCard {

    @Id
    private String cardId;

    @NotNull
    @Indexed(unique = true)
    private String cardNumber;

    @NotNull
    private LocalDate openingDate;

    @NotNull
    private LocalDate expiryDate;

    @NotNull
    private Boolean status;

    @NotNull
    private String customerId;

    @NotNull
    private String accounts [];

    @NotNull
    private LocalDate creationDate;

    @NotNull
    private String creationUser;

    @NotNull
    private LocalDate modifiedDate;

    @NotNull
    private String modifiedUser;

}
