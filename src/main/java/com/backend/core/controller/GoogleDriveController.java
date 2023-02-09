package com.backend.core.controller;

import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.entity.dto.GoogleDriveFoldersDTO;
import com.backend.core.service.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequestMapping(value = "/googleDrive",
        consumes = {"*/*"},
        produces = {MediaType.APPLICATION_JSON_VALUE} )
public class GoogleDriveController {
    @Autowired
    GoogleDriveService googleDriveService;


    // Get all file on drive
//    @GetMapping("/getAllFolders")
//    public List<GoogleDriveFoldersDTO> getAllFolder() throws IOException, GeneralSecurityException {
//
////        List<GoogleDriveFileDTO> listFile = googleDriveService.getAllFile();
//        return googleDriveService.getAllFolder();
//    }

//     Upload file to public
    @PostMapping(
            consumes = {"*/*"},
            produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ApiResponse uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload,
                                  @RequestParam("filePath") String pathFile,
                                  @RequestParam("shared") String shared) {

        System.out.println(pathFile);
        if (pathFile.equals("")){
            pathFile = "Root"; // Save to default folder if the user does not select a folder to save - you can change it
        }
        System.out.println(pathFile);
        googleDriveService.uploadFile(fileUpload, pathFile, Boolean.parseBoolean(shared));
        return new ApiResponse("test", fileUpload.getResource().getFilename());
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
}
