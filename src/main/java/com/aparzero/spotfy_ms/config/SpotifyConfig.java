package com.aparzero.spotfy_ms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

import java.net.URI;

@Configuration
public class SpotifyConfig {
    private final String clientId;
    private final String clientSecret;
    private final String redirectUrl;

    public SpotifyConfig(@Value("${spotify.client-id}")final String clientId,
                         @Value("${spotify.client-secret}") final String clientSecret,
                         @Value("${spotify.redirect-url}") final String redirectUrl) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUrl = redirectUrl;
    }

    @Bean
    SpotifyApi spotifyApi(){
       return new SpotifyApi.Builder()
               .setClientId(clientId)
               .setClientSecret(clientSecret)
               .setRedirectUri(SpotifyHttpManager.makeUri(redirectUrl))
               .build();
    }
}
