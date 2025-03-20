package org.example.netwave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.netwave.entity.SIM;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SimDTO {
    private int simId;
    private String simNumber;
    private SIM.SIMStatus status = SIM.SIMStatus.ACTIVE;
}
