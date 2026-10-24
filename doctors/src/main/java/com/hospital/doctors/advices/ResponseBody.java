package com.hospital.doctors.advices;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
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
