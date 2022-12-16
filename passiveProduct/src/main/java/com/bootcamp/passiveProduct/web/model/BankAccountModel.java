package com.bootcamp.passiveProduct.web.model;

import com.bootcamp.passiveProduct.domain.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountModel {
    @Id
    private String id;

    @NotBlank(message="Tipo de cuenta  cannot be null or empty")
    private String accountType;

    /*@NotBlank(message="Modality  cannot be null or empty")
    private String modalityAccount;*/

    @NotBlank(message="Número de cuenta  cannot be null or empty")
    @Indexed(unique = true)
    private String accountNumber;

    @NotBlank(message="CCI  cannot be null or empty")
    @Indexed(unique = true)
    private String cci;

    /*@NotBlank(message="Monto disponible  cannot be null or empty")
    private Double availableBalance;*/

    private Double balance;

    //@NotNull("Debe definir un monto de apertura")
    private Double openAmmount;

    @NotBlank(message="Moneda  cannot be null or empty")
    private String currency;

    private Double maintenanceCommision;

    private Integer movementQuantity;

    private Integer movementFrecuency;

    private List<String> owners;

    private List<String> signatories;

    private LocalDate openingDate;

    private String profile;

    private Integer maxTransaction;

    private double comissionTransaction;
}
