package com.transportersInc.test.dto;

import com.transportersInc.test.model.Container;
import lombok.*;

import javax.validation.constraints.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestDTO {

    @NotNull(message = "el campo budget es obligatorio")
    @NotEmpty(message="El campo budget no debe ir vacío")
    @DecimalMin (value = "0.01", inclusive = true, message = "El valor mínimo del campo budget es 0.01")
    @Digits(integer = 1, fraction = 2, message = "El campo budget debe ser un decimal")
    private double budget;

    @NotNull(message = "el campo data es obligatorio")
    @Size(min = 1,max = 10,message = "Se debe evaluar de 1 a 10 contenedores para realizar el despacho")
    private List<Container> data;

}
