package com.backend.core.serviceImpl.shipper;

import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.requestdto.ListRequestDTO;
import com.backend.core.entities.requestdto.delivery.DeliveryActionOnOrderDTO;
import com.backend.core.entities.requestdto.delivery.DeliveryFilterRequestDTO;
import com.backend.core.entities.responsedto.DeliveryOrderRenderInfoDTO;
import com.backend.core.entities.tableentity.Delivery;
import com.backend.core.entities.tableentity.Invoice;
import com.backend.core.entities.tableentity.Staff;
import com.backend.core.enums.DeliveryEnum;
import com.backend.core.enums.ErrorTypeEnum;
import com.backend.core.enums.FilterTypeEnum;
import com.backend.core.enums.ShipperActionEnum;
import com.backend.core.repository.customQuery.CustomQueryRepository;
import com.backend.core.repository.delivery.DeliveryRenderInfoRepository;
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
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.Date;
import java.util.List;

@Service
@Qualifier("DeliveryCrudServiceImpl")
public class DeliveryCrudServiceImpl implements CrudService {
    @Autowired
    DeliveryRepository deliveryRepo;
    @Autowired
    DeliveryRenderInfoRepository deliveryRenderInfoRepo;
    @Autowired
    ValueRenderUtils valueRenderUtils;
    @Autowired
    CustomQueryRepository customQueryRepo;
    @Autowired
    InvoiceRepository invoiceRepo;
    @Autowired
    StaffRepository staffRepo;

    public DeliveryCrudServiceImpl() {
    }

    public ResponseEntity<ApiResponse> singleCreationalResponse(Object paramObj, HttpServletRequest httpRequest) {
        return null;
    }

    public ResponseEntity<ApiResponse> listCreationalResponse(List<Object> objList, HttpServletRequest httpRequest) {
        return null;
    }

    public ResponseEntity<ApiResponse> removingResponseByRequest(Object paramObj, HttpServletRequest httpRequest) {
        return null;
    }

    public ResponseEntity<ApiResponse> removingResponseById(int id, HttpServletRequest httpRequest) {
        return null;
    }

    public ResponseEntity<ApiResponse> updatingResponseByList(ListRequestDTO listRequestDTO, HttpServletRequest httpRequest) {
        return null;
    }

    public ResponseEntity<ApiResponse> updatingResponseById(int id, HttpServletRequest httpRequest) {
        return null;
    }

    public ResponseEntity<ApiResponse> updatingResponseByRequest(Object paramObj, HttpServletRequest httpRequest) {
        if (paramObj instanceof DeliveryActionOnOrderDTO) {
            return this.actionOnOrder((DeliveryActionOnOrderDTO) paramObj, httpRequest);
        } else {
            return paramObj instanceof DeliveryOrderRenderInfoDTO
                    ? this.reportDelivery((DeliveryOrderRenderInfoDTO) paramObj, httpRequest)
                    : new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<ApiResponse> readingFromSingleRequest(Object paramObj, HttpServletRequest httpRequest) {
        try {
            DeliveryFilterRequestDTO deliveryFilterRequest = (DeliveryFilterRequestDTO) paramObj;
            List<DeliveryOrderRenderInfoDTO> deliveryOrderList = this.customQueryRepo.getBindingFilteredList(
                    this.valueRenderUtils.getFilterQuery(
                            deliveryFilterRequest,
                            FilterTypeEnum.DELIVERY,
                            httpRequest, true
                    ),
                    DeliveryOrderRenderInfoDTO.class
            );

            return new ResponseEntity<>(new ApiResponse("success", deliveryOrderList), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponse> readingFromListRequest(List<Object> paramObjList, HttpServletRequest httpRequest) {
        return null;
    }

    public ResponseEntity<ApiResponse> readingResponse(String renderType, HttpServletRequest httpRequest) {
        return null;
    }

    public ResponseEntity<ApiResponse> readingById(int id, HttpServletRequest httpRequest) {
        try {
            DeliveryOrderRenderInfoDTO deliveryRenderInfo = this.deliveryRenderInfoRepo.getDeliveryRenderInfoByInvoiceId(id);
            return deliveryRenderInfo == null
                    ? new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.BAD_REQUEST)
                    : new ResponseEntity<>(new ApiResponse("success", deliveryRenderInfo), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponse> actionOnOrder(DeliveryActionOnOrderDTO actionOnOrder, HttpServletRequest httpRequest) {
        try {
            int invoiceId = actionOnOrder.getId();
            String action = actionOnOrder.getAction();
            Invoice invoice = this.invoiceRepo.getInvoiceById(invoiceId);

            // check invoice exists or not
            if (invoice == null) {
                return new ResponseEntity<>(new ApiResponse("failed", "This invoice does not exist"), HttpStatus.BAD_REQUEST);
            } else {
                Delivery delivery = new Delivery();

                // when shipper takes order
                if (action.equals(ShipperActionEnum.TAKE_ORDER.name()) && invoice.getDeliveryStatus().equals(DeliveryEnum.SHIPPER_WAITING.name())) {
                    invoice.setDeliveryStatus(DeliveryEnum.SHIPPING.name());
                    this.invoiceRepo.save(invoice);

                    int shipperId = this.valueRenderUtils.getCustomerOrStaffIdFromRequest(httpRequest);
                    Staff shipper = this.staffRepo.getStaffById(shipperId);

                    delivery.setInvoice(invoice);
                    delivery.setStaff(shipper);
                    delivery.setCurrentStatus(action);
                    this.deliveryRepo.save(delivery);

                    return new ResponseEntity<>(new ApiResponse("success", "Take order " + invoiceId + " successfully"), HttpStatus.OK);
                }
                // when shipper cancels order
                else if (action.equals(ShipperActionEnum.CANCEL_ORDER.name()) && invoice.getDeliveryStatus().equals(DeliveryEnum.SHIPPING.name())) {
                    invoice.setDeliveryStatus(DeliveryEnum.SHIPPER_WAITING.name());
                    this.invoiceRepo.save(invoice);
                    delivery = this.deliveryRepo.getDeliveryByInvoiceId(invoiceId);
                    this.deliveryRepo.deleteById(delivery.getId());
                    return new ResponseEntity<>(new ApiResponse("success", "Cancel order " + invoiceId + " successfully"), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<ApiResponse> reportDelivery(DeliveryOrderRenderInfoDTO deliveryReport, HttpServletRequest httpRequest) {
        try {
            Delivery delivery = this.deliveryRepo.getDeliveryByInvoiceIdAndShipperId(deliveryReport.getInvoiceId(), deliveryReport.getShipperId());
            String currentDeliveryStatus = deliveryReport.getCurrentDeliveryStatus();

            // check this delivery data and delivery status exist or not
            if (delivery != null && EnumUtils.findEnumInsensitiveCase(DeliveryEnum.class, currentDeliveryStatus) != null) {
                Date deliveryDate = deliveryReport.getDeliveryDate();
                Date expDeliveryDate = deliveryReport.getExpectedDeliveryDate();
                String shipperCmt = deliveryReport.getAdditionalShipperComment();

                // it must have comment from shipper when the order is failed
                if ((shipperCmt == null || shipperCmt.isBlank()) &&
                        currentDeliveryStatus.equals(DeliveryEnum.SHIPPED.name())) {
                    return new ResponseEntity<>(new ApiResponse("failed", "Must have comment about the reason of the failed order"), HttpStatus.BAD_REQUEST);
                }

                // change delivery value of invoice when delivery is done or failed
                if (currentDeliveryStatus.equals(DeliveryEnum.SHIPPED.name()) ||
                        currentDeliveryStatus.equals(DeliveryEnum.FAILED.name())) {
                    // it must have delivery date when the delivery is finished
                    if (deliveryDate == null) {
                        return new ResponseEntity<>(new ApiResponse("failed", "Must have delivery date"), HttpStatus.BAD_REQUEST);
                    }

                    Invoice invoice = delivery.getInvoice();
                    invoice.setDeliveryStatus(currentDeliveryStatus);
                    invoice.setReason(currentDeliveryStatus.equals(DeliveryEnum.FAILED.name()) ? shipperCmt : "");
                    this.invoiceRepo.save(invoice);
                }

                if (expDeliveryDate == null) {
                    return new ResponseEntity<>(new ApiResponse("failed", "Must have expected delivery date"), HttpStatus.BAD_REQUEST);
                }

                delivery.setExpectedDeliveryDate(expDeliveryDate);
                delivery.setAdditionalShipperComment(shipperCmt);
                delivery.setCurrentStatus(currentDeliveryStatus);
                this.deliveryRepo.save(delivery);

                return new ResponseEntity<>(new ApiResponse("success", "Report delivery status successfully"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}