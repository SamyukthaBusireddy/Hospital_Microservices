package com.hospital.billings.dto;

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
