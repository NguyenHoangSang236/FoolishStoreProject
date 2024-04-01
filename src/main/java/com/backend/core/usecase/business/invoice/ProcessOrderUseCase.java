package com.backend.core.usecase.business.invoice;

import com.backend.core.entity.account.model.Staff;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.cart.model.Cart;
import com.backend.core.entity.delivery.model.Delivery;
import com.backend.core.entity.invoice.gateway.OrderProcessDTO;
import com.backend.core.entity.invoice.model.Invoice;
import com.backend.core.entity.invoice.model.InvoicesWithProducts;
import com.backend.core.entity.notification.gateway.NotificationDTO;
import com.backend.core.entity.product.model.ProductManagement;
import com.backend.core.infrastructure.business.account.repository.StaffRepository;
import com.backend.core.infrastructure.business.cart.repository.CartRepository;
import com.backend.core.infrastructure.business.invoice.repository.InvoiceRepository;
import com.backend.core.infrastructure.business.product.repository.ProductManagementRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.*;
import com.backend.core.usecase.util.CheckUtils;
import com.backend.core.usecase.service.FirebaseService;
import com.backend.core.usecase.service.GhnService;
import com.backend.core.usecase.util.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProcessOrderUseCase extends UseCase<ProcessOrderUseCase.InputValue, ApiResponse> {
    @Autowired
    ValueRenderUtils valueRenderUtils;
    @Autowired
    InvoiceRepository invoiceRepo;
    @Autowired
    StaffRepository staffRepo;
    @Autowired
    CheckUtils checkUtils;
    @Autowired
    FirebaseService firebaseService;
    @Autowired
    GhnService ghnService;
    @Autowired
    CartRepository cartRepo;
    @Autowired
    ProductManagementRepository productManagementRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        OrderProcessDTO orderProcess = input.getOrderProcess();
        int adminId = valueRenderUtils.getCustomerOrStaffIdFromRequest(input.getHttpRequest());
        Staff adminInCharge = staffRepo.getStaffById(adminId);

        String adminAction = orderProcess.getAdminAction();
        int invoiceId = orderProcess.getId();
        Invoice invoice = invoiceRepo.getInvoiceById(invoiceId);

        String outOfStockProductName = invoice.getOutOfStockProduct();

        // check invoice is existed or not
        if (invoice == null) {
            return new ApiResponse("failed", "This invoice is not existed", HttpStatus.BAD_REQUEST);
        }

        // admin action from request is existed or not
        if (!checkUtils.isAdminAcceptanceEnumValueExist(adminAction) &&
                !checkUtils.isInvoiceEnumValueExist(adminAction)) {
            return new ApiResponse("failed", "This action is not existed", HttpStatus.BAD_REQUEST);
        }

        // check if invoice product is out of stock or not
        if (outOfStockProductName != null) {
            return new ApiResponse("failed", outOfStockProductName + " has been out of stock!", HttpStatus.BAD_REQUEST);
        }

        // check if invoice can be updated or not
        if (!invoice.isUpdatable()) {
            return new ApiResponse("failed", "This order can not be updated", HttpStatus.BAD_REQUEST);
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
        }
        // admin confirm online payment
        else if (adminAction.equals(AdminAcceptanceEnum.CONFIRMED_ONLINE_PAYMENT.name()) &&
                invoice.getAdminAcceptance().equals(AdminAcceptanceEnum.PAYMENT_WAITING.name()) &&
                !invoice.getPaymentMethod().equals(PaymentEnum.COD.name())) {
            invoice.setAdminAcceptance(adminAction);
            invoice.setOrderStatus(InvoiceEnum.PACKING.name());
            invoice.setPaymentStatus(PaymentEnum.PAID.name());
            invoice.setPayDate(new Date());
            invoice.setStaff(adminInCharge);

            // add sold quantity and subtract in-stock quantity of products from this invoice
            productQuantityProcess(adminAction, invoice);
        }
        // admin finished packing the order
        else if (adminAction.equals(InvoiceEnum.FINISH_PACKING.name()) &&
                (invoice.getAdminAcceptance().equals(AdminAcceptanceEnum.CONFIRMED_ONLINE_PAYMENT.name()) ||
                        invoice.getAdminAcceptance().equals((AdminAcceptanceEnum.ACCEPTED.name()))) &&
                invoice.getOrderStatus().equals(InvoiceEnum.PACKING.name())) {
            // create a new GHN shipping order
            if (!createNewGhnShippingOrder(invoice)) {
                return new ApiResponse("failed", "Failed to add new GHN shipping order, please check necessary information again", HttpStatus.BAD_REQUEST);
            }

            invoice.setOrderStatus(adminAction);
        }
        // admin confirms order is being shipped
        else if ((adminAction.equals(InvoiceEnum.SHIPPING.name())) &&
                invoice.getOrderStatus().equals(InvoiceEnum.FINISH_PACKING.name()) &&
                (invoice.getAdminAcceptance().equals(AdminAcceptanceEnum.ACCEPTED.name()) ||
                        invoice.getAdminAcceptance().equals(AdminAcceptanceEnum.CONFIRMED_ONLINE_PAYMENT.name())) &&
                invoice.getDelivery() != null) {
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
                return new ApiResponse("failed", "Must have a reason for failed order", HttpStatus.BAD_REQUEST);
            }

            // set shipped date for order when SUCCESS
            if (adminAction.equals(InvoiceEnum.SUCCESS.name())) {
                invoice.getDelivery().setShipDate(new Date());
            }

            // set payment status for SUCCESS COD order
            if (invoice.getPaymentMethod().equals(PaymentEnum.COD.name()) && adminAction.equals(InvoiceEnum.SUCCESS.name())) {
                invoice.setPaymentStatus(PaymentEnum.PAID.name());
            }

            invoice.setOrderStatus(adminAction);
        } else
            return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name(), HttpStatus.BAD_REQUEST);

        updateCartItemBuyingStatusOnAdminAcceptance(adminAction, invoice);

        invoiceRepo.save(invoice);

        // send message to customer
        sendNotificationOnOrderProcess("Your order's process", adminActionMessage(adminAction), invoice.getCustomer().getAccount().getUsername(), invoice);

        return new ApiResponse("success", adminActionResult(adminAction), HttpStatus.OK);
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

    public boolean createNewGhnShippingOrder(Invoice invoice) {
        try {
            Delivery newDelivery = ghnService.getNewGhnShippingOrderCode(invoice);

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

    public void updateCartItemBuyingStatusOnAdminAcceptance(String adminAcceptance, Invoice invoice) {
        List<InvoicesWithProducts> invoiceProductList = invoice.getInvoicesWithProducts();

        for (InvoicesWithProducts invoiceProduct : invoiceProductList) {
            Cart cartItem = cartRepo.getPendingCartItemByProductManagementIdAndCustomerIdAndInvoiceId(
                    invoiceProduct.getProductManagement().getId(),
                    invoice.getCustomer().getId(),
                    invoice.getId()
            );

            if (adminAcceptance.equals(InvoiceEnum.SUCCESS.name())) {
                cartItem.setBuyingStatus(CartEnum.BOUGHT.name());
            } else if (adminAcceptance.equals(InvoiceEnum.FAILED.name()) &&
                    adminAcceptance.equals(InvoiceEnum.SUCCESS.name())) {
                cartItem.setBuyingStatus(CartEnum.NOT_BOUGHT_YET.name());
            } else {
                cartItem.setBuyingStatus(CartEnum.PENDING.name());
            }

            cartRepo.save(cartItem);
        }
    }

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
            case "SHIPPING" -> {
                return "We are shipping your order";
            }
            case "SUCCESS" -> {
                return "Your order has been shipped successfully";
            }
            case "FAILED" -> {
                return "Sorry! Your order has been failed, please check the reason";
            }
            default -> {
                return ErrorTypeEnum.NO_DATA_ERROR.name();
            }
        }
    }

    @Value
    public static class InputValue implements InputValues {
        OrderProcessDTO orderProcess;
        HttpServletRequest httpRequest;
    }
}
