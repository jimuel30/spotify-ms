package com.aparzero.spotfy_ms.domain;


import lombok.Data;

@Data
public class Token {
    public Token(String accessToken, String refreshToken ){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
    private String accessToken;
    private String refreshToken;
}
