package com.backend.core.usecase.business.account;

import com.backend.core.entity.account.gateway.AccountFilterDTO;
import com.backend.core.entity.account.gateway.AccountFilterRequestDTO;
import com.backend.core.entity.account.model.Account;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.api.ListRequestDTO;
import com.backend.core.entity.api.PaginationDTO;
import com.backend.core.infrastructure.business.account.dto.CustomerRenderInfoDTO;
import com.backend.core.infrastructure.business.account.dto.StaffRenderInfoDTO;
import com.backend.core.infrastructure.business.account.repository.AccountRepository;
import com.backend.core.infrastructure.business.account.repository.CustomerRenderInfoRepository;
import com.backend.core.infrastructure.business.account.repository.StaffRenderInfoRepository;
import com.backend.core.infrastructure.config.database.CustomQueryRepository;
import com.backend.core.usecase.service.CrudService;
import com.backend.core.usecase.statics.AccountStatusEnum;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.statics.RoleEnum;
import com.backend.core.usecase.util.process.CheckUtils;
import com.backend.core.usecase.util.process.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("AccountCrudServiceImpl")
public class AccountCrudServiceImpl implements CrudService {
    @Autowired
    CustomerRenderInfoRepository customerRenderInfoRepo;

    @Autowired
    StaffRenderInfoRepository staffRenderInfoRepo;

    @Autowired
    AccountRepository accountRepo;

    @Autowired
    CustomQueryRepository customQueryRepo;

    @Autowired
    CheckUtils checkUtils;

    @Autowired
    ValueRenderUtils valueRenderUtils;


    @Override
    public ResponseEntity<ApiResponse> singleCreationalResponse(Object paramObj, HttpServletRequest httpRequest) {
        return null;
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
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> updatingResponseByRequest(Object paramObj, HttpServletRequest httpRequest) {
        try {
            // convert request param into Account
            Account account = (Account) paramObj;

            int id = account.getId();
            String status = account.getStatus();

            // get Account by id
            Account selectedAcc = accountRepo.getAccountByID(id);

            // check if selected account is existed
            if (selectedAcc == null) {
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.BAD_REQUEST);
            }

            // check the status is correct or not
            if (EnumUtils.findEnumInsensitiveCase(AccountStatusEnum.class, status) == null) {
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.BAD_REQUEST);
            }

            // set status to CUSTOMER account and the status of it is DIFFERENT from request param's one
            if (!selectedAcc.getStatus().equals(account.getStatus()) &&
                    !selectedAcc.getRole().equals(RoleEnum.ADMIN.name())) {
                selectedAcc.setStatus(status);
                selectedAcc.setCurrentJwt(null);
                accountRepo.save(selectedAcc);

                return new ResponseEntity<>(new ApiResponse("success", "Update account successfully"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> readingFromSingleRequest(Object paramObj, HttpServletRequest httpRequest) {
        if (paramObj instanceof PaginationDTO) {
            try {
                // convert request param into PaginationDTO
                PaginationDTO pagination = (PaginationDTO) paramObj;

                int limit = pagination.getLimit();
                int page = pagination.getPage();
                String type = pagination.getType();

                // check the type from request param to process
                if (type.equals(RoleEnum.CUSTOMER.name())) {
                    List<CustomerRenderInfoDTO> customerList = new ArrayList<>();

                    // get customer info list by pagination
                    customerList = customerRenderInfoRepo.getCustomerInfoList(
                            valueRenderUtils.getStartLineForQueryPagination(limit, page),
                            limit
                    );

                    return new ResponseEntity<>(new ApiResponse("success", customerList), HttpStatus.OK);
                } else if (type.equals(RoleEnum.ADMIN.name())) {
                    List<StaffRenderInfoDTO> adminList = new ArrayList<>();

                    // get admin info list by pagination
                    adminList = staffRenderInfoRepo.getAdminInfoList(
                            valueRenderUtils.getStartLineForQueryPagination(limit, page),
                            limit
                    );

                    return new ResponseEntity<>(new ApiResponse("success", adminList), HttpStatus.OK);
                } else
                    return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else if (paramObj instanceof AccountFilterRequestDTO) {
            try {
                AccountFilterRequestDTO accountFilterRequest = (AccountFilterRequestDTO) paramObj;
                String filterQuery = valueRenderUtils.accountFilterQuery(
                        (AccountFilterDTO) accountFilterRequest.getFilter(),
                        accountFilterRequest.getPagination()
                );

                if (accountFilterRequest.getPagination().getType().equals(RoleEnum.ADMIN.name())) {
                    List<StaffRenderInfoDTO> staffInfoList = customQueryRepo.getBindingFilteredList(filterQuery, StaffRenderInfoDTO.class);

                    return new ResponseEntity<>(new ApiResponse("success", staffInfoList), HttpStatus.OK);
                } else if (accountFilterRequest.getPagination().getType().equals(RoleEnum.CUSTOMER.name())) {
                    List<CustomerRenderInfoDTO> customerInfoList = customQueryRepo.getBindingFilteredList(filterQuery, CustomerRenderInfoDTO.class);

                    return new ResponseEntity<>(new ApiResponse("success", customerInfoList), HttpStatus.OK);
                } else
                    return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<ApiResponse> readingById(int id, HttpServletRequest httpRequest) {
        try {
            if (id < 1) {
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.BAD_REQUEST);
            }

            CustomerRenderInfoDTO customerRenderInfo = customerRenderInfoRepo.getCustomerInfoByAccountId(id);
            StaffRenderInfoDTO staffRenderInfo = staffRenderInfoRepo.getStaffInfoByAccountId(id);

            if (customerRenderInfo != null) {
                return new ResponseEntity<>(new ApiResponse("success", customerRenderInfo), HttpStatus.OK);
            } else if (staffRenderInfo != null) {
                return new ResponseEntity<>(new ApiResponse("success", staffRenderInfo), HttpStatus.OK);
            } else
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
