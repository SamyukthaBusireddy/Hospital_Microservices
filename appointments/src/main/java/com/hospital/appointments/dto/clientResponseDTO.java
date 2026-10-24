package com.hospital.appointments.dto;

import com.hospital.appointments.advices.ResponseBody;
import com.hospital.appointments.advices.ResponseHeader;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class clientResponseDTO<T> {
    private ResponseBody<T> responseBody;
    private ResponseHeader responseHeader;
}
