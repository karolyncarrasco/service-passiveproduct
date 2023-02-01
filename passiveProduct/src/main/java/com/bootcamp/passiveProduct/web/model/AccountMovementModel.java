package com.bootcamp.passiveProduct.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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

    private LocalDate movementDate;

    @NotBlank(message = "Debe indicar la descripción del movimiento")
    private String summary;

    private String recipientAccount;

    private String targetEntity;

    @Min(value = 1, message = "El monto debe ser mayor a 0")
    private double amount;

    @NotBlank(message = "Debe indicar el tipo de movimiento D: Débito, A: Abono")
    private String type;

    private double comission;

}
