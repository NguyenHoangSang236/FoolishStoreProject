package com.backend.core.usecase.business.refund;

import com.backend.core.entity.account.model.Account;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.invoice.model.Invoice;
import com.backend.core.entity.notification.gateway.NotificationDTO;
import com.backend.core.entity.refund.gateway.RefundConfirmDTO;
import com.backend.core.entity.refund.model.Refund;
import com.backend.core.infrastructure.business.invoice.repository.InvoiceRepository;
import com.backend.core.infrastructure.business.refund.repository.RefundRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.service.GoogleDriveService;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.statics.RefundEnum;
import com.backend.core.usecase.service.FirebaseService;
import com.backend.core.usecase.util.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class ConfirmRefundUseCase extends UseCase<ConfirmRefundUseCase.InputValue, ApiResponse> {
    @Autowired
    InvoiceRepository invoiceRepo;
    @Autowired
    ValueRenderUtils valueRenderUtils;
    @Autowired
    GoogleDriveService googleDriveService;
    @Autowired
    RefundRepository refundRepo;
    @Autowired
    FirebaseService firebaseService;


    @Override
    public ApiResponse execute(InputValue input) {
        try {
            RefundConfirmDTO refundConfirm = input.getRefundConfirmRequest();

            Invoice invoice = invoiceRepo.getInvoiceById(refundConfirm.getInvoiceId());
            Account adminAcc = valueRenderUtils.getCurrentAccountFromRequest(input.getHttpRequest());

            // check invoice existence
            if (invoice == null) {
                return new ApiResponse("failed", ErrorTypeEnum.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST);
            }

            // check invoice is in refund list or not
            if (invoice.getRefund() == null) {
                return new ApiResponse("failed", "This invoice is not in refund list", HttpStatus.BAD_REQUEST);
            }

            // check invoice refund status can be confirmed to refund or not
            if (invoice.getRefund().getStatus().equals(RefundEnum.REFUNDED.name())) {
                return new ApiResponse("failed", "This invoice has already been refunded", HttpStatus.BAD_REQUEST);
            }

            // upload image to google drive and get the url
            String fileId = googleDriveService.uploadFile(refundConfirm.getEvidenceImage(), "Root", true);
            String ggDriveUrl = valueRenderUtils.getGoogleDriveUrlFromFileId(fileId);

            Refund refund = invoice.getRefund();
            refund.setStatus(RefundEnum.REFUNDED.name());
            refund.setDate(new Date());
            refund.setEvidentImage(ggDriveUrl);
            refund.setStaff(adminAcc.getStaff());

            refundRepo.save(refund);

            // send notification to customer
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("invoiceId", String.valueOf(invoice.getId()));

            NotificationDTO notification = NotificationDTO.builder()
                    .title("Your order's process")
                    .body("We has refunded your order, please checkout your account")
                    .data(dataMap)
                    .topic(invoice.getCustomer().getAccount().getUsername())
                    .build();

            firebaseService.sendMessage(notification);

            return new ApiResponse("success", "Refunded successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Value
    public static class InputValue implements InputValues {
        RefundConfirmDTO refundConfirmRequest;
        HttpServletRequest httpRequest;
    }
}
