package com.backend.core.serviceImpl.admin;

import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.requestdto.ListRequestDTO;
import com.backend.core.entities.requestdto.refund.RefundFilterRequestDTO;
import com.backend.core.entities.tableentity.Refund;
import com.backend.core.enums.ErrorTypeEnum;
import com.backend.core.enums.FilterTypeEnum;
import com.backend.core.repository.customQuery.CustomQueryRepository;
import com.backend.core.service.CrudService;
import com.backend.core.util.process.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("RefundCrudServiceImpl")
public class RefundCrudServiceImpl implements CrudService {
    @Autowired
    CustomQueryRepository customQueryRepo;

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
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> readingFromSingleRequest(Object paramObj, HttpServletRequest httpRequest) {
        try {
            if (paramObj instanceof RefundFilterRequestDTO) {
                RefundFilterRequestDTO refundFilterRequest = (RefundFilterRequestDTO) paramObj;

                String filterQuery = valueRenderUtils.getFilterQuery(refundFilterRequest, FilterTypeEnum.REFUND, httpRequest, false);

                List<Refund> refundList = customQueryRepo.getBindingFilteredList(filterQuery, Refund.class);

                return new ResponseEntity<>(new ApiResponse("success", refundList), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
        return null;
    }
}
