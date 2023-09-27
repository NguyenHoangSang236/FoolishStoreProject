package com.backend.core.serviceImpl.common;

import com.backend.core.entities.embededkey.InvoicesWithProductsPrimaryKeys;
import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.requestdto.ListRequestDTO;
import com.backend.core.entities.requestdto.invoice.InvoiceFilterRequestDTO;
import com.backend.core.entities.requestdto.invoice.OrderProcessDTO;
import com.backend.core.entities.responsedto.CartRenderInfoDTO;
import com.backend.core.entities.responsedto.InvoiceDetailRenderInfoDTO;
import com.backend.core.entities.responsedto.InvoiceRenderInfoDTO;
import com.backend.core.entities.responsedto.OnlinePaymentInfoDTO;
import com.backend.core.entities.tableentity.*;
import com.backend.core.enums.*;
import com.backend.core.repository.cart.CartRenderInfoRepository;
import com.backend.core.repository.cart.CartRepository;
import com.backend.core.repository.customQuery.CustomQueryRepository;
import com.backend.core.repository.customer.CustomerRepository;
import com.backend.core.repository.invoice.InvoiceDetailsRenderInfoRepository;
import com.backend.core.repository.invoice.InvoiceRepository;
import com.backend.core.repository.invoice.InvoicesWithProductsRepository;
import com.backend.core.repository.onlinePayment.OnlinePaymentAccountRepository;
import com.backend.core.repository.product.ProductManagementRepository;
import com.backend.core.repository.staff.StaffRepository;
import com.backend.core.service.CrudService;
import com.backend.core.util.process.CheckUtils;
import com.backend.core.util.process.ValueRenderUtils;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    StaffRepository staffRepo;

    @Autowired
    CartRenderInfoRepository cartRenderInfoRepo;

    @Autowired
    OnlinePaymentAccountRepository onlinePaymentAccountRepo;

    @Autowired
    CheckUtils checkUtils;

    @Autowired
    ValueRenderUtils valueRenderUtils;


    public InvoiceCrudServiceImpl() {
    }


    @Override
    public synchronized ResponseEntity<ApiResponse> singleCreationalResponse(Object paramObj, HttpServletRequest httpRequest) {
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(httpRequest);
        Gson gson = new Gson();
        Map<String, String> request = gson.fromJson((String) paramObj, Map.class);
        String paymentMethod = request.get("paymentMethod");
        String deliveryType = request.get("deliveryType");
        String responseSuccessMessage = "New order with ID --- has been created successfully";
        Invoice newInvoice;

        try {
            if (!deliveryType.equals(DeliveryEnum.EXPRESS_DELIVERY.name()) && !deliveryType.equals(DeliveryEnum.NORMAL_DELIVERY.name())) {
                return new ResponseEntity<>(new ApiResponse("failed", "This delivery type does not existed"), HttpStatus.BAD_REQUEST);
            }

            int newInvoiceId = invoiceRepo.getLastestInvoiceId() + 1;

            if (paymentMethod.equals(PaymentEnum.COD.name())) {
                newInvoice = new Invoice(
                        newInvoiceId,
                        new Date(),
                        DeliveryEnum.ACCEPTANCE_WAITING.name(),
                        deliveryType,
                        PaymentEnum.UNPAID.name(),
                        paymentMethod,
                        CurrencyEnum.USD.name(),
                        "",
                        "",
                        0,
                        0,
                        "",
                        "",
                        AdminAcceptanceEnum.ACCEPTANCE_WAITING.name(),
                        null,
                        null,
                        customerRepo.getCustomerById(customerId)
                );
                responseSuccessMessage += ", please wait for Admin accept your order!";
            } else if (isOnlinePaymentMethod(paymentMethod)) {
                newInvoice = new Invoice(
                        newInvoiceId,
                        new Date(),
                        DeliveryEnum.PAYMENT_WAITING.name(),
                        deliveryType,
                        PaymentEnum.UNPAID.name(),
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
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.BAD_REQUEST);
            }

            createNewInvoice(newInvoice, customerId);

            responseSuccessMessage = responseSuccessMessage.replace("---", String.valueOf(newInvoiceId));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new ApiResponse("success", responseSuccessMessage), HttpStatus.OK);
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
        Invoice invoice = new Invoice();
        String message = "Cancel order successfully, ";
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(httpRequest);

        if (id == 0) {
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.BAD_REQUEST);
        } else {
            try {
                invoice = invoiceRepo.getInvoiceById(id);

                // check if this invoice exists or not
                if (invoice == null) {
                    return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.BAD_REQUEST);
                }

                // check if this customer is the owner or not
                if (customerId != invoice.getCustomer().getId()) {
                    return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.UNAUTHORIZED.name()), HttpStatus.UNAUTHORIZED);
                }

                // if online payment and shipper has not accepted the order yet -> refund 50%
                if (!invoice.getPaymentMethod().equals(PaymentEnum.COD.name()) &&
                        !invoice.getDeliveryStatus().equals(DeliveryEnum.FAILED.name()) &&
                        (invoice.getDeliveryStatus().equals(DeliveryEnum.SHIPPER_WAITING.name()) ||
                                invoice.getDeliveryStatus().equals(DeliveryEnum.PACKING.name()) ||
                                invoice.getDeliveryStatus().equals(DeliveryEnum.PAYMENT_WAITING.name()))) {
                    invoice.setRefundPercentage(50);
                    invoice.setReason("Customer cancels order before shipper takes over, refund 50%");
                    message += "you will be refunded 50% of the total order value, we will send it within 24 hours!";
                }
                // if COD payment -> error
                else if (invoice.getPaymentMethod().equals(PaymentEnum.COD.name())) {
                    return new ResponseEntity<>(new ApiResponse("failed", "Can not cancel this order"), HttpStatus.BAD_REQUEST);
                } else {
                    invoice.setReason("Customer cancels order, no refund");
                    message += "the shipper has already been on the way, so you will not have any refund!";
                }
                invoice.setDeliveryStatus(DeliveryEnum.CUSTOMER_CANCEL.name());
                invoiceRepo.save(invoice);

                // retrieve product quantity from this invoice
                productQuantityProcess(DeliveryEnum.CUSTOMER_CANCEL.name(), invoice);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>(new ApiResponse("success", message), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<ApiResponse> updatingResponseByRequest(Object paramObj, HttpServletRequest httpRequest) {
        try {
            OrderProcessDTO orderProcess = (OrderProcessDTO) paramObj;
            int adminId = valueRenderUtils.getCustomerOrStaffIdFromRequest(httpRequest);
            Staff adminInCharge = staffRepo.getStaffById(adminId);

            String adminAction = orderProcess.getAdminAction();
            int invoiceId = orderProcess.getId();
            Invoice invoice = invoiceRepo.getInvoiceById(invoiceId);

            if (invoice == null || EnumUtils.findEnumInsensitiveCase(AdminAcceptanceEnum.class, adminAction) == null)
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.NO_CONTENT);

            if ((adminAction.equals(AdminAcceptanceEnum.ACCEPTED.name()) ||
                    adminAction.equals(AdminAcceptanceEnum.REFUSED.name())) &&
                    invoice.getAdminAcceptance().equals(AdminAcceptanceEnum.ACCEPTANCE_WAITING.name()) &&
                    invoice.getDeliveryStatus().equals(DeliveryEnum.ACCEPTANCE_WAITING.name()) &&
                    invoice.getPaymentMethod().equals(PaymentEnum.COD.name())) {
                invoice.setAdminAcceptance(adminAction);
                invoice.setStaff(adminInCharge);

                if (adminAction.equals(AdminAcceptanceEnum.REFUSED.name())) {
                    invoice.setDeliveryStatus(DeliveryEnum.NOT_SHIPPED.name());
                } else {
                    invoice.setDeliveryStatus(DeliveryEnum.PACKING.name());
                    // add sold quantity and subtract in-stock quantity of products from this invoice
                    productQuantityProcess(adminAction, invoice);
                }

                updateCartItemBuyingStatusOnAdminAcceptance(adminAction, invoice);
            } else if (adminAction.equals(AdminAcceptanceEnum.CONFIRMED_ONLINE_PAYMENT.name()) &&
                    invoice.getAdminAcceptance().equals(AdminAcceptanceEnum.PAYMENT_WAITING.name()) &&
                    invoice.getDeliveryStatus().equals(DeliveryEnum.ACCEPTANCE_WAITING.name()) &&
                    !invoice.getPaymentMethod().equals(PaymentEnum.COD.name())) {
                invoice.setAdminAcceptance(adminAction);
                invoice.setDeliveryStatus(DeliveryEnum.PACKING.name());
                // add sold quantity and subtract in-stock quantity of products from this invoice
                productQuantityProcess(adminAction, invoice);
            } else if (adminAction.equals(AdminAcceptanceEnum.FINISH_PACKING.name()) &&
                    invoice.getStaff().getId() == adminId &&
                    invoice.getDeliveryStatus().equals(DeliveryEnum.PACKING.name()) &&
                    invoice.getAdminAcceptance().equals(AdminAcceptanceEnum.ACCEPTED.name())) {
                invoice.setDeliveryStatus(DeliveryEnum.SHIPPER_WAITING.name());
            } else
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.BAD_REQUEST);

            invoiceRepo.save(invoice);

            return new ResponseEntity<>(new ApiResponse("success", adminActionResult(adminAction)), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<ApiResponse> readingFromSingleRequest(Object paramObj, HttpServletRequest httpRequest) {
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

        return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.BAD_REQUEST);

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
    public ResponseEntity<ApiResponse> readingById(int invoiceId, HttpServletRequest httpRequest) {
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(httpRequest);

        System.out.println(customerId);

        if (!isInvoiceOwner(customerId, invoiceId)) {
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.UNAUTHORIZED.name()), HttpStatus.UNAUTHORIZED);
        } else {
            try {
                List<InvoiceDetailRenderInfoDTO> invoiceItemsList = invoiceDetailsRenderInfoRepo.getInvoiceItemsByInvoiceId(invoiceId);
                return new ResponseEntity<>(new ApiResponse("success", invoiceItemsList), HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }


    public boolean isInvoiceOwner(int customerId, int invoiceId) {
        return (invoiceRepo.getInvoiceCountByInvoiceIdAndCustomerId(invoiceId, customerId) > 0);
    }


    public boolean isOnlinePaymentMethod(String method) {
        return method.equals(PaymentEnum.MOMO.name()) || method.equals(PaymentEnum.BANK_TRANSFER.name()) || method.equals(PaymentEnum.PAYPAL.name());
    }


    // filter invoices
    public ResponseEntity<ApiResponse> filterInvoice(InvoiceFilterRequestDTO invoiceFilterRequest, HttpServletRequest request) {
        try {
            String filterQuery = valueRenderUtils.getFilterQuery(invoiceFilterRequest, FilterTypeEnum.INVOICE, request, true);

            // get list from query
            List<InvoiceRenderInfoDTO> invoiceRenderList = customQueryRepo.getBindingFilteredList(filterQuery, InvoiceRenderInfoDTO.class);

            return new ResponseEntity<>(new ApiResponse("success", invoiceRenderList), HttpStatus.OK);
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    // get receiver banking info
    public ResponseEntity<ApiResponse> getReceiverBankInfo(Invoice invoice) {
        Invoice currentInvoice = invoiceRepo.getInvoiceById(invoice.getId());
        String currentPaymentMethod = invoice.getPaymentMethod();
        OnlinePaymentAccount receiverInfo = onlinePaymentAccountRepo.getOnlinePaymentAccountByType(currentPaymentMethod);
        OnlinePaymentInfoDTO receiver = new OnlinePaymentInfoDTO();

        if (currentInvoice.getPaymentStatus().equals(PaymentEnum.UNPAID.name()) &&
                currentInvoice.getDeliveryStatus().equals(DeliveryEnum.PAYMENT_WAITING.name()) &&
                currentInvoice.getAdminAcceptance().equals(AdminAcceptanceEnum.PAYMENT_WAITING.name())) {
            receiver = new OnlinePaymentInfoDTO(
                    "Pay for invoice " + invoice.getId(),
                    currentInvoice.getTotalPrice(),
                    receiverInfo
            );

            return new ResponseEntity<>(new ApiResponse("success", receiver), HttpStatus.OK);
        } else
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.BAD_REQUEST);
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
            tblCart.setSelectStatus(1);
            tblCart.setBuyingStatus(CartEnum.PENDING.name());
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
    public void productQuantityProcess(String reason, Invoice invoice) {
        List<InvoicesWithProducts> invoiceProductList = invoice.getInvoicesWithProducts();

        for (InvoicesWithProducts invoiceProduct : invoiceProductList) {
            ProductManagement pm = invoiceProduct.getProductManagement();
            int quantity = invoiceProduct.getQuantity();

            if (reason.equals(AdminAcceptanceEnum.ACCEPTED.name()) ||
                    reason.equals(AdminAcceptanceEnum.CONFIRMED_ONLINE_PAYMENT.name())) {
                // subtract available quantity
                pm.subtractQuantity(ProductManagementEnum.AVAILABLE_QUANTITY.name(), quantity);
                // add sold quantity
                pm.addQuantity(ProductManagementEnum.SOLD_QUANTITY.name(), quantity);
            } else if (reason.equals(AdminAcceptanceEnum.REFUSED.name()) ||
                    reason.equals(DeliveryEnum.CUSTOMER_CANCEL.name())) {
                // subtract available quantity
                pm.subtractQuantity(ProductManagementEnum.SOLD_QUANTITY.name(), quantity);
                // add sold quantity
                pm.addQuantity(ProductManagementEnum.AVAILABLE_QUANTITY.name(), quantity);
            }

            productManagementRepo.save(pm);
        }
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


    // update buying status of cart item when admin accept or refuse the order
    public void updateCartItemBuyingStatusOnAdminAcceptance(String adminAcceptance, Invoice invoice) {
        List<InvoicesWithProducts> invoiceProductList = invoice.getInvoicesWithProducts();

        for (InvoicesWithProducts invoiceProduct : invoiceProductList) {
            Cart cartItem = cartRepo.getCartItemByProductManagementIdAndCustomerId(
                    invoiceProduct.getProductManagement().getId(),
                    invoice.getCustomer().getId()
            );

            cartItem.setBuyingStatus(
                    adminAcceptance.equals(AdminAcceptanceEnum.ACCEPTED.name())
                            ? CartEnum.BOUGHT.name()
                            : CartEnum.NOT_BOUGHT_YET.name()
            );
            cartRepo.save(cartItem);
        }
    }
}
