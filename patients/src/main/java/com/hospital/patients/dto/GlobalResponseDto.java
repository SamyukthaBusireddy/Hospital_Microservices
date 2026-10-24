package com.hospital.patients.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GlobalResponseDto {
    private String msg;
    private String error;
}
