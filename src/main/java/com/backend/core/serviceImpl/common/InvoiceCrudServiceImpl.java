package com.backend.core.serviceImpl.common;

import com.backend.core.entities.embededkey.InvoicesWithProductsPrimaryKeys;
import com.backend.core.entities.renderdto.CartRenderInfoDTO;
import com.backend.core.entities.renderdto.InvoiceDetailRenderInfoDTO;
import com.backend.core.entities.renderdto.InvoiceRenderInfoDTO;
import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.requestdto.ListRequestDTO;
import com.backend.core.entities.requestdto.invoice.InvoiceFilterRequestDTO;
import com.backend.core.entities.requestdto.invoice.OnlinePaymentReceiverDTO;
import com.backend.core.entities.requestdto.invoice.OrderProcessDTO;
import com.backend.core.entities.tableentity.Cart;
import com.backend.core.entities.tableentity.Invoice;
import com.backend.core.entities.tableentity.InvoicesWithProducts;
import com.backend.core.entities.tableentity.ProductManagement;
import com.backend.core.enums.*;
import com.backend.core.repository.cart.CartRenderInfoRepository;
import com.backend.core.repository.cart.CartRepository;
import com.backend.core.repository.customQuery.CustomQueryRepository;
import com.backend.core.repository.customer.CustomerRepository;
import com.backend.core.repository.invoice.InvoiceDetailsRenderInfoRepository;
import com.backend.core.repository.invoice.InvoiceRepository;
import com.backend.core.repository.invoice.InvoicesWithProductsRepository;
import com.backend.core.repository.product.ProductManagementRepository;
import com.backend.core.service.CrudService;
import com.backend.core.util.process.CheckUtils;
import com.backend.core.util.process.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    @Autowired
    CheckUtils checkUtils;

    @Autowired
    ValueRenderUtils valueRenderUtils;


    public InvoiceCrudServiceImpl() {
    }


    @Override
    public synchronized ResponseEntity singleCreationalResponse(Object paramObj, HttpServletRequest httpRequest) {
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(httpRequest);
        String paymentMethod = (String) paramObj;
        String responseSuccessMessage = "New order has been created successfully";
        Invoice newInvoice;

        try {
            int newInvoiceId = invoiceRepo.getLastestInvoiceId() + 1;

            if (paymentMethod.equals(PaymentMethodEnum.COD.name())) {
                newInvoice = new Invoice(
                        newInvoiceId,
                        new Date(),
                        DeliveryStatusEnum.ACCEPTANCE_WAITING.name(),
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
                responseSuccessMessage += ", please wait for Admin accept your order!";
            } else if (isOnlinePaymentMethod(paymentMethod)) {
                newInvoice = new Invoice(
                        newInvoiceId,
                        new Date(),
                        DeliveryStatusEnum.PAYMENT_WAITING.name(),
                        0,
                        paymentMethod,
                        CurrencyEnum.USD.name(),
                        "",
                        "",
                        0,
                        0,
                        "",
                        "",
                        AdminAcceptanceEnum.PAYMENT_WAITING.name(),
                        null,
                        null,
                        customerRepo.getCustomerById(customerId)
                );
                responseSuccessMessage += ", please transfer money to the given banking information for us to continue processing your order!";
            } else {
                return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.BAD_REQUEST);
            }

            createNewInvoice(newInvoice, customerId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(new ApiResponse("success", responseSuccessMessage), HttpStatus.OK);
    }


    @Override
    public ResponseEntity listCreationalResponse(List<Object> objList, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ResponseEntity removingResponseByRequest(Object paramObj, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ResponseEntity removingResponseById(int id, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ResponseEntity updatingResponseByList(ListRequestDTO listRequestDTO, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ResponseEntity updatingResponseById(int id, HttpServletRequest httpRequest) {
        Invoice invoice = new Invoice();
        String message = "Cancel order successfully, ";
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(httpRequest);

        if (id == 0) {
            return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.BAD_REQUEST);
        } else {
            try {
                invoice = invoiceRepo.getInvoiceById(id);

                // check if this customer is the owner or not
                if (customerId != invoice.getCustomer().getId()) {
                    return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.UNAUTHORIZED.name()), HttpStatus.UNAUTHORIZED);
                }

                // if online payment and shipper has not accepted the order yet -> refund 50%
                if (!invoice.getPaymentMethod().equals(PaymentMethodEnum.COD.name()) &&
                        !invoice.getDeliveryStatus().equals(DeliveryStatusEnum.CUSTOMER_CANCEL.name()) &&
                        (invoice.getDeliveryStatus().equals(DeliveryStatusEnum.SHIPPER_WAITING.name()) ||
                                invoice.getDeliveryStatus().equals(DeliveryStatusEnum.PACKING.name()) ||
                                invoice.getDeliveryStatus().equals(DeliveryStatusEnum.PAYMENT_WAITING.name()))) {
                    invoice.setRefundPercentage(50);
                    invoice.setReason("Customer cancels order before shipper takes over, refund 50%");
                    message += "you will be refunded 50% of the total order value, we will send it within 24 hours!";
                }
                // if COD payment -> error
                else if (invoice.getPaymentMethod().equals(PaymentMethodEnum.COD.name())) {
                    return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
                } else {
                    invoice.setReason("Customer cancels order, no refund");
                    message += "the shipper has already been on the way, so you will not have any refund!";
                }
                invoice.setDeliveryStatus(DeliveryStatusEnum.CUSTOMER_CANCEL.name());
                invoiceRepo.save(invoice);

            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity(new ApiResponse("success", message), HttpStatus.OK);
    }


    @Override
    public ResponseEntity updatingResponseByRequest(Object paramObj, HttpServletRequest httpRequest) {
        try {
            OrderProcessDTO orderProcess = (OrderProcessDTO) paramObj;

            String adminAction = orderProcess.getAdminAction();
            int invoiceId = orderProcess.getId();
            Invoice invoice = invoiceRepo.getInvoiceById(invoiceId);

            if (invoice == null)
                return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.NO_CONTENT);


            if ((adminAction.equals(AdminAcceptanceEnum.ACCEPTED.name()) ||
                    adminAction.equals(AdminAcceptanceEnum.REFUSED.name())) &&
                    invoice.getAdminAcceptance().equals(AdminAcceptanceEnum.WAITING.name()) &&
                    invoice.getDeliveryStatus().equals(DeliveryStatusEnum.ACCEPTANCE_WAITING.name()) &&
                    invoice.getPaymentMethod().equals(PaymentMethodEnum.COD.name())) {
                invoice.setAdminAcceptance(adminAction);
                if (adminAction.equals(AdminAcceptanceEnum.REFUSED.name())) {
                    invoice.setDeliveryStatus(DeliveryStatusEnum.NOT_SHIPPED.name());
                } else {
                    invoice.setDeliveryStatus(DeliveryStatusEnum.PACKING.name());
                }
            } else if (adminAction.equals(AdminAcceptanceEnum.CONFIRMED_ONLINE_PAYMENT.name()) &&
                    invoice.getAdminAcceptance().equals(AdminAcceptanceEnum.PAYMENT_WAITING.name()) &&
                    invoice.getDeliveryStatus().equals(DeliveryStatusEnum.ACCEPTANCE_WAITING.name()) &&
                    !invoice.getPaymentMethod().equals(PaymentMethodEnum.COD.name())) {
                invoice.setAdminAcceptance(adminAction);
                invoice.setDeliveryStatus(DeliveryStatusEnum.PACKING.name());
            } else
                return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);


            invoiceRepo.save(invoice);

            return new ResponseEntity(new ApiResponse("success", adminActionResult(adminAction)), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity readingFromSingleRequest(Object paramObj, HttpServletRequest httpRequest) {
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(httpRequest);

        List<InvoiceRenderInfoDTO> invoiceRenderList = new ArrayList<>();
        List<Invoice> invoiceList = new ArrayList<>();

        // filter orders
        if (paramObj instanceof InvoiceFilterRequestDTO invoiceFilterRequest) {
            return filterInvoice(invoiceFilterRequest, httpRequest);
        }
        // get receiver bank info for online payment
        else if (paramObj instanceof Invoice paramInvoice) {
            return getReceiverBankInfo(paramInvoice);
        }

        return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @Override
    public ResponseEntity readingFromListRequest(List<Object> paramObjList, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ResponseEntity readingResponse(String renderType, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ResponseEntity readingById(int invoiceId, HttpServletRequest httpRequest) {
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(httpRequest);

        System.out.println(customerId);

        if (!isInvoiceOwner(customerId, invoiceId)) {
            return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.UNAUTHORIZED.name()), HttpStatus.UNAUTHORIZED);
        } else {
            try {
                List<InvoiceDetailRenderInfoDTO> invoiceItemsList = invoiceDetailsRenderInfoRepo.getInvoiceItemsByInvoiceId(invoiceId);
                return new ResponseEntity(new ApiResponse("success", invoiceItemsList), HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }


    public boolean isInvoiceOwner(int customerId, int invoiceId) {
        return (invoiceRepo.getInvoiceCountByInvoiceIdAndCustomerId(invoiceId, customerId) > 0);
    }


    public boolean isOnlinePaymentMethod(String method) {
        List choices = Arrays.asList(PaymentMethodEnum.values());

        return (choices.contains(PaymentMethodEnum.MOMO.name()) ||
                choices.contains(PaymentMethodEnum.BANK_TRANSFER.name()) ||
                choices.contains(PaymentMethodEnum.PAYPAL.name()));
    }


    // filter invoices
    public ResponseEntity filterInvoice(InvoiceFilterRequestDTO invoiceFilterRequest, HttpServletRequest request) {
        try {
            String filterQuery = valueRenderUtils.getFilterQuery(invoiceFilterRequest, FilterTypeEnum.INVOICE, request);

            // get list from query
            List<InvoiceRenderInfoDTO> invoiceRenderList = customQueryRepo.getBindingFilteredList(filterQuery, InvoiceRenderInfoDTO.class);

            return new ResponseEntity(new ApiResponse("success", invoiceRenderList), HttpStatus.OK);
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    // get receiver banking info
    public ResponseEntity getReceiverBankInfo(Invoice invoice) {
        Invoice currentInvoice = invoiceRepo.getInvoiceById(invoice.getId());
        String currentPaymentMethod = invoice.getPaymentMethod();
        OnlinePaymentReceiverDTO receiver = new OnlinePaymentReceiverDTO();

        if (currentInvoice.getPaymentStatus() == 0 &&
                currentInvoice.getDeliveryStatus().equals(DeliveryStatusEnum.PAYMENT_WAITING.name()) &&
                currentInvoice.getAdminAcceptance().equals(AdminAcceptanceEnum.WAITING.name())) {
            if (currentPaymentMethod.equals(PaymentMethodEnum.PAYPAL.name())) {
                receiver = new OnlinePaymentReceiverDTO(
                        "Pay for invoice " + currentInvoice.getId(),
                        "03365672301",
                        "NGUYEN HOANG SANG",
                        "TP BANK - Tien Phong Bank",
                        currentInvoice.getTotalPrice()
                );
            } else if (currentPaymentMethod.equals(PaymentMethodEnum.MOMO.name())) {
                receiver = new OnlinePaymentReceiverDTO(
                        "Pay for invoice " + currentInvoice.getId(),
                        "0977815809",
                        "NGUYEN HOANG SANG",
                        "",
                        currentInvoice.getTotalPrice()
                );
            }
        }

        return new ResponseEntity(new ApiResponse("success", receiver), HttpStatus.OK);
    }


    // create a new invoice process
    public void createNewInvoice(Invoice newInvoice, int customerId) {
        double invoiceTotalPrice = 0;
        List<CartRenderInfoDTO> cartItemList = cartRenderInfoRepo.getSelectedCartItemListByCustomerId(customerId);

        // save new invoice first to take its ID as the foreign key for InvoicesWithProducts to progress
        invoiceRepo.save(newInvoice);

        // modify data to tables
        for (CartRenderInfoDTO item : cartItemList) {
            invoiceTotalPrice += item.getTotalPrice();

            Cart tblCart = cartRepo.getCartById(item.getId());

            // set cart item from Cart table to buying_status = BOUGHT and select_status = 0
            tblCart.setSelectStatus(0);
            tblCart.setBuyingStatus(CartEnum.BOUGHT.name());
            cartRepo.save(tblCart);

            // get ProductManagement by id, color and size
            ProductManagement pm = productManagementRepo.getProductsManagementByProductIDAndColorAndSize(
                    item.getProductId(),
                    item.getColor(),
                    item.getSize()
            );

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


    // subtract available quantity and add sold quantity of the product
    public void soldProductQuantityProcess(int productId, String productColor, String productSize, int productQuantity) {
        // get ProductManagement by id, color and size
        ProductManagement pm = productManagementRepo.getProductsManagementByProductIDAndColorAndSize(
                productId,
                productColor,
                productSize
        );

        // subtract available quantity
        pm.subtractQuantity(ProductManagementEnum.AVAILABLE_QUANTITY.name(), productQuantity);
        // add sold quantity
        pm.addQuantity(ProductManagementEnum.SOLD_QUANTITY.name(), productQuantity);

        productManagementRepo.save(pm);

    }


    // generate result message for each action
    public String adminActionResult(String adminAction) {
        switch (adminAction) {
            case "REFUSED" -> {
                return "Refused order successfully";
            }
            case "ACCEPTED" -> {
                return "Accepted order successfully and it is being packed";
            }
            case "CONFIRMED_ONLINE_PAYMENT" -> {
                return "Confirm order successfully and it is being packed";
            }
            default -> {
                return ErrorTypeEnum.NO_DATA_ERROR.name();
            }
        }
    }

}
