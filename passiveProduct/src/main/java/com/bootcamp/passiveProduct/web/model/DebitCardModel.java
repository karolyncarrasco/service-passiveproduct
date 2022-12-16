package com.bootcamp.passiveProduct.web.model;

import com.bootcamp.passiveProduct.domain.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DebitCardModel {
    @Id
    private String cardId;

    @NotBlank(message="Número de tarjeta  cannot be null or empty")
    private String cardNumber;

    @NotBlank(message="Fecha de apertura  cannot be null or empty")
    private LocalDate openingDate;

    @NotBlank(message="Fecha de expiración  cannot be null or empty")
    private LocalDate expiryDate;

    @NotBlank(message="Estado  cannot be null or empty")
    private Boolean status;

    @NotBlank(message="Cliente  cannot be null or empty")
    private String customerId;

    @NotBlank(message="Cuentas  cannot be null or empty")
    private String accounts [];
}
