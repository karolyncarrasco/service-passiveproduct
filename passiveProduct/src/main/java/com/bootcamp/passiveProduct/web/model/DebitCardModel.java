package com.bootcamp.passiveProduct.web.model;

import com.bootcamp.passiveProduct.domain.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DebitCardModel {
    @Id
    private String id;

    @NotBlank(message="Número de tarjeta  cannot be null or empty")
    private String cardNumber;

    private LocalDate openingDate;

    private LocalDate expiryDate;

    private String customerId;

    private ArrayList<String> accounts;

    @NotBlank(message="Cuentas  cannot be null or empty")
    private String mainAccount;
}
