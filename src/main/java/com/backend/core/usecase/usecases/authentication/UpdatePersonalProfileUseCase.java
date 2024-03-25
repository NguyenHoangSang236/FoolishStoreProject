package com.backend.core.usecase.usecases.authentication;

import com.backend.core.entity.account.model.Customer;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.infrastructure.business.account.dto.CustomerRenderInfoDTO;
import com.backend.core.infrastructure.business.account.repository.CustomerRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.util.process.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


@Component
public class UpdatePersonalProfileUseCase extends UseCase<UpdatePersonalProfileUseCase.InputValue, ApiResponse> {
    @Autowired
    ValueRenderUtils valueRenderUtils;
    @Autowired
    CustomerRepository customerRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        CustomerRenderInfoDTO customerInfo = input.getCustomerInfo();
        HttpServletRequest request = input.getRequest();

        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(request);

        Customer customer = customerRepo.getCustomerById(customerId);
        customer.setCustomerInfoFromRenderInfo(customerInfo);

        customerRepo.save(customer);

        return new ApiResponse("success", "Update profile successfully", HttpStatus.OK);
    }

    @Value
    public static class InputValue implements InputValues {
        CustomerRenderInfoDTO customerInfo;
        HttpServletRequest request;
    }
}
