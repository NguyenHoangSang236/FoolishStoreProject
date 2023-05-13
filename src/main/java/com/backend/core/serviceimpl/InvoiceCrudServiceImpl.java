package com.backend.core.serviceimpl;

import com.backend.core.entity.dto.*;
import com.backend.core.entity.embededkey.InvoicesWithProductsPrimaryKeys;
import com.backend.core.entity.interfaces.FilterRequest;
import com.backend.core.entity.renderdto.InvoiceDetailRenderInfoDTO;
import com.backend.core.entity.renderdto.InvoiceRenderInfoDTO;
import com.backend.core.entity.tableentity.Invoice;
import com.backend.core.entity.tableentity.Cart;
import com.backend.core.entity.tableentity.ProductManagement;
import com.backend.core.enums.*;
import com.backend.core.repository.*;
import com.backend.core.service.CrudService;
import com.backend.core.util.ValueRenderUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    ProductManagementRepository productManagementRepo;

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    InvoicesWithProductsRepository invoicesWithProductsRepo;

    @Autowired
    CartRepository cartRepo;


    public InvoiceCrudServiceImpl() {}



    @Override
    public ApiResponse singleCreationalResponse(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }



    @Override
    public ApiResponse listCreationalResponse(List<Object> objList, HttpSession session, HttpServletRequest httpRequest) {
        int customerId = ValueRenderUtils.getCustomerIdByHttpSession(session);

        List<CartItemDTO> cartItemList = new ArrayList<CartItemDTO>();

        // check if logged in or not
        if(customerId == 0) {
            return new ApiResponse("failed", "Login first");
        }
        else {
            int newInvoiceId = invoiceRepo.getLastestInvoiceId() + 1;

            Invoice newInvoice = new Invoice(
                    newInvoiceId,
                    new Date(),
                    DeliveryTypeEnum.ACCEPTANCE_WAITING.name(),
                    0,
                    PaymentMethodEnum.COD.name(),
                    CurrencyEnum.USD.name(),
                    "",
                    "",
                    0,
                    0,
                    "",
                    "",
                    AdminAcceptanceEnum.WAITING.name(),
                    null,
                    null,
                    customerRepo.getCustomerById(customerId)
            );

            try {
                if(objList.size() > 0) {
                    // check if all cart items meet the requirements or not
                    for(Object elem : objList) {
                        CartItemDTO item = new ObjectMapper().convertValue(elem, CartItemDTO.class);
                        cartItemList.add(item);

                        Cart cartItem = cartRepo.getCartById(item.getCartId());

                        // check if this cart item is in your cart or not
                        if(cartItem.getCustomer().getId() != customerId ||
                           cartItem.getBuyingStatus().equals(CartBuyingStatusEnum.NOT_BOUGHT_YET.name())) {
                            return new ApiResponse("failed", "This item is not in your cart");
                        }

                        // check if this cart item's quantity is higher than available quantity or not
                        if(cartItem.getProductManagement().getAvailableQuantity() < item.getQuantity()) {
                            return new ApiResponse("failed", "The available quantity of " + cartItem.getProductManagement().getProduct().getName() + " is only " + cartItem.getProductManagement().getAvailableQuantity() + " left");
                        }
                    }

                    // save new invoice first to take its ID as the foreign key for InvoicesWithProducts to progress
                    invoiceRepo.save(newInvoice);

                    // insert data to tables
                    for(CartItemDTO item : cartItemList) {
                        Cart cartItem = cartRepo.getCartById(item.getCartId());

                        // get ProductManagement by id, color and size
                        ProductManagement pm = productManagementRepo.getPrductsManagementByProductIDAndColorAndSize(
                                item.getProductId(),
                                item.getColor(),
                                item.getSize()
                        );

                        // subtract available quantity
                        pm.subtractQuantity(ProductManagementQuantityTypeEnum.AVAILABLE_QUANTITY.name(), cartItem.getQuantity());
                        // add sold quantity
                        pm.addQuantity(ProductManagementQuantityTypeEnum.SOLD_QUANTITY.name(), cartItem.getQuantity());
                        productManagementRepo.save(pm);

                        InvoicesWithProducts invoicesWithProducts = new InvoicesWithProducts(
                                new InvoicesWithProductsPrimaryKeys(pm.getId(), newInvoice.getId()),
                                pm,
                                newInvoice,
                                item.getQuantity()
                        );

                        // insert to InvoicesWithProducts table
                        invoicesWithProductsRepo.insertInvoicesWithProducts(invoicesWithProducts);
                    }

                    return new ApiResponse("success", "Add new order successfully");
                }
                else return new ApiResponse("failed", "There is no item");
            }
            catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
            }
        }
    }


    @Override
    public ApiResponse removingResponse(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ApiResponse updatingResponse(List<Object> paramObjList, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ApiResponse readingFromSingleRequest(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        int customerId = ValueRenderUtils.getCustomerIdByHttpSession(session);

        List<InvoiceRenderInfoDTO> invoiceRenderList = new ArrayList<>();
        List<Invoice> invoiceList = new ArrayList<>();

        // check if logged in or not
        if(customerId == 0) {
            return new ApiResponse("failed", "Login first");
        }
        else {
            if(paramObj instanceof InvoiceFilterRequestDTO) {
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
                            invoiceFilter.getPaymentMethod(),
                            invoiceFilterRequest.getPagination().getPage(),
                            invoiceFilterRequest.getPagination().getLimit()
                    );

                    // get object[] list from query
                    List<Object[]> objList = customQueryRepo.getBindingFilteredList(query);

                    // convert object[] list to InvoiceRenderInfoDTO list
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

                    return new ApiResponse("success", invoiceRenderList);
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
            else if(paramObj instanceof PaginationDTO) {
                try {
                    PaginationDTO pagination = (PaginationDTO) paramObj;

                    switch (pagination.getType()) {
                        case "ALL_CURRENT_INVOICES" -> {
                            invoiceList = invoiceRepo.getAllCurrentInvoicesByCustomerId(
                                    customerId,
                                    (pagination.getPage() - 1) * pagination.getLimit(),
                                    pagination.getLimit()
                            );
                        }
                        case "INVOICE_PURCHASE_HISTORY" -> {
                            invoiceList = invoiceRepo.getAllInvoicesByCustomerId(
                                    customerId,
                                    (pagination.getPage() - 1) * pagination.getLimit(),
                                    pagination.getLimit()
                            );
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

        return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
    }


    @Override
    public ApiResponse readingFromListRequest(List<Object> paramObjList, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ApiResponse readingResponse(HttpSession session, String renderType, HttpServletRequest httpRequest) {
//        int customerId = ValueRenderUtils.getCustomerIdByHttpSession(session);
//
//        // check if logged in or not
//        if(customerId == 0) {
//            return new ApiResponse("failed", "Login first");
//        }
//        else {
//            try {
//                List<Invoice> invoiceList = new ArrayList<>();
//
//                switch (renderType) {
//                    case "CUSTOMER_ALL_CURRENT_INVOICES": {
//                        invoiceList = invoiceRepo.getAllCurrentInvoicesByCustomerId(customerId);
//                        break;
//                    }
//                    case "CUSTOMER_PURCHASE_HISTORY": {
//                        invoiceList = invoiceRepo.getAllInvoicesByCustomerId(customerId);
//                        break;
//                    }
//                }
//
//                return new ApiResponse("success", invoiceList);
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//                return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
//            }
//        }
        return null;
    }


    @Override
    public ApiResponse readingById(int invoiceId, HttpSession session, HttpServletRequest httpRequest) {
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
