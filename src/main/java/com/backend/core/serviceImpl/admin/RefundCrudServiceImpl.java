package com.backend.core.serviceImpl.admin;

import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.requestdto.ListRequestDTO;
import com.backend.core.entities.requestdto.refund.RefundConfirmDTO;
import com.backend.core.entities.requestdto.refund.RefundFilterRequestDTO;
import com.backend.core.entities.tableentity.Account;
import com.backend.core.entities.tableentity.Invoice;
import com.backend.core.entities.tableentity.Refund;
import com.backend.core.enums.ErrorTypeEnum;
import com.backend.core.enums.FilterTypeEnum;
import com.backend.core.enums.RefundEnum;
import com.backend.core.repository.customQuery.CustomQueryRepository;
import com.backend.core.repository.invoice.InvoiceRepository;
import com.backend.core.repository.refund.RefundRepository;
import com.backend.core.service.CrudService;
import com.backend.core.service.GoogleDriveService;
import com.backend.core.util.process.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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


    @Override
    public ResponseEntity<ApiResponse> singleCreationalResponse(Object paramObj, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> listCreationalResponse(List<Object> objList, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> removingResponseByRequest(Object paramObj, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> removingResponseById(int id, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> updatingResponseByList(ListRequestDTO listRequestDTO, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> updatingResponseById(int id, HttpServletRequest httpRequest) {
        return null;
    }

    // todo: send notification to customer when admin refund the order
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

    @Override
    public ResponseEntity<ApiResponse> readingFromListRequest(List<Object> paramObjList, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> readingResponse(String renderType, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> readingById(int id, HttpServletRequest httpRequest) {
        return null;
    }
}
