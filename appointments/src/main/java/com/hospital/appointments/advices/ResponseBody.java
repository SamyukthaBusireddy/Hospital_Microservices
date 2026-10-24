package com.hospital.appointments.advices;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBody<T>{
    private T data;
    private ApiError error;

    public ResponseBody(ApiError err){
        this.error=err;
    }
    public ResponseBody(T data) {
        this.data = data;
    }
}
