package com.backend.core.serviceimpl;

import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.entity.renderdto.InvoiceDetailRenderInfoDTO;
import com.backend.core.entity.tableentity.Invoice;
import com.backend.core.enums.RenderTypeEnum;
import com.backend.core.repository.InvoiceDetailsRenderInfoRepository;
import com.backend.core.repository.InvoiceRepository;
import com.backend.core.service.CrudService;
import com.backend.core.util.ValueRenderUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("InvoiceCrudServiceImpl")
public class InvoiceCrudServiceImpl implements CrudService {
    @Autowired
    InvoiceRepository invoiceRepo;

    @Autowired
    InvoiceDetailsRenderInfoRepository invoiceDetailsRenderInfoRepo;



    public InvoiceCrudServiceImpl() {}



    @Override
    public ApiResponse creationalResponse(Object paramObj, HttpSession session) {
        return null;
    }


    @Override
    public ApiResponse removingResponse(Object paramObj, HttpSession session) {
        return null;
    }


    @Override
    public ApiResponse updatingResponse(List<Object> paramObjList, HttpSession session) {
        return null;
    }


    @Override
    public ApiResponse readingFromSingleRequest(Object paramObj, HttpSession session) {
        return null;
    }


    @Override
    public ApiResponse readingFromListRequest(List<Object> paramObjList, HttpSession session) {
        return null;
    }


    @Override
    public ApiResponse readingResponse(HttpSession session, String renderType) {
        int customerId = ValueRenderUtils.getCustomerIdByHttpSession(session);

        if(customerId == 0) {
            return new ApiResponse("failed", "Login first");
        }
        else {
            try {
                List<Invoice> invoiceList = new ArrayList<>();

                switch (renderType) {
                    case "CUSTOMER_ALL_CURRENT_INVOICES": {
                        invoiceList = invoiceRepo.getAllCurrentInvoicesByCustomerId(customerId);
                        break;
                    }
                    case "CUSTOMER_PURCHASE_HISTORY": {
                        invoiceList = invoiceRepo.getAllInvoicesByCustomerId(customerId);
                        break;
                    }
                }

                return new ApiResponse("success", invoiceList);
            }
            catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("failed", "Technical error");
            }
        }
    }


    @Override
    public ApiResponse readingById(int invoiceId, HttpSession session) {
        int customerId = ValueRenderUtils.getCustomerIdByHttpSession(session);

        if(customerId == 0) {
            return new ApiResponse("failed", "Login first");
        }
        else if(!isInvoiceOwner(customerId, invoiceId)) {
            return new ApiResponse("failed", "You are not the owner of this invoice");
        }
        else {
            try {
                List<InvoiceDetailRenderInfoDTO> invoiceItemsList = invoiceDetailsRenderInfoRepo.getInvoiceItemsByInvoiceId(invoiceId);

                return new ApiResponse("success", invoiceItemsList);
            }
            catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("failed", "Technical error");
            }
        }
    }


    public boolean isInvoiceOwner(int customerId, int invoiceId) {
        return (invoiceRepo.getInvoiceCountByInvoiceIdAndCustomerId(invoiceId, customerId) > 0);
    }
}
