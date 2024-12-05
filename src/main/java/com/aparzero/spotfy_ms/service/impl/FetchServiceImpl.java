package com.aparzero.spotfy_ms.service.impl;

import com.aparzero.spotfy_ms.domain.Response;
import com.aparzero.spotfy_ms.service.FetchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.data.artists.GetArtistRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;


@Service
public class FetchServiceImpl implements FetchService {

    private static final Logger LOG = LoggerFactory.getLogger(FetchServiceImpl.class);

    private final SpotifyApi spotifyApi;

    public FetchServiceImpl(final SpotifyApi spotifyApi) {
        this.spotifyApi = spotifyApi;
    }


    @Override
    public ResponseEntity<Response> getUserTopArtists(final String accessToken) {

        LOG.info("Access Token Received: {}",accessToken);
        ResponseEntity<Response> response;
        try {
            spotifyApi.setAccessToken(accessToken);
            final GetUsersTopArtistsRequest request = spotifyApi.getUsersTopArtists()
                    .time_range("long_term")
                    .limit(12)
                    .offset(5)
                    .build();
            final Paging<Artist> artistPaging = request.execute();
            LOG.info("Fetching User top artists SUCCESS!!");
            response = Response.success(artistPaging.getItems());

        }catch (Exception e){
            LOG.info("Error Occurred: {}",e.getMessage());
            response = Response.failed(e.getMessage(),400);
        }
        return response;
    }

    @Override
    public ResponseEntity<Response> getArtistInfo(String accessToken, String artistId) {
        LOG.info("Fetching Artist Info: {}",artistId);
        ResponseEntity<Response> response;
        try {
            spotifyApi.setAccessToken(accessToken);
            final GetArtistRequest request = spotifyApi.getArtist(artistId).build();
            LOG.info("Fetching Artist Info SUCCESS!!");
            response = Response.success(request.execute());

        }catch (Exception e){
            LOG.info("Error Occurred: {}",e.getMessage());
            response = Response.failed(e.getMessage(),400);
        }
        return response;
    }

    @Override
    public ResponseEntity<Response> getUserProfile(String accessToken) {
        LOG.info("Fetching USER Info...");
        ResponseEntity<Response> response;
        try {
            spotifyApi.setAccessToken(accessToken);
            final GetCurrentUsersProfileRequest request = spotifyApi.getCurrentUsersProfile().build();
            LOG.info("Fetching USER Info SUCCESS!!");
            response = Response.success(request.execute());

        }catch (Exception e){
            LOG.info("Error Occurred: {}",e.getMessage());
            response = Response.failed(e.getMessage(),400);
        }
        return response;
    }
}
