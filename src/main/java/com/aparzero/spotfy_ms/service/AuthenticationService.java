package com.aparzero.spotfy_ms.service;

import com.aparzero.spotfy_ms.domain.Response;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface AuthenticationService {
    ResponseEntity<Response> login();
    ResponseEntity<Response> getCode(String code, HttpServletResponse response) throws IOException;
}
