package com.backend.core.infrastructure.business.google.controller;

import com.backend.core.entity.account.model.Customer;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.infrastructure.business.account.repository.CustomerRepository;
import com.backend.core.infrastructure.config.api.ResponseMapper;
import com.backend.core.infrastructure.config.constants.GlobalDefaultStaticVariables;
import com.backend.core.infrastructure.config.google_drive.GoogleDriveConfig;
import com.backend.core.usecase.UseCaseExecutor;
import com.backend.core.usecase.service.GoogleDriveService;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.usecases.google.UploadImageUseCase;
import com.backend.core.usecase.util.process.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping(value = "/authen/googleDrive", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class GoogleDriveController {
    final UseCaseExecutor useCaseExecutor;
    final UploadImageUseCase uploadImageUseCase;


    //  Upload file to public
    @PostMapping(value = "/upLoadCustomerAvatar",
            consumes = {"*/*"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public CompletableFuture<ResponseEntity<ApiResponse>> uploadFileToGoogleDrive(@RequestParam("fileUpload") MultipartFile fileUpload,
                                                        @RequestParam("filePath") String driveFolderPath,
                                                        @RequestParam("shared") String shared,
                                                        HttpServletRequest request) {
        return useCaseExecutor.execute(
                uploadImageUseCase,
                new UploadImageUseCase.InputValue(fileUpload, driveFolderPath, shared, request),
                ResponseMapper::map
        );
    }


// Get all file on drive
//    @GetMapping("/getAllFolders")
//    public List<GoogleDriveFoldersDTO> getAllFolder() throws IOException, GeneralSecurityException {
//
////        List<GoogleDriveFileDTO> listFile = googleDriveService.getAllFile();
//        return googleDriveService.getAllFolder();
//    }
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
