package com.bootcamp.passiveProduct.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ClientModel {
    @Id
    private String id;

    @NotBlank(message="Identity Number cannot be null or empty")
    private String identityNumber;

    private IdentityTypeModel identityType;

    private ClientTypeModel clientType;

    @NotBlank(message="Name cannot be null or empty")
    private String name;

    @NotBlank(message="Last name cannot be null or empty")
    //@JsonProperty("Apellido")
    private String lastName;

    @NotBlank(message="Business name cannot be null or empty")
    private String businessName;

    @NotBlank(message="Email cannot be null or empty")
    private String email;

    @NotBlank(message="Phone Number cannot be null or empty")
    private String phoneNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;


}
