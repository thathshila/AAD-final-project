package org.example.netwave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.netwave.entity.Packages;
import org.example.netwave.entity.UserConnection;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PackageDTO {
    private int packageId;
    private String packageName;
    private String packageType;
    private Float dataLimit;
    private Integer callMinutes;
    private Integer smsLimit;
    private Double price;
    private int validityDays;
    private int credit_bundle_id;
    private UserConnection userConnection;

}
