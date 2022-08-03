package com.transportersInc.test.dto;

import com.transportersInc.test.entities.Stats;
import java.util.List;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseDTO {

    private double containersDispatched;
    private double containersNotDispatched;
    private double budgetUsed;

}
