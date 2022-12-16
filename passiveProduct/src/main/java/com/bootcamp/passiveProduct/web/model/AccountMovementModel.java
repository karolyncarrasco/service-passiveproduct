package com.bootcamp.passiveProduct.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountMovementModel {

    @Id
    private String id;

    @NotBlank(message = "Debe indicar la cuenta")
    public String accountId;

    public LocalDate movementDate;

    @Min(value = 1, message = "El monto debe ser mayor a 0")
    public double amount;

    @NotBlank(message = "Debe indicar el tipo de movimiento D: Débito, A: Abono")
    public String type;

}
