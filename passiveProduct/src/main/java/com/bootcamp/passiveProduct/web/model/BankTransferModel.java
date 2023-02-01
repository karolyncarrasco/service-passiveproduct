package com.bootcamp.passiveProduct.web.model;

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
public class BankTransferModel {
    @Id
    private String id;

    @NotBlank(message = "La cuenta origen no debe ser vacía")
    private String sourceAccount;

    @NotBlank(message = "La cuenta destino no debe ser vacía")
    private String targetAccount;

    //@NotBlank(message = "La fecha de transferencia no debe ser vacía")
    private LocalDate transferDate;

    private double amount;
}
