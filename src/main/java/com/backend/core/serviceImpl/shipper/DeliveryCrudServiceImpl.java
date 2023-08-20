package com.backend.core.serviceImpl.shipper;

import com.backend.core.entities.renderdto.DeliveryOrderRenderInfoDTO;
import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.requestdto.ListRequestDTO;
import com.backend.core.entities.requestdto.delivery.DeliveryActionOnOrderDTO;
import com.backend.core.entities.requestdto.delivery.DeliveryFilterRequestDTO;
import com.backend.core.entities.tableentity.Delivery;
import com.backend.core.entities.tableentity.Invoice;
import com.backend.core.entities.tableentity.Staff;
import com.backend.core.enums.DeliveryStatusEnum;
import com.backend.core.enums.ErrorTypeEnum;
import com.backend.core.enums.FilterTypeEnum;
import com.backend.core.enums.ShipperActionEnum;
import com.backend.core.repository.customQuery.CustomQueryRepository;
import com.backend.core.repository.delivery.DeliveryRepository;
import com.backend.core.repository.invoice.InvoiceRepository;
import com.backend.core.repository.staff.StaffRepository;
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
@Qualifier("DeliveryCrudServiceImpl")
public class DeliveryCrudServiceImpl implements CrudService {
    @Autowired
    DeliveryRepository deliveryRepo;

    @Autowired
    ValueRenderUtils valueRenderUtils;

    @Autowired
    CustomQueryRepository customQueryRepo;

    @Autowired
    InvoiceRepository invoiceRepo;

    @Autowired
    StaffRepository staffRepo;


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
        try {
            DeliveryActionOnOrderDTO actionOnOrder = (DeliveryActionOnOrderDTO) paramObj;

            int invoiceId = actionOnOrder.getId();
            String action = actionOnOrder.getAction();

            Invoice invoice = invoiceRepo.getInvoiceById(invoiceId);

            if (invoice == null) {
                return new ResponseEntity(new ApiResponse("failed", "This invoice does not exist"), HttpStatus.BAD_REQUEST);
            }

            // when shipper wants to take the order
            if (action.equals(ShipperActionEnum.TAKE_ORDER.name()) &&
                    invoice.getDeliveryStatus().equals(DeliveryStatusEnum.SHIPPER_WAITING.name())) {
                invoice.setDeliveryStatus(DeliveryStatusEnum.SHIPPING.name());
                invoiceRepo.save(invoice);

                Delivery delivery = new Delivery();
                int shipperId = valueRenderUtils.getCustomerOrStaffIdFromRequest(httpRequest);
                Staff shipper = staffRepo.getStaffById(shipperId);

                delivery.setInvoice(invoice);
                delivery.setStaff(shipper);
                delivery.setCurrentStatus(action);
                deliveryRepo.save(delivery);

                return new ResponseEntity(new ApiResponse("success", "Take the order " + invoiceId + " successfully"), HttpStatus.OK);
            }
            // when shipper wants to cancel the order before they ship
            else if (action.equals(ShipperActionEnum.CANCEL_ORDER.name()) &&
                    invoice.getDeliveryStatus().equals(DeliveryStatusEnum.SHIPPING.name())) {
                invoice.setDeliveryStatus(DeliveryStatusEnum.SHIPPER_WAITING.name());
                invoiceRepo.save(invoice);

                Delivery delivery = deliveryRepo.getDeliveryByInvoiceId(invoiceId);
                deliveryRepo.deleteById(delivery.getId());

                return new ResponseEntity(new ApiResponse("success", "Cancel order " + invoiceId + " successfully"), HttpStatus.OK);
            } else
                return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity readingFromSingleRequest(Object paramObj, HttpServletRequest httpRequest) {
        try {
            DeliveryFilterRequestDTO deliveryFilterRequest = (DeliveryFilterRequestDTO) paramObj;

            List<DeliveryOrderRenderInfoDTO> deliveryOrderList = customQueryRepo.getBindingFilteredList(
                    valueRenderUtils.getFilterQuery(deliveryFilterRequest, FilterTypeEnum.DELIVERY, httpRequest),
                    DeliveryOrderRenderInfoDTO.class
            );

            return new ResponseEntity(new ApiResponse("success", deliveryOrderList), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
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
