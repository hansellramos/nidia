package com.transportersInc.test.entities;

import com.transportersInc.test.model.Container;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Stats {

    private double containersDispatched;
    private double containersNotDispatched;
    private double budgetUsed;
    private List<Container> containers;

}
