package com.aparzero.spotfy_ms.domain;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class Response {

    public Response (int status, String message, Object data, Boolean isSuccess){
        this.status = status;
        this.message = message;
        this.data = data;
        this.isSuccess = isSuccess;
    }

    private int status;
    private String message;
    private Boolean isSuccess;
    private Object data;

    public static ResponseEntity<Response> success( Object data){
        Response response = new Response(200, "SUCCESS", data, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    public static ResponseEntity<Response> failed(String message, int status){
        Response response = new Response(status, message, null, false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
