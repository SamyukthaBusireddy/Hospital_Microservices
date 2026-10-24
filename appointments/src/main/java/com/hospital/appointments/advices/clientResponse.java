package com.hospital.appointments.advices;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class clientResponse<T> {
    private ResponseBody<T> responseBody;
    private ResponseHeader responseHeader;
}
