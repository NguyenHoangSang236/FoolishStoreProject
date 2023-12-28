package com.backend.core.serviceImpl.common;

import com.backend.core.entities.embededkey.InvoicesWithProductsPrimaryKeys;
import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.requestdto.ListRequestDTO;
import com.backend.core.entities.requestdto.cart.CartCheckoutDTO;
import com.backend.core.entities.requestdto.invoice.InvoiceFilterRequestDTO;
import com.backend.core.entities.requestdto.invoice.OrderProcessDTO;
import com.backend.core.entities.requestdto.notification.NotificationDTO;
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
import com.backend.core.repository.product.ProductRepository;
import com.backend.core.repository.refund.RefundRepository;
import com.backend.core.repository.staff.StaffRepository;
import com.backend.core.service.CrudService;
import com.backend.core.util.process.CheckUtils;
import com.backend.core.util.process.FirebaseUtils;
import com.backend.core.util.process.GhnUtils;
import com.backend.core.util.process.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.*;

@Service
@Qualifier("InvoiceCrudServiceImpl")
public class InvoiceCrudServiceImpl implements CrudService {
    @Autowired
    InvoiceRepository invoiceRepo;

    @Autowired
    RefundRepository refundRepo;

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
    ProductRepository productRepo;

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

    @Autowired
    GhnUtils ghnUtils;

    @Autowired
    FirebaseUtils firebaseUtils;


    public InvoiceCrudServiceImpl() {
    }


    @Override
    public synchronized ResponseEntity<ApiResponse> singleCreationalResponse(Object paramObj, HttpServletRequest httpRequest) {
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(httpRequest);

        String responseSuccessMessage = "New order with ID --- has been created successfully";
        Invoice newInvoice;

        try {
            CartCheckoutDTO cartCheckout = (CartCheckoutDTO) paramObj;

            if (cartCheckout.getAddress().equals(null) || cartCheckout.getAddress().isBlank()) {
                return new ResponseEntity<>(new ApiResponse("failed", "Please input address"), HttpStatus.BAD_REQUEST);
            }

            List<CartRenderInfoDTO> selectedCartItemList = cartRenderInfoRepo.getSelectedCartItemListByCustomerId(customerId);

            String paymentMethod = cartCheckout.getPaymentMethod();
            double shippingFee = ghnUtils.calculateShippingFee(cartCheckout, selectedCartItemList);
            double subtotal = 0;
            int newInvoiceId = 0;
            String address = cartCheckout.getAddress();
            String note = cartCheckout.getNote();
            Integer invoiceId = invoiceRepo.getLatestInvoiceId();

            if (invoiceId != null) {
                newInvoiceId = (int) invoiceId + 1;
            }

            // calculate subtotal price
            for (CartRenderInfoDTO cartRenderInfoDTO : selectedCartItemList) {
                subtotal += cartRenderInfoDTO.getTotalPrice();
            }

            if (paymentMethod.equals(PaymentEnum.COD.name())) {
                newInvoice = new Invoice(
                        newInvoiceId,
                        new Date(),
                        PaymentEnum.UNPAID.name(),
                        InvoiceEnum.ACCEPTANCE_WAITING.name(),
                        paymentMethod,
                        CurrencyEnum.USD.name(),
                        note,
                        subtotal + shippingFee,
                        address,
                        cartCheckout.getToWardCode(),
                        cartCheckout.getToDistrictId(),
                        shippingFee,
                        AdminAcceptanceEnum.ACCEPTANCE_WAITING.name(),
                        customerRepo.getCustomerById(customerId)
                );
                responseSuccessMessage += ", please wait for Admin accept your order!";
            } else if (isOnlinePaymentMethod(paymentMethod)) {
                newInvoice = new Invoice(
                        newInvoiceId,
                        new Date(),
                        PaymentEnum.UNPAID.name(),
                        InvoiceEnum.PAYMENT_WAITING.name(),
                        paymentMethod,
                        CurrencyEnum.USD.name(),
                        note,
                        subtotal + shippingFee,
                        address,
                        cartCheckout.getToWardCode(),
                        cartCheckout.getToDistrictId(),
                        shippingFee,
                        AdminAcceptanceEnum.PAYMENT_WAITING.name(),
                        customerRepo.getCustomerById(customerId)
                );
                responseSuccessMessage += ", please transfer money to the given banking information for us to continue processing your order!";
            } else {
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.BAD_REQUEST);
            }

            boolean result = createNewInvoice(newInvoice, customerId);

            if (result) {
                return new ResponseEntity<>(new ApiResponse("success", responseSuccessMessage.replace("---", String.valueOf(newInvoiceId))), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

                // check the order status whether it can be canceled or not
                if (invoice.isUpdatable() == false) {
                    return new ResponseEntity<>(new ApiResponse("failed", "This order can not be canceled any more"), HttpStatus.BAD_REQUEST);
                }

                // check if this customer is the owner or not
                if (customerId != invoice.getCustomer().getId()) {
                    return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.UNAUTHORIZED.name()), HttpStatus.UNAUTHORIZED);
                }

                // if online payment and shipper has not accepted the order yet -> refund 50%
                if (!invoice.getPaymentMethod().equals(PaymentEnum.COD.name()) && invoice.getDelivery() == null) {
                    if (invoice.getPaymentStatus().equals(PaymentEnum.PAID.name())) {
                        invoice.setRefundPercentage(50);
                        invoice.setReason("Customer cancels order before admin create shipping order, refund 50%");
                        message += "you will be refunded 50% of the total order value, we will send it within 24 hours!";

                        Refund refund = new Refund();
                        refund.setInvoice(invoice);
                        refund.setRefundMoney(invoice.getTotalPrice() / 2);
                        refund.setStatus(RefundEnum.NOT_REFUNDED_YET.name());

                        refundRepo.save(refund);
                    } else if (invoice.getPaymentStatus().equals(PaymentEnum.UNPAID.name())) {
                        invoice.setReason("Customer cancels order before paying, no refund");
                        message += "you will not have any refund because you have not paid for this order";
                    }

                    invoice.setOrderStatus(InvoiceEnum.CUSTOMER_CANCEL.name());
                }
                // if COD payment -> no refund
                else if (invoice.getPaymentMethod().equals(PaymentEnum.COD.name())) {
                    invoice.setOrderStatus(InvoiceEnum.CUSTOMER_CANCEL.name());
                    invoice.setReason("Customer cancels COD order, no refund");
                    message += "this is COD order, so you will not have any refund!";
                }
                // if online payment and shipper has already accepted the order -> no refund
                else {
                    invoice.setOrderStatus(InvoiceEnum.CUSTOMER_CANCEL.name());
                    invoice.setReason("Customer cancels order, no refund");
                    message += "the shipper has already been on the way, so you will not have any refund!";
                }

                invoiceRepo.save(invoice);

                // send message to admin
                sendNotificationOnOrderProcess("Order Management", "A customer has canceled an order", "admin", invoice.getId());

                // retrieve product quantity from this invoice
                productQuantityProcess(InvoiceEnum.CUSTOMER_CANCEL.name(), invoice);
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

            String outOfStockProductName = invoice.getOutOfStockProduct();

            // check invoice is existed or not, and admin action from request is existed or not
            if (invoice == null || EnumUtils.findEnumInsensitiveCase(AdminAcceptanceEnum.class, adminAction) == null) {
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.NO_CONTENT);
            }

            // check if invoice product is out of stock or not
            if (outOfStockProductName != null) {
                return new ResponseEntity<>(new ApiResponse("failed", outOfStockProductName + " has been out of stock!"), HttpStatus.BAD_REQUEST);
            }

            // check if invoice can be updated or not
            if (invoice.isUpdatable() == false) {
                return new ResponseEntity<>(new ApiResponse("failed", "This order can not be updated"), HttpStatus.BAD_REQUEST);
            }

            // admin accept or refuse COD order
            if ((adminAction.equals(AdminAcceptanceEnum.ACCEPTED.name()) ||
                    adminAction.equals(AdminAcceptanceEnum.REFUSED.name())) &&
                    invoice.getAdminAcceptance().equals(AdminAcceptanceEnum.ACCEPTANCE_WAITING.name()) &&
                    invoice.getPaymentMethod().equals(PaymentEnum.COD.name())) {
                invoice.setAdminAcceptance(adminAction);
                invoice.setOrderStatus(InvoiceEnum.PACKING.name());
                invoice.setStaff(adminInCharge);

                // add sold quantity and subtract in-stock quantity of products from this invoice
                productQuantityProcess(adminAction, invoice);

                updateCartItemBuyingStatusOnAdminAcceptance(adminAction, invoice);
            }
            // admin confirm online payment
            else if (adminAction.equals(AdminAcceptanceEnum.CONFIRMED_ONLINE_PAYMENT.name()) &&
                    invoice.getAdminAcceptance().equals(AdminAcceptanceEnum.PAYMENT_WAITING.name()) &&
                    !invoice.getPaymentMethod().equals(PaymentEnum.COD.name())) {
                invoice.setAdminAcceptance(adminAction);
                invoice.setOrderStatus(InvoiceEnum.PACKING.name());
                invoice.setPaymentStatus(PaymentEnum.PAID.name());
                invoice.setStaff(adminInCharge);

                // add sold quantity and subtract in-stock quantity of products from this invoice
                productQuantityProcess(adminAction, invoice);
            }
            // admin finished pack the order
            else if (adminAction.equals(InvoiceEnum.FINISH_PACKING.name()) &&
                    (invoice.getAdminAcceptance().equals(AdminAcceptanceEnum.CONFIRMED_ONLINE_PAYMENT.name()) &&
                            invoice.getAdminAcceptance().equals((AdminAcceptanceEnum.ACCEPTED))) &&
                    !invoice.getOrderStatus().equals(InvoiceEnum.PACKING.name())) {
                // create a new GHN shipping order
                if (!createNewGhnShippingOrder(invoice)) {
                    return new ResponseEntity<>(new ApiResponse("failed", "Failed to add new GHN shipping order, please check necessary information again"), HttpStatus.BAD_REQUEST);
                }

                invoice.setOrderStatus(adminAction);
            }
            // admin confirms order is being shipped
            else if ((adminAction.equals(InvoiceEnum.SHIPPING.name())) &&
                    invoice.getOrderStatus().equals(InvoiceEnum.FINISH_PACKING.name()) &&
                    (invoice.getAdminAcceptance().equals(AdminAcceptanceEnum.ACCEPTED.name()) ||
                            invoice.getAdminAcceptance().equals(AdminAcceptanceEnum.CONFIRMED_ONLINE_PAYMENT.name()))) {
                invoice.setOrderStatus(adminAction);
            }
            // admin confirms order is SUCCESS or FAILED
            else if ((adminAction.equals(InvoiceEnum.SUCCESS.name()) || adminAction.equals(InvoiceEnum.FAILED.name())) &&
                    invoice.getOrderStatus().equals(InvoiceEnum.SHIPPING.name()) &&
                    (invoice.getAdminAcceptance().equals(AdminAcceptanceEnum.ACCEPTED.name()) ||
                            invoice.getAdminAcceptance().equals(AdminAcceptanceEnum.CONFIRMED_ONLINE_PAYMENT.name()))) {
                String reason = orderProcess.getReason();

                // FAILED must have a reason
                if (adminAction.equals(InvoiceEnum.FAILED.name()) && (reason == null || reason.isBlank())) {
                    return new ResponseEntity<>(new ApiResponse("failed", "Must have a reason for failed order"), HttpStatus.BAD_REQUEST);
                }

                if (invoice.getPaymentMethod().equals(PaymentEnum.COD.name())) {
                    invoice.setPaymentStatus(PaymentEnum.PAID.name());
                }

                invoice.setOrderStatus(adminAction);
            } else
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.BAD_REQUEST);

            invoiceRepo.save(invoice);

            // send message to customer
            sendNotificationOnOrderProcess("Your order's process", adminActionMessage(adminAction), invoice.getCustomer().getAccount().getUsername(), invoice.getId());

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
        Account currentAcc = valueRenderUtils.getCurrentAccountFromRequest(httpRequest);

        if (!isInvoiceOwnerOrAdmin(currentAcc, invoiceId)) {
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


    public boolean isInvoiceOwnerOrAdmin(Account acc, int invoiceId) {
        if (acc.getCustomer() == null && acc.getStaff() != null) {
            return true;
        } else
            return (invoiceRepo.getInvoiceCountByInvoiceIdAndCustomerId(invoiceId, acc.getCustomer().getId()) > 0);
    }


    public boolean isOnlinePaymentMethod(String method) {
        return method.equals(PaymentEnum.MOMO.name()) || method.equals(PaymentEnum.BANK_TRANSFER.name()) || method.equals(PaymentEnum.PAYPAL.name());
    }


    // filter invoices
    public ResponseEntity<ApiResponse> filterInvoice(InvoiceFilterRequestDTO invoiceFilterRequest, HttpServletRequest request) {
        try {
            if (invoiceFilterRequest.getFilter() == null) {
                return new ResponseEntity<>(new ApiResponse("failed", "Must have filter"), HttpStatus.BAD_REQUEST);
            }

            boolean isCustomer = valueRenderUtils.getCurrentAccountFromRequest(request).getRole().equals(RoleEnum.CUSTOMER.name());

            String filterQuery = valueRenderUtils.getFilterQuery(invoiceFilterRequest, FilterTypeEnum.INVOICE, request, isCustomer);

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
    public boolean createNewInvoice(Invoice newInvoice, int customerId) {
        try {
            double invoiceTotalPrice = 0;
            List<CartRenderInfoDTO> cartItemList = cartRenderInfoRepo.getSelectedCartItemListByCustomerId(customerId);

            // save new invoice first to take its ID as the foreign key for InvoicesWithProducts to progress
            invoiceRepo.save(newInvoice);

            if (cartItemList.isEmpty()) {
                return false;
            }

            // modify data to tables
            for (CartRenderInfoDTO item : cartItemList) {
                invoiceTotalPrice += item.getTotalPrice();

                Cart tblCart = cartRepo.getCartById(item.getId());

                // set cart item from Cart table to buying_status = BOUGHT and select_status = 0
                tblCart.setSelectStatus(1);
                tblCart.setBuyingStatus(CartEnum.PENDING.name());
                tblCart.setInvoice(newInvoice);
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


            // set receiver account for online payment invoice
            OnlinePaymentAccount receiverInfo = onlinePaymentAccountRepo.getOnlinePaymentAccountByType(newInvoice.getPaymentMethod());
            newInvoice.setReceiverPaymentAccount(receiverInfo);

            invoiceRepo.save(newInvoice);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
                    reason.equals(InvoiceEnum.CUSTOMER_CANCEL.name())) {
                // subtract sold quantity
                pm.subtractQuantity(ProductManagementEnum.SOLD_QUANTITY.name(), quantity);
                // add available quantity
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
            case "FINISH_PACKING" -> {
                return "Confirm order has been finished packing";
            }
            default -> {
                return ErrorTypeEnum.NO_DATA_ERROR.name();
            }
        }
    }


    // generate message for notification
    public String adminActionMessage(String adminAction) {
        switch (adminAction) {
            case "REFUSED" -> {
                return "Sorry, we have to refuse your order";
            }
            case "SUCCESS" -> {
                return "Your order has been success, have a good day!";
            }
            case "FAILED" -> {
                return "Your order has been failed";
            }
            case "SHIPPING" -> {
                return "We are shipping your order";
            }
            case "ACCEPTED" -> {
                return "Your order has been accepted";
            }
            case "CONFIRMED_ONLINE_PAYMENT" -> {
                return "We have received your payment";
            }
            case "FINISH_PACKING" -> {
                return "We have finished packing your order";
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
            System.out.println(invoiceProduct.getProductManagement().getId());
            System.out.println(invoice.getCustomer().getId());

            Cart cartItem = cartRepo.getPendingCartItemByProductManagementIdAndCustomerIdAndInvoiceId(
                    invoiceProduct.getProductManagement().getId(),
                    invoice.getCustomer().getId(),
                    invoice.getId()
            );

            cartItem.setBuyingStatus(
                    adminAcceptance.equals(AdminAcceptanceEnum.ACCEPTED.name())
                            ? CartEnum.BOUGHT.name()
                            : CartEnum.NOT_BOUGHT_YET.name()
            );

            cartRepo.save(cartItem);
        }
    }


    // create new GHN shipping order
    public boolean createNewGhnShippingOrder(Invoice invoice) {
        try {
            Delivery newDelivery = ghnUtils.getNewGhnShippingOrderCode(invoice);

            if (newDelivery != null && !newDelivery.getShippingOrderCode().isBlank()) {
                invoice.setDelivery(newDelivery);
                invoiceRepo.save(invoice);

                return true;
            } else return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // send notification about order process
    public void sendNotificationOnOrderProcess(String title, String content, String topic, int invoiceId) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("invoiceId", String.valueOf(invoiceId));

        NotificationDTO notification = NotificationDTO.builder()
                .topic(title)
                .body(content)
                .data(dataMap)
                .topic(topic)
                .build();

        firebaseUtils.sendMessage(notification);
    }
}