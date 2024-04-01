package com.backend.core.usecase.business.invoice;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.invoice.model.Invoice;
import com.backend.core.entity.invoice.model.InvoicesWithProducts;
import com.backend.core.entity.notification.gateway.NotificationDTO;
import com.backend.core.entity.product.model.ProductManagement;
import com.backend.core.entity.refund.model.Refund;
import com.backend.core.infrastructure.business.invoice.repository.InvoiceRepository;
import com.backend.core.infrastructure.business.product.repository.ProductManagementRepository;
import com.backend.core.infrastructure.business.refund.repository.RefundRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.*;
import com.backend.core.usecase.service.FirebaseService;
import com.backend.core.usecase.util.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CancelOrderByIdUseCase extends UseCase<CancelOrderByIdUseCase.InputValue, ApiResponse> {
    @Autowired
    ValueRenderUtils valueRenderUtils;
    @Autowired
    InvoiceRepository invoiceRepo;
    @Autowired
    RefundRepository refundRepo;
    @Autowired
    ProductManagementRepository productManagementRepo;
    @Autowired
    FirebaseService firebaseService;


    @Override
    public ApiResponse execute(InputValue input) {
        Invoice invoice = new Invoice();
        int id = input.getInvoiceId();
        String message = "Cancel order successfully, ";
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(input.getHttpRequest());

        if (id == 0) {
            return new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name(), HttpStatus.BAD_REQUEST);
        } else {
            invoice = invoiceRepo.getInvoiceById(id);

            // check if this invoice exists or not
            if (invoice == null) {
                return new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name(), HttpStatus.BAD_REQUEST);
            }

            // check the order status whether it can be canceled or not
            if (!invoice.isUpdatable()) {
                return new ApiResponse("failed", "This order can not be canceled any more", HttpStatus.BAD_REQUEST);
            }

            // check if this customer is the owner or not
            if (customerId != invoice.getCustomer().getId()) {
                return new ApiResponse("failed", ErrorTypeEnum.UNAUTHORIZED.name(), HttpStatus.UNAUTHORIZED);
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
            sendNotificationOnOrderProcess("Order Management", "A customer has canceled an order", "admin", invoice);

            // retrieve product quantity from this invoice
            productQuantityProcess(InvoiceEnum.CUSTOMER_CANCEL.name(), invoice);

            return new ApiResponse("success", message, HttpStatus.OK);
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

        firebaseService.sendMessage(notification);
    }

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

    @Value
    public static class InputValue implements InputValues {
        int invoiceId;
        HttpServletRequest httpRequest;
    }
}
