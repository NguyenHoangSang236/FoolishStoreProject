package com.backend.core.usecase.business.invoice;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.cart.gateway.CartCheckoutDTO;
import com.backend.core.entity.cart.model.Cart;
import com.backend.core.entity.invoice.key.InvoicesWithProductsPrimaryKeys;
import com.backend.core.entity.invoice.model.Invoice;
import com.backend.core.entity.invoice.model.InvoicesWithProducts;
import com.backend.core.entity.invoice.model.OnlinePaymentAccount;
import com.backend.core.entity.notification.gateway.NotificationDTO;
import com.backend.core.entity.product.model.ProductManagement;
import com.backend.core.infrastructure.business.account.repository.CustomerRepository;
import com.backend.core.infrastructure.business.cart.dto.CartRenderInfoDTO;
import com.backend.core.infrastructure.business.cart.repository.CartRenderInfoRepository;
import com.backend.core.infrastructure.business.cart.repository.CartRepository;
import com.backend.core.infrastructure.business.invoice.repository.InvoiceRepository;
import com.backend.core.infrastructure.business.invoice.repository.InvoicesWithProductsRepository;
import com.backend.core.infrastructure.business.online_payment.repository.OnlinePaymentAccountRepository;
import com.backend.core.infrastructure.business.product.repository.ProductManagementRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.*;
import com.backend.core.usecase.util.process.FirebaseUtils;
import com.backend.core.usecase.util.process.GhnUtils;
import com.backend.core.usecase.util.process.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AddNewOrderUseCase extends UseCase<AddNewOrderUseCase.InputValue, ApiResponse> {
    @Autowired
    ValueRenderUtils valueRenderUtils;
    @Autowired
    FirebaseUtils firebaseUtils;
    @Autowired
    CartRenderInfoRepository cartRenderInfoRepo;
    @Autowired
    GhnUtils ghnUtils;
    @Autowired
    InvoiceRepository invoiceRepo;
    @Autowired
    CustomerRepository customerRepo;
    @Autowired
    CartRepository cartRepo;
    @Autowired
    InvoicesWithProductsRepository invoicesWithProductsRepo;
    @Autowired
    OnlinePaymentAccountRepository onlinePaymentAccountRepo;
    @Autowired
    ProductManagementRepository productManagementRepo;


    @Override
    public synchronized ApiResponse execute(InputValue input) {
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(input.getHttpRequest());

        String responseSuccessMessage = "New order with ID --- has been created successfully";
        Invoice newInvoice;

        CartCheckoutDTO cartCheckout = input.getCartCheckout();

        if (cartCheckout.getAddress().equals(null) || cartCheckout.getAddress().isBlank()) {
            return new ApiResponse("failed", "Please input address", HttpStatus.BAD_REQUEST);
        }

        List<CartRenderInfoDTO> selectedCartItemList = cartRenderInfoRepo.getSelectedCartItemListByCustomerId(customerId);

        String paymentMethod = cartCheckout.getPaymentMethod();
        double shippingFee = ghnUtils.calculateShippingFee(cartCheckout, selectedCartItemList);
        double subtotal = 0;
        int newInvoiceId = 1;
        String address = cartCheckout.getAddress();
        String note = cartCheckout.getNote();
        Integer invoiceId = invoiceRepo.getLatestInvoiceId();

        if (invoiceId != null) {
            newInvoiceId = invoiceId + 1;
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
            return new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name(), HttpStatus.BAD_REQUEST);
        }

        boolean result = createNewInvoice(newInvoice, customerId);

        if (result) {
            sendNotificationOnOrderProcess("New order", "A new order has been created", "admin", newInvoice);

            return new ApiResponse("success", responseSuccessMessage.replace("---", String.valueOf(newInvoiceId)), HttpStatus.OK);
        } else {
            return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean isOnlinePaymentMethod(String method) {
        return method.equals(PaymentEnum.MOMO.name()) || method.equals(PaymentEnum.BANK_TRANSFER.name()) || method.equals(PaymentEnum.PAYPAL.name());
    }

    public boolean createNewInvoice(Invoice newInvoice, int customerId) {
        try {
            double invoiceTotalPrice = 0;
            List<CartRenderInfoDTO> cartItemList = cartRenderInfoRepo.getSelectedCartItemListByCustomerId(customerId);
            List<Cart> cartList = new ArrayList<>();
            List<InvoicesWithProducts> invoiceItemList = new ArrayList<>();

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
                cartList.add(tblCart);
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
                invoiceItemList.add(invoicesWithProducts);
                invoicesWithProductsRepo.insertInvoicesWithProducts(invoicesWithProducts);
            }


            // set receiver account for online payment invoice
            OnlinePaymentAccount receiverInfo = onlinePaymentAccountRepo.getOnlinePaymentAccountByType(newInvoice.getPaymentMethod());

            newInvoice.setReceiverPaymentAccount(receiverInfo);
            newInvoice.setCarts(cartList);
            newInvoice.setInvoicesWithProducts(invoiceItemList);

            invoiceRepo.save(newInvoice);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void sendNotificationOnOrderProcess(String title, String content, String topic, Invoice invoice) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("invoiceId", String.valueOf(invoice.getId()));
        dataMap.put("paymentMethod", invoice.getPaymentMethod());
        dataMap.put("paymentStatus", invoice.getPaymentStatus());

        NotificationDTO notification = NotificationDTO.builder()
                .title(title)
                .body(content)
                .data(dataMap)
                .topic(topic)
                .build();

        firebaseUtils.sendMessage(notification);
    }

    @Value
    public static class InputValue implements InputValues {
        CartCheckoutDTO cartCheckout;
        HttpServletRequest httpRequest;
    }
}
