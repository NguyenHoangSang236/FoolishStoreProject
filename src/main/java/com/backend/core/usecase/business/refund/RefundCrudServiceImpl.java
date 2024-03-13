package com.backend.core.usecase.business.refund;

import com.backend.core.entity.account.model.Account;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.invoice.model.Invoice;
import com.backend.core.entity.notification.gateway.NotificationDTO;
import com.backend.core.entity.refund.gateway.RefundConfirmDTO;
import com.backend.core.entity.refund.gateway.RefundFilterRequestDTO;
import com.backend.core.entity.refund.model.Refund;
import com.backend.core.infrastructure.business.invoice.repository.InvoiceRepository;
import com.backend.core.infrastructure.business.refund.repository.RefundRepository;
import com.backend.core.infrastructure.config.database.CustomQueryRepository;
import com.backend.core.usecase.service.CrudService;
import com.backend.core.usecase.service.GoogleDriveService;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.statics.FilterTypeEnum;
import com.backend.core.usecase.statics.RefundEnum;
import com.backend.core.usecase.util.process.FirebaseUtils;
import com.backend.core.usecase.util.process.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Qualifier("RefundCrudServiceImpl")
public class RefundCrudServiceImpl implements CrudService {
    @Autowired
    CustomQueryRepository customQueryRepo;

    @Autowired
    InvoiceRepository invoiceRepo;

    @Autowired
    RefundRepository refundRepo;

    @Autowired
    ValueRenderUtils valueRenderUtils;

    @Autowired
    GoogleDriveService googleDriveService;

    @Autowired
    FirebaseUtils firebaseUtils;


    @Override
    public ResponseEntity<ApiResponse> updatingResponseByRequest(Object paramObj, HttpServletRequest httpRequest) {
        try {
            RefundConfirmDTO refundConfirm = (RefundConfirmDTO) paramObj;

            Invoice invoice = invoiceRepo.getInvoiceById(refundConfirm.getInvoiceId());
            Account adminAcc = valueRenderUtils.getCurrentAccountFromRequest(httpRequest);

            // check invoice existence
            if (invoice == null) {
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.BAD_REQUEST);
            }

            // check invoice is in refund list or not
            if (invoice.getRefund() == null) {
                return new ResponseEntity<>(new ApiResponse("failed", "This invoice is not in refund list"), HttpStatus.BAD_REQUEST);
            }

            // check invoice refund status can be confirmed to refund or not
            if (invoice.getRefund().getStatus().equals(RefundEnum.REFUNDED.name())) {
                return new ResponseEntity<>(new ApiResponse("failed", "This invoice has already been refunded"), HttpStatus.BAD_REQUEST);
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

            firebaseUtils.sendMessage(notification);

            return new ResponseEntity<>(new ApiResponse("success", "Refunded successfully"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> readingFromSingleRequest(Object paramObj, HttpServletRequest httpRequest) {
        try {
            if (paramObj instanceof RefundFilterRequestDTO) {
                RefundFilterRequestDTO refundFilterRequest = (RefundFilterRequestDTO) paramObj;

                String filterQuery = valueRenderUtils.getFilterQuery(refundFilterRequest, FilterTypeEnum.REFUND, httpRequest, false);

                List<Refund> refundList = customQueryRepo.getBindingFilteredList(filterQuery, Refund.class);

                return new ResponseEntity<>(new ApiResponse("success", refundList), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
