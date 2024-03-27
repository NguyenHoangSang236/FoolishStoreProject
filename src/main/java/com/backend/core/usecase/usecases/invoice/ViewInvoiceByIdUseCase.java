package com.backend.core.usecase.usecases.invoice;

import com.backend.core.entity.account.model.Account;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.invoice.model.Invoice;
import com.backend.core.infrastructure.business.invoice.dto.InvoiceDetailsDTO;
import com.backend.core.infrastructure.business.invoice.dto.InvoiceProductInfoDTO;
import com.backend.core.infrastructure.business.invoice.repository.InvoiceDetailsRenderInfoRepository;
import com.backend.core.infrastructure.business.invoice.repository.InvoiceRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.statics.RoleEnum;
import com.backend.core.usecase.util.process.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ViewInvoiceByIdUseCase extends UseCase<ViewInvoiceByIdUseCase.InputValue, ApiResponse> {
    @Autowired
    ValueRenderUtils valueRenderUtils;
    @Autowired
    InvoiceRepository invoiceRepo;
    @Autowired
    InvoiceDetailsRenderInfoRepository invoiceDetailsRenderInfoRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        Account currentAcc = valueRenderUtils.getCurrentAccountFromRequest(input.getHttpRequest());
        int invoiceId = input.getInvoiceId();

        if (!isInvoiceOwnerOrAdmin(currentAcc, invoiceId)) {
            return new ApiResponse("failed", ErrorTypeEnum.UNAUTHORIZED.name(), HttpStatus.UNAUTHORIZED);
        } else {
            List<InvoiceProductInfoDTO> invoiceItemsList = invoiceDetailsRenderInfoRepo.getInvoiceItemsByInvoiceId(invoiceId);
            Invoice invoice = invoiceRepo.getInvoiceById(invoiceId);

            InvoiceDetailsDTO invoiceDetails = InvoiceDetailsDTO.builder()
                    .invoice(invoice)
                    .invoiceProducts(invoiceItemsList)
                    .build();

            return new ApiResponse("success", invoiceDetails, HttpStatus.OK);
        }
    }

    public boolean isInvoiceOwnerOrAdmin(Account acc, int invoiceId) {
        if (acc.getRole().equals(RoleEnum.ADMIN.name())) {
            return true;
        } else {
            return (invoiceRepo.getInvoiceCountByInvoiceIdAndCustomerId(invoiceId, acc.getCustomer().getId()) > 0);
        }
    }

    @Value
    public static class InputValue implements InputValues {
        int invoiceId;
        HttpServletRequest httpRequest;
    }
}
