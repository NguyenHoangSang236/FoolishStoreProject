package com.backend.core.util;

import com.backend.core.configuration.GoogleDriveConfig;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

@Component
public class GoogleAuthenTokenManager {
    private final GoogleCredentials credentials;
    private AccessToken token;

    private static GoogleAuthenTokenManager ggAuthenTokenManager;


    public GoogleAuthenTokenManager(GoogleCredentials credentials) throws IOException {
        this.credentials = credentials;
        this.token = credentials.getAccessToken();
    }


    public synchronized AccessToken getToken() throws IOException {
        if (token != null && token.getExpirationTime().after(Date.from(Instant.now()))) {
            return token;
        }
        this.token = credentials.refreshAccessToken();
        return token;
    }
}
