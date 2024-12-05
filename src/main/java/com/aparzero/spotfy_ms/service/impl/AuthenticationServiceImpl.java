package com.aparzero.spotfy_ms.service.impl;

import com.aparzero.spotfy_ms.domain.Response;
import com.aparzero.spotfy_ms.domain.Token;
import com.aparzero.spotfy_ms.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {


    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private final SpotifyApi spotifyApi;

    private final String frontEndUrl;

    public AuthenticationServiceImpl(final SpotifyApi spotifyApi,
                                     @Value("${cors.front-end-domain}") final String frontEndUrl) {
        this.spotifyApi = spotifyApi;
        this.frontEndUrl = frontEndUrl;
    }

    @Override
    public ResponseEntity<Response> login() {
        ResponseEntity<Response> response;

        try {
            final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                    .scope("user-read-email user-library-read user-read-recently-played playlist-read-collaborative user-top-read")  // Use space instead of commas
                    .show_dialog(true)
                    .build();

            final URI uri = authorizationCodeUriRequest.execute();
            response = Response.success(String.valueOf(uri));
        }catch (Exception e){
            response = Response.failed(e.getMessage(),400);
        }
        return response;
    }

    @Override
    public ResponseEntity<Response> getCode(String code, HttpServletResponse servletResponse) throws IOException {

        LOG.info("USER CODE: {}",code);

        ResponseEntity<Response> response;
        final AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
        try {
            LOG.info("VALIDATING CODE...");
            final AuthorizationCodeCredentials credentials = authorizationCodeRequest.execute();
            LOG.info("VALIDATION SUCCESS!!...");
            final String accessToken = credentials.getAccessToken();
            final String refreshToken = credentials.getRefreshToken();
            final String redirectUrl = frontEndUrl + "/main?q=";

            final String redirectUrlWithParams = redirectUrl + "&access_token=" + accessToken +
                    "&refresh_token=" + refreshToken;

            LOG.info("FRONT-END-URL: {}",redirectUrlWithParams);
            servletResponse.sendRedirect(redirectUrlWithParams);
            LOG.info("WHERE DOES IT ERROR");
            final Token token = new Token(credentials.getAccessToken(),credentials.getRefreshToken());
            response = Response.success(token);

        }catch (Exception e){
            LOG.info("ERROR: {}",e.getMessage());
            response = Response.failed(e.getMessage(),400);
        }
        return response;
    }
}
