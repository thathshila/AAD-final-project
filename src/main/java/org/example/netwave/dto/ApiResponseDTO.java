package org.example.netwave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponseDTO <T> {
    private int code;
    private String message;
    private boolean status;
    private T data;
}