package com.backend.core.serviceimpl;

import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.entity.dto.FilterFactory;
import com.backend.core.entity.dto.InvoiceFilterDTO;
import com.backend.core.entity.dto.InvoiceFilterRequestDTO;
import com.backend.core.entity.interfaces.FilterRequest;
import com.backend.core.entity.renderdto.InvoiceDetailRenderInfoDTO;
import com.backend.core.entity.renderdto.InvoiceRenderInfoDTO;
import com.backend.core.entity.renderdto.ProductRenderInfoDTO;
import com.backend.core.entity.tableentity.Invoice;
import com.backend.core.enums.ErrorTypeEnum;
import com.backend.core.enums.FilterTypeEnum;
import com.backend.core.repository.CustomQueryRepository;
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

    @Autowired
    CustomQueryRepository customQueryRepo;



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
        int customerId = ValueRenderUtils.getCustomerIdByHttpSession(session);

        List<InvoiceRenderInfoDTO> invoiceRenderList = new ArrayList<>();

        // check if logged in or not
        if(customerId == 0) {
            return new ApiResponse("failed", "Login first");
        }
        else {
            try {
                // determine filter type
                FilterRequest invoiceFilterRequest = FilterFactory.getFilterRequest(FilterTypeEnum.INVOICE);

                // convert paramObj
                invoiceFilterRequest = (InvoiceFilterRequestDTO) paramObj;

                InvoiceFilterDTO invoiceFilter = (InvoiceFilterDTO) invoiceFilterRequest.getFilter();

                // create query for filter
                String query = ValueRenderUtils.invoiceFilterQuery(
                        customerId,
                        invoiceFilter.getAdminAcceptance(),
                        invoiceFilter.getPaymentStatus(),
                        invoiceFilter.getDeliveryStatus(),
                        invoiceFilter.getStartInvoiceDate(),
                        invoiceFilter.getEndInvoiceDate(),
                        invoiceFilter.getPaymentMethod()
                );

                // get object[] list from query
                List<Object[]> objList = customQueryRepo.getBindingFilteredList(query);

                // convert object[] list to ProductRenderInfoDTO list
                invoiceRenderList = objList.stream().map(
                        obj -> new InvoiceRenderInfoDTO(
                                obj[0] instanceof Long ? ((Long) obj[0]).intValue() : (int) obj[0],
                                obj[1] instanceof Long ? ((Long) obj[1]).intValue() : (int) obj[1],
                                (java.util.Date) obj[2],
                                (Byte) obj[3],
                                (String) obj[4],
                                (double) obj[5],
                                (String) obj[6],
                                (String) obj[7],
                                (String) obj[8],
                                (String) obj[9],
                                (String) obj[10],
                                (String) obj[11],
                                (double) obj[12],
                                (String) obj[13]
                        )
                ).toList();

            }
            catch (StringIndexOutOfBoundsException e) {
                e.printStackTrace();
                return new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name());
            }
            catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
            }
        }

        return new ApiResponse("success", invoiceRenderList);
    }


    @Override
    public ApiResponse readingFromListRequest(List<Object> paramObjList, HttpSession session) {
        return null;
    }


    @Override
    public ApiResponse readingResponse(HttpSession session, String renderType) {
        int customerId = ValueRenderUtils.getCustomerIdByHttpSession(session);

        // check if logged in or not
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
                return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
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
                return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
            }
        }
    }


    public boolean isInvoiceOwner(int customerId, int invoiceId) {
        return (invoiceRepo.getInvoiceCountByInvoiceIdAndCustomerId(invoiceId, customerId) > 0);
    }
}
