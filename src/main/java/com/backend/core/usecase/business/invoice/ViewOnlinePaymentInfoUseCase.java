package com.backend.core.usecase.business.invoice;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.invoice.model.Invoice;
import com.backend.core.entity.invoice.model.OnlinePaymentAccount;
import com.backend.core.infrastructure.business.invoice.dto.InvoiceRenderInfoDTO;
import com.backend.core.infrastructure.business.invoice.repository.InvoiceRepository;
import com.backend.core.infrastructure.business.online_payment.dto.OnlinePaymentInfoDTO;
import com.backend.core.infrastructure.business.online_payment.repository.OnlinePaymentAccountRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.AdminAcceptanceEnum;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.statics.PaymentEnum;
import com.backend.core.usecase.util.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ViewOnlinePaymentInfoUseCase extends UseCase<ViewOnlinePaymentInfoUseCase.InputValue, ApiResponse> {
    @Autowired
    ValueRenderUtils valueRenderUtils;
    @Autowired
    InvoiceRepository invoiceRepo;
    @Autowired
    OnlinePaymentAccountRepository onlinePaymentAccountRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(input.getHttpRequest());
        Invoice invoice = input.getInvoice();

        List<InvoiceRenderInfoDTO> invoiceRenderList = new ArrayList<>();
        List<Invoice> invoiceList = new ArrayList<>();

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

            return new ApiResponse("success", receiver, HttpStatus.OK);
        } else
            return new ApiResponse("failed", ErrorTypeEnum.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST);
    }

    @Value
    public static class InputValue implements InputValues {
        Invoice invoice;
        HttpServletRequest httpRequest;
    }
}
