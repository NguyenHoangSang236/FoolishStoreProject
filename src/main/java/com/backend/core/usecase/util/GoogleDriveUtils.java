package com.backend.core.usecase.util;

import com.backend.core.infrastructure.config.google_drive.GoogleDriveConfig;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Component
public class GoogleDriveUtils {
    @Autowired
    private GoogleDriveConfig googleDriveConfig;


    // Get all file
    public List<File> listEverything() throws IOException, GeneralSecurityException {
        googleDriveConfig = GoogleDriveConfig.getGgDriveConfigInstance();
        // Print the names and IDs for up to 10 files.
        FileList result = googleDriveConfig.getInstance()
                .files()
                .list()
                .setPageSize(1000)
                .setFields("nextPageToken, files(id, name, size, thumbnailLink, shared)") // get field of Google Drive file
                .execute();

        return result.getFiles();
    }


    // Get all folder
    public List<File> listFolderContent(String parentId) throws IOException, GeneralSecurityException {
        googleDriveConfig = GoogleDriveConfig.getGgDriveConfigInstance();

        if (parentId == null) {
            parentId = "root";
        }

        String query = "'" + parentId + "' in parents";

        FileList result = googleDriveConfig.getInstance()
                .files()
                .list()
                .setQ(query)
                .setPageSize(10)
                .setFields("nextPageToken, files(id, name)") // get field of Google Drive folder
                .execute();

        return result.getFiles();
    }


    // Download file by id
    public void downloadFile(String id, OutputStream outputStream) throws IOException, GeneralSecurityException {
        if (id != null) {
            googleDriveConfig = GoogleDriveConfig.getGgDriveConfigInstance();
            googleDriveConfig.getInstance().files().get(id).executeMediaAndDownloadTo(outputStream);
        }
    }


    // Delete file by id
    public void deleteFileOrFolder(String fileId) throws Exception {
        try {
            googleDriveConfig = GoogleDriveConfig.getGgDriveConfigInstance();
            googleDriveConfig.getInstance().files().delete(fileId).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Set permission drive file
    private Permission setPermission(String type, String role) {
        Permission permission = new Permission();
        permission.setType(type);
        permission.setRole(role);
        return permission;
    }


    // Upload file
    public String uploadFile(MultipartFile file, String folderName, String type, String role) throws Exception {
        String folderId = getFolderId(folderName);

        try {
            if (file != null) {
                File fileMetadata = new File();
                fileMetadata.setParents(Collections.singletonList(folderId));
                fileMetadata.setShortcutDetails(new File.ShortcutDetails().setTargetId(folderId));
                fileMetadata.setName(file.getOriginalFilename());
                File uploadFile = googleDriveConfig.getInstance()
                        .files()
                        .create(
                                fileMetadata,
                                new InputStreamContent(
                                        file.getContentType(),
                                        new ByteArrayInputStream(file.getBytes())
                                )
                        )
                        .setFields("id")
                        .execute();

                if (!type.equals("private") && !role.equals("private")) {
                    // Call Set Permission drive
                    googleDriveConfig
                            .getInstance()
                            .permissions()
                            .create(uploadFile.getId(), setPermission(type, role))
                            .execute();
                }
                return uploadFile.getId();
            }
        } catch (TokenResponseException e) {
            if (e.getDetails().getError().equals("invalid_grant")) {
                return uploadFile(file, folderName, type, role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    // get id folder google drive
    public String getFolderId(String folderName) throws Exception {
        String parentId = null;
        String[] folderNames = folderName.split("/");

        googleDriveConfig = GoogleDriveConfig.getGgDriveConfigInstance();

        Drive driveInstance = googleDriveConfig.getInstance();
        for (String name : folderNames) {
            parentId = findOrCreateFolder(parentId, name, driveInstance);
        }
        return parentId;
    }


    private String findOrCreateFolder(String parentId, String folderName, Drive driveInstance) throws Exception {
        String folderId = searchFolderId(parentId, folderName, driveInstance);
        // Folder already exists, so return id
        if (folderId != null) {
            return folderId;
        }
        //Folder don't exist, create it and return folderId
        File fileMetadata = new File();
        fileMetadata.setMimeType("application/vnd.google-apps.folder");
        fileMetadata.setName(folderName);

        if (parentId != null) {
            fileMetadata.setParents(Collections.singletonList(parentId));
        }
        return driveInstance.files().create(fileMetadata)
                .setFields("id")
                .execute()
                .getId();
    }


    private String searchFolderId(String parentId, String folderName, Drive service) throws Exception {
        String folderId = null;
        String pageToken = null;
        FileList result = null;

        File fileMetadata = new File();
        fileMetadata.setMimeType("application/vnd.google-apps.folder");
        fileMetadata.setName(folderName);

        do {
            String query = " mimeType = 'application/vnd.google-apps.folder' ";
            if (parentId == null) {
                query = query + " and 'root' in parents";
            } else {
                query = query + " and '" + parentId + "' in parents";
            }
            result = service.files().list().setQ(query)
                    .setSpaces("drive")
                    .setFields("nextPageToken, files(id, name)")
                    .setPageToken(pageToken)
                    .execute();

            for (File file : result.getFiles()) {
                if (file.getName().equalsIgnoreCase(folderName)) {
                    folderId = file.getId();
                }
            }
            pageToken = result.getNextPageToken();
        } while (pageToken != null && folderId == null);

        return folderId;
    }

    public boolean checkExpiredAccessToken() {
        return (GoogleDriveConfig.getGgDriveConfigInstance().googleCredential.getAccessToken() != null &&
                GoogleDriveConfig.getGgDriveConfigInstance().googleCredential.getExpirationTimeMilliseconds() < System.currentTimeMillis());
    }
}

