package com.backend.core.serviceimpl;

import com.backend.core.entity.dto.*;
import com.backend.core.entity.embededkey.InvoicesWithProductsPrimaryKeys;
import com.backend.core.entity.interfaces.FilterRequest;
import com.backend.core.entity.renderdto.CartRenderInfoDTO;
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
import java.util.Arrays;
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

    @Autowired
    CartRenderInfoRepository cartRenderInfoRepo;


    public InvoiceCrudServiceImpl() {}



    @Override
    public synchronized ApiResponse singleCreationalResponse(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        int customerId = ValueRenderUtils.getCustomerIdByHttpSession(session);
        String paymentMethod = (String) paramObj;
        List<CartRenderInfoDTO> cartItemList = new ArrayList<>();

        // check if logged in or not
        if(customerId == 0) {
            return new ApiResponse("failed", "Login first");
        }
        else {
            Invoice newInvoice = new Invoice();

            try{
                cartItemList = cartRenderInfoRepo.getSelectedCartItemListByCustomerId(customerId);
                int newInvoiceId = invoiceRepo.getLastestInvoiceId() + 1;
                double invoiceTotalPrice = 0;

                if(paymentMethod.equals(PaymentMethodEnum.COD.name())) {
                    newInvoice = new Invoice(
                            newInvoiceId,
                            new Date(),
                            DeliveryTypeEnum.ACCEPTANCE_WAITING.name(),
                            0,
                            paymentMethod,
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
                }
                else if(isPaymentMethod(paymentMethod) == true) {
                    newInvoice = new Invoice(
                            newInvoiceId,
                            new Date(),
                            DeliveryTypeEnum.PAYMENT_WAITING.name(),
                            0,
                            paymentMethod,
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
                }
                else {
                    return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
                }

                // save new invoice first to take its ID as the foreign key for InvoicesWithProducts to progress
                invoiceRepo.save(newInvoice);

                // modify data to tables
                for(CartRenderInfoDTO item : cartItemList) {
                    invoiceTotalPrice += item.getTotalPrice();

                    Cart tblCart = cartRepo.getCartById(item.getId());

                    // get ProductManagement by id, color and size
                    ProductManagement pm = productManagementRepo.getPrductsManagementByProductIDAndColorAndSize(
                            item.getProductId(),
                            item.getColor(),
                            item.getSize()
                    );

                    // set cart item from Cart table to buying_status = BOUGHT and select_status = 0
                    tblCart.setSelectStatus(0);
                    tblCart.setBuyingStatus(CartEnum.BOUGHT.name());
                    cartRepo.save(tblCart);

                    // subtract available quantity
                    pm.subtractQuantity(ProductManagementQuantityTypeEnum.AVAILABLE_QUANTITY.name(), tblCart.getQuantity());
                    // add sold quantity
                    pm.addQuantity(ProductManagementQuantityTypeEnum.SOLD_QUANTITY.name(), tblCart.getQuantity());
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

                newInvoice.setTotalPrice(invoiceTotalPrice);
                invoiceRepo.save(newInvoice);
            }
            catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
            }
        }

        return new ApiResponse("success", "New order has been created successfully");
    }



    @Override
    public ApiResponse listCreationalResponse(List<Object> objList, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ApiResponse removingResponse(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ApiResponse updatingResponse(ListRequestDTO listRequestDTO, HttpSession session, HttpServletRequest httpRequest) {
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
                    String filterQuery = ValueRenderUtils.getFilterQuery(paramObj, FilterTypeEnum.INVOICE, session);

                    // get list from query
                    invoiceRenderList = customQueryRepo.getBindingFilteredList(filterQuery, InvoiceRenderInfoDTO.class);

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
            else if(paramObj instanceof Invoice) {
                Invoice paramInvoice = (Invoice) paramObj;
                Invoice currentInvoice = invoiceRepo.getInvoiceById(paramInvoice.getId());
                String currentPaymentMethod = paramInvoice.getPaymentMethod();
                OnlinePaymentReceiverDTO receiver = new OnlinePaymentReceiverDTO();

                if(currentInvoice.getPaymentStatus() == 0 &&
                   currentInvoice.getDeliveryStatus() == DeliveryTypeEnum.PAYMENT_WAITING.name() &&
                   currentInvoice.getAdminAcceptance() == AdminAcceptanceEnum.WAITING.name())   {
                    if(currentPaymentMethod.equals(PaymentMethodEnum.PAYPAL.name())) {
                        receiver = new OnlinePaymentReceiverDTO(
                                "Pay for invoice " + currentInvoice.getId(),
                                "03365672301",
                                "NGUYEN HOANG SANG",
                                "TP BANK - Tien Phong Bank",
                                currentInvoice.getTotalPrice()
                        );
                    }
                    else if(currentPaymentMethod.equals(PaymentMethodEnum.MOMO.name())) {
                        receiver = new OnlinePaymentReceiverDTO(
                                "Pay for invoice " + currentInvoice.getId(),
                                "0977815809",
                                "NGUYEN HOANG SANG",
                                "",
                                currentInvoice.getTotalPrice()
                        );
                    }
                }



                return new ApiResponse("success", receiver);
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


    public boolean isPaymentMethod(String method) {
        List choices = Arrays.asList(PaymentMethodEnum.values());

        //compare with enum value
        if(choices.contains(PaymentMethodEnum.MOMO.name()) ||
           choices.contains(PaymentMethodEnum.BANK_TRANSFER.name()) ||
           choices.contains(PaymentMethodEnum.PAYPAL.name())){
            return true;
        }

        return false;
    }
}
