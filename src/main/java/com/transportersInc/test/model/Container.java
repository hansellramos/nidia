package com.transportersInc.test.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Container {

    @Valid
    @NotNull(message = "el campo name es obligatorio")
    @NotEmpty(message="El campo name no debe ir vacío")
    @Length(min=2,max=45, message= "la longitud del campo name debe estar entre 2 y 45")
    private String name;

    @Valid
    @NotNull(message = "el campo transportCost es obligatorio")
    @NotEmpty(message="El campo transportCost no debe ir vacío")
    @DecimalMin (value = "0.01", inclusive = true, message = "El valor mínimo del campo transportCost es 0.01")
    @Digits(integer = 1, fraction = 2, message = "El campo transportCost debe ser un decimal")
    private double transportCost;

    @Valid
    @NotNull(message = "el campo containerPrice es obligatorio")
    @NotEmpty(message="El campo containerPrice no debe ir vacío")
    @DecimalMin (value = "0.01", inclusive = true, message = "El valor mínimo del campo containerPrice es 0.01")
    @Digits(integer = 1, fraction = 2, message = "El campo containerPrice debe ser un decimal")
    private double containerPrice;

}
