package com.backend.core.controller;

import com.backend.core.configuration.GoogleDriveConfig;
import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.entity.dto.GoogleDriveFoldersDTO;
import com.backend.core.enums.ErrorTypeEnum;
import com.backend.core.service.GoogleDriveService;
import com.backend.core.util.ValueRenderUtils;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping(value = "/googleDrive",
                consumes = {"*/*"},
                produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GoogleDriveController {
    @Autowired
    GoogleDriveService googleDriveService;

    @Autowired
    GoogleDriveConfig googleDriveConfig;


    // Get all file on drive
//    @GetMapping("/getAllFolders")
//    public List<GoogleDriveFoldersDTO> getAllFolder() throws IOException, GeneralSecurityException {
//
////        List<GoogleDriveFileDTO> listFile = googleDriveService.getAllFile();
//        return googleDriveService.getAllFolder();
//    }

//     Upload file to public
    @PostMapping(value = "/uploadFile",
                 consumes = {"*/*"},
                 produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ApiResponse uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload,
                                  @RequestParam("filePath") String pathFile,
                                  @RequestParam("shared") String shared) {

        if (pathFile.equals("")){
            pathFile = "Root"; // Save to default folder if the user does not select a folder to save - you can change it
        }

        try {
            String fileId = googleDriveService.uploadFile(fileUpload, pathFile, Boolean.parseBoolean(shared));

            return new ApiResponse("success", ValueRenderUtils.getGoogleDriveUrlFromFileId(fileId));
        }
        catch (Exception e) {
            return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
        }
    }
//
//    // Delete file by id
//    @GetMapping("/delete/file/{id}")
//    public ModelAndView deleteFile(@PathVariable String id) throws Exception {
//        googleDriveService.deleteFile(id);
//        return new ModelAndView("redirect:" + "/");
//    }
//
//    // Download file
//    @GetMapping("/download/file/{id}")
//    public void downloadFile(@PathVariable String id, HttpServletResponse response) throws IOException, GeneralSecurityException {
//        googleDriveService.downloadFile(id, response.getOutputStream());
//    }
//
//    // Get all root folder on drive
//    @GetMapping("/list/folders") // or {"/list/folders","/list/folders/{parentId}"}
//    public ModelAndView listFolder() throws IOException, GeneralSecurityException {
//        ModelAndView mav = new ModelAndView("list_folder");
//        List<GoogleDriveFoldersDTO> listFolder = googleDriveService.getAllFolder();
//        mav.addObject("DTO", listFolder);
//        return mav;
//    }
//
//    // Create folder
//    @PostMapping("/create/folder")
//    public ModelAndView createFolder(@RequestParam("folderName") String folderName) throws Exception {
//        googleDriveService.createFolder(folderName);
//        return new ModelAndView("redirect:" + "/list/folders");
//    }
//
//    // Delete folder by id
//    @GetMapping("/delete/folder/{id}")
//    public ModelAndView deleteFolder(@PathVariable String id) throws Exception {
//        googleDriveService.deleteFolder(id);
//        return new ModelAndView("redirect:" + "/list/folders");
//    }


//    @PostMapping
//    public ApiResponse uploadBasic() throws IOException, GeneralSecurityException {
//        // Load pre-authorized user credentials from the environment.
//        // TODO(developer) - See https://developers.google.com/identity for
//        // guides on implementing OAuth2 for your application.
////        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault()
////                .createScoped(List.of(DriveScopes.DRIVE_FILE));
////        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(
////                credentials);
//
//        // Build a new authorized API client service.
//        Drive service = googleDriveConfig.getInstance();
//
//        // Upload file photo.jpg on drive.
//        File fileMetadata = new File();
//        fileMetadata.setName("photo.jpeg");
//        // File's content.
//        java.io.File filePath = new java.io.File("/home/mr/Downloads/index.jpeg");
//        // Specify media type and file-path for file.
//        FileContent mediaContent = new FileContent("image/jpeg", filePath);
//        try {
//            File file = service.files().create(fileMetadata, mediaContent)
//                    .setFields("id")
//                    .execute();
//            System.out.println("File ID: " + file.getId());
////            return file.getId();
//            return new ApiResponse("test", file.getName());
//        } catch (GoogleJsonResponseException e) {
//            // TODO(developer) - handle error appropriately
//            System.err.println("Unable to upload file: " + e.getDetails());
//            throw e;
//        }
//    }

}
