package com.backend.core.infrastructure.config.google_drive;

import com.backend.core.infrastructure.config.constants.ConstantValue;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;


@Component
public class GoogleDriveConfig {
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static GoogleDriveConfig ggDriveConfigInstance;
    public GoogleCredential googleCredential;


    public GoogleDriveConfig() {
    }


    public static GoogleDriveConfig getGgDriveConfigInstance() {
        if (ggDriveConfigInstance == null) {
            synchronized (GoogleDriveConfig.class) {
                if (ggDriveConfigInstance == null) {
                    ggDriveConfigInstance = new GoogleDriveConfig();
                }
            }
        }
        return ggDriveConfigInstance;
    }


    public Drive getInstance() throws GeneralSecurityException, IOException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, googleCredential())
                .setApplicationName(ConstantValue.APPLICATION_NAME)
                .build();
    }


    private GoogleCredential googleCredential() throws IOException {
        FileInputStream keyFile = new FileInputStream(ConstantValue.SERVICE_ACCOUNT_PRIVATE_JSON_KEY);
        this.googleCredential = GoogleCredential.fromStream(keyFile).createScoped(Collections.singleton("https://www.googleapis.com/auth/drive"));
        return this.googleCredential;
    }
}
