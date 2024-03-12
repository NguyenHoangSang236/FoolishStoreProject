package com.backend.core.usecase.service;

import com.backend.core.entity.google.gateway.GoogleDriveFileDTO;
import com.backend.core.entity.google.gateway.GoogleDriveFoldersDTO;
import com.google.api.services.drive.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import usecase.util.process.GoogleDriveUtils;
import usecase.util.process.ValueRenderUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;


@Service
public class GoogleDriveService {
    @Autowired
    GoogleDriveUtils googleDriveUtils;

    @Autowired
    ValueRenderUtils valueRenderUtils;


    public List<GoogleDriveFileDTO> getAllFile() throws IOException, GeneralSecurityException {

        List<GoogleDriveFileDTO> responseList = null;
        List<File> files = googleDriveUtils.listEverything();
        GoogleDriveFileDTO dto = null;

        if (files != null) {
            responseList = new ArrayList<>();
            for (File f : files) {
                dto = new GoogleDriveFileDTO();
                if (f.getSize() != null) {
                    dto.setId(f.getId());
                    dto.setName(f.getName());
                    dto.setThumbnailLink(f.getThumbnailLink());
                    dto.setSize(valueRenderUtils.getConvertedDataSize(f.getSize()));
                    dto.setLink("https://drive.google.com/file/d/" + f.getId() + "/view?usp=sharing");
                    dto.setShared(f.getShared());

                    responseList.add(dto);
                }
            }
        }
        return responseList;
    }


    public void deleteFile(String id) throws Exception {
        googleDriveUtils.deleteFileOrFolder(id);
    }


    public String uploadFile(MultipartFile file, String filePath, boolean isPublic) throws Exception {
        String type = "";
        String role = "";
        if (isPublic) {
            // Public file of folder for everyone
            type = "anyone";
            role = "reader";
        } else {
            // Private
            type = "private";
            role = "private";
        }
        return googleDriveUtils.uploadFile(file, filePath, type, role);
    }


    public void downloadFile(String id, OutputStream outputStream) throws IOException, GeneralSecurityException {
        googleDriveUtils.downloadFile(id, outputStream);
    }


    public List<GoogleDriveFoldersDTO> getAllFolder() throws IOException, GeneralSecurityException {
        List<File> files = googleDriveUtils.listFolderContent("root");
        List<GoogleDriveFoldersDTO> responseList = null;
        GoogleDriveFoldersDTO dto = null;

        if (files != null) {
            responseList = new ArrayList<>();
            for (File f : files) {
                dto = new GoogleDriveFoldersDTO();
                dto.setId(f.getId());
                dto.setName(f.getName());
                dto.setLink("https://drive.google.com/drive/u/3/folders/" + f.getId());
                responseList.add(dto);
            }
        }
        return responseList;
    }

    public void createFolder(String folderName) throws Exception {
        String folderId = googleDriveUtils.getFolderId(folderName);
        System.out.println(folderId);
    }

    public void deleteFolder(String id) throws Exception {
        googleDriveUtils.deleteFileOrFolder(id);
    }
}