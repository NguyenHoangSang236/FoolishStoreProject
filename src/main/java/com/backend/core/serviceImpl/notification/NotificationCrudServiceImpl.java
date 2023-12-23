package com.backend.core.serviceImpl.notification;

import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.requestdto.ListRequestDTO;
import com.backend.core.entities.requestdto.notification.NotificationFilterRequestDTO;
import com.backend.core.entities.tableentity.Account;
import com.backend.core.entities.tableentity.Notification;
import com.backend.core.enums.ErrorTypeEnum;
import com.backend.core.enums.FilterTypeEnum;
import com.backend.core.enums.RoleEnum;
import com.backend.core.repository.customQuery.CustomQueryRepository;
import com.backend.core.repository.notification.NotificationRepository;
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
@Qualifier("NotificationCrudServiceImpl")
public class NotificationCrudServiceImpl implements CrudService {
    @Autowired
    ValueRenderUtils valueRenderUtils;

    @Autowired
    CustomQueryRepository customQueryRepo;

    @Autowired
    NotificationRepository notificationRepo;


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
        try {
            Notification notification = notificationRepo.getNotificationById(id);
            Account account = valueRenderUtils.getCurrentAccountFromRequest(httpRequest);

            // check if this notification is existed
            if (notification == null) {
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.BAD_REQUEST);
            }

            String topic = notification.getTopic();

            // check if this notification belongs to this account or not
            if ((!account.getUsername().equals(topic) && topic != "admin") ||
                    (!account.getRole().equals(RoleEnum.ADMIN) && topic == "admin")) {
                return new ResponseEntity<>(new ApiResponse("failed", "This notification does not belong to you"), HttpStatus.BAD_REQUEST);
            }

            notification.setSeen(true);
            notificationRepo.save(notification);

            return new ResponseEntity<>(new ApiResponse("success", "Seen notification successfully"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> updatingResponseByRequest(Object paramObj, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> readingFromSingleRequest(Object paramObj, HttpServletRequest httpRequest) {
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(httpRequest);

        try {
            if (paramObj instanceof NotificationFilterRequestDTO) {
                NotificationFilterRequestDTO filterRequest = (NotificationFilterRequestDTO) paramObj;
                String filterQuery = valueRenderUtils.getFilterQuery(filterRequest, FilterTypeEnum.NOTIFICATION, httpRequest, true);

                List<Notification> notiList = customQueryRepo.getBindingFilteredList(filterQuery, Notification.class);

                return new ResponseEntity<>(new ApiResponse("success", notiList), HttpStatus.OK);
            } else
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.BAD_REQUEST);
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
