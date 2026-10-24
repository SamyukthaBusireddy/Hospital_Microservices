package com.hospital.appointments.advices;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiResponse<T> {

    //    @JsonFormat(pattern = "hh:mm:ss dd-MM-yyyy")
    private ResponseHeader responseHeader;
    private ResponseBody responseBody;

    public ApiResponse(T data, HttpStatus status) {
        this.responseHeader = new ResponseHeader(status);
        this.responseBody = new ResponseBody<>(data);
    }
    public ApiResponse(ApiError error, HttpStatus status) {
        this.responseHeader = new ResponseHeader(status);
        this.responseBody = new ResponseBody<>(error);
    }
}
