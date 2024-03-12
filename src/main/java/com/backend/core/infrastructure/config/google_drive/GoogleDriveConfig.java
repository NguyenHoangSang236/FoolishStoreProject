package infrastructure.config.google_drive;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Component
public class GoogleDriveConfig {
    private static final String APPLICATION_NAME = "FoolishFashionStore"; // Application name
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "src/main/resources/client_secret.json"; // path file Google Drive Service
    private static final String SERVICE_ACCOUNT_PRIVATE_JSON_KEY = "src/main/resources/service_account_private_key.json";

    private static GoogleDriveConfig ggDriveConfigInstance;

    //    public Credential googleCredential;
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
                .setApplicationName(APPLICATION_NAME)
                .build();
    }


//    /**
//     * Creates an authorized Credential object.
//     *
//     * @param HTTP_TRANSPORT The network HTTP Transport.
//     * @return An authorized Credential object.
//     * @throws IOException If the credentials.json file cannot be found.
//     */
//    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
//        // Load client secrets.
//        InputStream in = new FileInputStream(CREDENTIALS_FILE_PATH);
//        if (in == null) {
//            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
//        }
//        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
//
//        // Build flow and trigger user authorization request.
//        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
//                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
//                .setAccessType("offline")
//                .build();
//
//        // PORT URI OF GOOGLE SERVICE
//        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8081).build();
//
//        this.googleCredential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
//
//        return this.googleCredential;
//    }

    private GoogleCredential googleCredential() throws IOException, GeneralSecurityException {
        FileInputStream keyFile = new FileInputStream(SERVICE_ACCOUNT_PRIVATE_JSON_KEY);
        this.googleCredential = GoogleCredential.fromStream(keyFile).createScoped(Collections.singleton("https://www.googleapis.com/auth/drive"));
        return this.googleCredential;
    }
}
