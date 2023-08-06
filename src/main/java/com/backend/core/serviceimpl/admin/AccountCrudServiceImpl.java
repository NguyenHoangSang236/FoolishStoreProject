package com.backend.core.serviceimpl.admin;

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
import com.backend.core.util.CheckUtils;
import com.backend.core.util.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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


    @Override
    public ApiResponse singleCreationalResponse(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ApiResponse listCreationalResponse(List<Object> objList, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ApiResponse removingResponseByRequest(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ApiResponse removingResponseById(int id, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ApiResponse updatingResponseByList(ListRequestDTO listRequestDTO, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ApiResponse updatingResponseById(int id, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ApiResponse updatingResponseByRequest(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        if (!CheckUtils.loggedIn(session)) {
            return new ApiResponse("failed", ErrorTypeEnum.LOGIN_FIRST.name());
        } else if (!CheckUtils.isAdmin(session)) {
            return new ApiResponse("failed", ErrorTypeEnum.UNAUTHORIZED.name());
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
                    return new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name());
                }

                // set status to CUSTOMER account and the status of it is DIFFERENT from request param's one
                if (!selectedAcc.getStatus().equals(account.getStatus()) && !selectedAcc.getRole().equals(RoleEnum.ADMIN.name())) {
                    selectedAcc.setStatus(status);
                    accountRepo.save(selectedAcc);

                    return new ApiResponse("success", "Update account status successfully");
                } else {
                    return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
            }
        }
    }

    @Override
    public ApiResponse readingFromSingleRequest(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        if (!CheckUtils.loggedIn(session)) {
            return new ApiResponse("failed", ErrorTypeEnum.LOGIN_FIRST.name());
        } else if (!CheckUtils.isAdmin(session)) {
            return new ApiResponse("failed", ErrorTypeEnum.UNAUTHORIZED.name());
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
                            ValueRenderUtils.getStartLineForQueryPagination(limit, page),
                            limit
                    );

                    return new ApiResponse("success", customerList);
                } else if (type.equals(RoleEnum.SHIPPER.name())) {
                    List<StaffRenderInfoDTO> shipperList = new ArrayList<>();

                    // get shipper info list by pagination
                    shipperList = staffRenderInfoRepo.getShipperInfoList(
                            ValueRenderUtils.getStartLineForQueryPagination(limit, page),
                            limit
                    );

                    return new ApiResponse("success", shipperList);
                } else if (type.equals(RoleEnum.ADMIN.name())) {
                    List<StaffRenderInfoDTO> adminList = new ArrayList<>();

                    // get admin info list by pagination
                    adminList = staffRenderInfoRepo.getAdminInfoList(
                            ValueRenderUtils.getStartLineForQueryPagination(limit, page),
                            limit
                    );

                    return new ApiResponse("success", adminList);
                } else return new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name());
            } catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
            }
        }
    }

    @Override
    public ApiResponse readingFromListRequest(List<Object> paramObjList, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ApiResponse readingResponse(HttpSession session, String renderType, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ApiResponse readingById(int id, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }


}
