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
    private String dataLimit;
    private Integer callMinutes;
    private Integer smsLimit;
    private Double price;
    private int validityDays;
    private int creditBundleId;
    private boolean isDeleted = false;



}

