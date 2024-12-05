package com.aparzero.spotfy_ms.controller;

import com.aparzero.spotfy_ms.domain.Response;
import com.aparzero.spotfy_ms.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
    @RequestMapping("/spotify-ms/api/v1/auth")
public class AuthController {


    private final AuthenticationService authenticationService;

    public AuthController(final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @GetMapping("/login")
    public ResponseEntity<Response> login() {
       return authenticationService.login();
    }

    @GetMapping("/code")
    public ResponseEntity<Response> getCode(@RequestParam("code") String userCode, HttpServletResponse servletResponse) throws IOException {
        return  authenticationService.getCode(userCode,servletResponse);
    }

}
