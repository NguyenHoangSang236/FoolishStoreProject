package com.backend.core.serviceImpl.admin;

import com.backend.core.entities.renderdto.CustomerRenderInfoDTO;
import com.backend.core.entities.renderdto.StaffRenderInfoDTO;
import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.requestdto.ListRequestDTO;
import com.backend.core.entities.requestdto.PaginationDTO;
import com.backend.core.entities.tableentity.Account;
import com.backend.core.enums.ErrorTypeEnum;
import com.backend.core.enums.RoleEnum;
import com.backend.core.repository.account.AccountRepository;
import com.backend.core.repository.customer.CustomerRenderInfoRepository;
import com.backend.core.repository.staff.StaffRenderInfoRepository;
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
    CheckUtils checkUtils;

    @Autowired
    ValueRenderUtils valueRenderUtils;


    @Override
    public ResponseEntity singleCreationalResponse(Object paramObj, HttpServletRequest httpRequest) {
        return null;
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
        return null;
    }

    @Override
    public ResponseEntity updatingResponseByRequest(Object paramObj, HttpServletRequest httpRequest) {
        if (!checkUtils.isAdmin(httpRequest)) {
            return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.UNAUTHORIZED.name()), HttpStatus.UNAUTHORIZED);
        } else {
            try {
                // convert request param into Account
                Account account = (Account) paramObj;

                int id = account.getId();
                String status = account.getStatus();

                // get Account by id
                Account selectedAcc = accountRepo.getAccountByID(id);

                // check if selected account is existed
                if (selectedAcc == null) {
                    return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.NO_CONTENT);
                }

                // set status to CUSTOMER account and the status of it is DIFFERENT from request param's one
                if (!selectedAcc.getStatus().equals(account.getStatus()) && !selectedAcc.getRole().equals(RoleEnum.ADMIN.name())) {
                    selectedAcc.setStatus(status);
                    accountRepo.save(selectedAcc);

                    return new ResponseEntity(new ApiResponse("success", "Update account successfully"), HttpStatus.OK);
                } else {
                    return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Override
    public ResponseEntity readingFromSingleRequest(Object paramObj, HttpServletRequest httpRequest) {
        if (!checkUtils.isAdmin(httpRequest)) {
            return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.UNAUTHORIZED.name()), HttpStatus.UNAUTHORIZED);
        } else {
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

                    return new ResponseEntity(new ApiResponse("success", customerList), HttpStatus.OK);
                } else if (type.equals(RoleEnum.SHIPPER.name())) {
                    List<StaffRenderInfoDTO> shipperList = new ArrayList<>();

                    // get shipper info list by pagination
                    shipperList = staffRenderInfoRepo.getShipperInfoList(
                            valueRenderUtils.getStartLineForQueryPagination(limit, page),
                            limit
                    );

                    return new ResponseEntity(new ApiResponse("success", shipperList), HttpStatus.OK);
                } else if (type.equals(RoleEnum.ADMIN.name())) {
                    List<StaffRenderInfoDTO> adminList = new ArrayList<>();

                    // get admin info list by pagination
                    adminList = staffRenderInfoRepo.getAdminInfoList(
                            valueRenderUtils.getStartLineForQueryPagination(limit, page),
                            limit
                    );

                    return new ResponseEntity(new ApiResponse("success", adminList), HttpStatus.OK);
                } else
                    return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
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
    public ResponseEntity readingById(int id, HttpServletRequest httpRequest) {
        return null;
    }


}
