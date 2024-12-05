package com.aparzero.spotfy_ms.controller;

import com.aparzero.spotfy_ms.domain.Response;
import com.aparzero.spotfy_ms.service.FetchService;
import com.aparzero.spotfy_ms.service.impl.AuthenticationServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spotify-ms/api/v1/fetch")
public class FetchController {


    private static final Logger LOG = LoggerFactory.getLogger(FetchController.class);
    private final FetchService fetchService;

    public FetchController(final FetchService fetchService) {
        this.fetchService = fetchService;
    }


    @GetMapping("/top-artist")
    public ResponseEntity<Response> getTopArtists(@RequestHeader("Authorization") String token) {

            return fetchService.getUserTopArtists(token);
    }

    @GetMapping("/artist")
    public ResponseEntity<Response> getTopArtists(@RequestHeader("Authorization") String token, @RequestParam String artistId){
            return fetchService.getArtistInfo(token,artistId);

    }

    @GetMapping("/user-profile")
    public ResponseEntity<Response> getUserProfile(@RequestHeader("Authorization") String token){

            return fetchService.getUserProfile(token);

    }

}
