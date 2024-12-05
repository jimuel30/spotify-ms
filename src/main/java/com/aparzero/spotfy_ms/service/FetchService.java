package com.aparzero.spotfy_ms.service;

import com.aparzero.spotfy_ms.domain.Response;
import org.springframework.http.ResponseEntity;

public interface FetchService {
    ResponseEntity<Response> getUserTopArtists(String accessToken);
    ResponseEntity<Response> getArtistInfo(String accessToken, String artistId);

    ResponseEntity<Response> getUserProfile(String accessToken);
}
