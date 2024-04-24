package com.backend.core.usecase.business.google;

import com.backend.core.entity.account.model.Customer;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.infrastructure.business.account.repository.CustomerRepository;
import com.backend.core.infrastructure.config.constants.GlobalDefaultStaticVariables;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.service.GoogleDriveService;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.util.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UploadImageUseCase extends UseCase<UploadImageUseCase.InputValue, ApiResponse> {
    @Autowired
    GoogleDriveService googleDriveService;
    @Autowired
    CustomerRepository customerRepo;
    @Autowired
    ValueRenderUtils valueRenderUtils;

    @Override
    public ApiResponse execute(InputValue input) {
        String driveFolderPath = input.getDriveFolder();
        String shared = input.getIsShared();
        MultipartFile fileUpload = input.getFile();

        // Save to default folder if the user does not select a folder to save
        if (driveFolderPath.isEmpty()) {
            driveFolderPath = "Root";
        }

        if (fileUpload.isEmpty()) {
            return new ApiResponse("failed", "There is no file to upload", HttpStatus.BAD_REQUEST);
        }

        Customer customer = customerRepo.getCustomerById(valueRenderUtils.getCustomerOrStaffIdFromRequest(input.getHttpRequest()));

        // check if customer logged in or not
        if (customer == null) {
            return new ApiResponse("failed", "Login first!", HttpStatus.UNAUTHORIZED);
        } else {
            try {
                // get gg drive fileId after successfully uploading
                String fileId = googleDriveService.uploadFile(fileUpload, driveFolderPath, Boolean.parseBoolean(shared));

                // delete the old file on gg drive if the ID is different from the default user image's one
                if (!customer.getImage().equals(GlobalDefaultStaticVariables.DEFAULT_GOOGLE_DRIVE_USER_AVATAR_IMAGE)) {
                    googleDriveService.deleteFile(customer.getImage());
                }

                // set new avatar image file id to customer
                customer.setImage(fileId);
                customerRepo.save(customer);

//                return new ApiResponse("success", valueRenderUtils.getGoogleDriveUrlFromFileId(fileId));
                return new ApiResponse("success", fileId, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Value
    public static class InputValue implements InputValues {
        MultipartFile file;
        String driveFolder;
        String isShared;
        HttpServletRequest httpRequest;
    }
}
