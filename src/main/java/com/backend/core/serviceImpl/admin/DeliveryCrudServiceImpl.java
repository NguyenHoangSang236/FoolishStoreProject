package com.backend.core.serviceImpl.admin;

import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.requestdto.ListRequestDTO;
import com.backend.core.entities.requestdto.delivery.DeliveryRequestDTO;
import com.backend.core.entities.tableentity.Delivery;
import com.backend.core.entities.tableentity.Invoice;
import com.backend.core.enums.AdminAcceptanceEnum;
import com.backend.core.enums.ErrorTypeEnum;
import com.backend.core.repository.delivery.DeliveryRepository;
import com.backend.core.repository.invoice.InvoiceRepository;
import com.backend.core.service.CrudService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Qualifier("DeliveryCrudServiceImpl")
public class DeliveryCrudServiceImpl implements CrudService {
    @Autowired
    DeliveryRepository deliveryRepo;

    @Autowired
    InvoiceRepository invoiceRepo;

    @Override
    public ResponseEntity<ApiResponse> singleCreationalResponse(Object paramObj, HttpServletRequest httpRequest) {
        try {
            DeliveryRequestDTO request = (DeliveryRequestDTO) paramObj;
            Delivery delivery = new Delivery();

            Invoice invoice = invoiceRepo.getInvoiceById(request.getInvoiceId());

            // check if invoice is not existed
            if (invoice == null) {
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.BAD_REQUEST);
            } else {
                // invoice does not have any shipping order
                if (invoice.getDelivery() != null) {
                    return new ResponseEntity<>(new ApiResponse("failed", "This invoice has already had a shipping order"), HttpStatus.BAD_REQUEST);
                }
                // invoice does not have any acceptance or payment confirmation from ADMIN
                else if (!invoice.getAdminAcceptance().equals(AdminAcceptanceEnum.ACCEPTED.name()) &&
                        !invoice.getAdminAcceptance().equals(AdminAcceptanceEnum.CONFIRMED_ONLINE_PAYMENT.name())) {
                    return new ResponseEntity<>(new ApiResponse("failed", "This order has been accepted or confirmed payment by ADMIN"), HttpStatus.BAD_REQUEST);
                }
            }

            // check if invoice id (client order code) is existed
            Map<String, Object> map = new HashMap<>();
            map.put("client_order_code", request.getInvoiceId());

            ResponseEntity responseEntity = restTemplate("/shipping-order/detail-by-client-code", map);

            if (responseEntity.getStatusCode().value() != 200) {
                return new ResponseEntity<>(new ApiResponse("failed", "The shipping order with client order code = " + request.getInvoiceId() + " does not exist"), HttpStatus.BAD_REQUEST);
            }

            delivery.setInvoice(invoice);
            delivery.setShippingOrderCode(request.getShippingOrderCode());
            deliveryRepo.save(delivery);

            return new ResponseEntity<>(new ApiResponse("success", "Create new shipping order successfully"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
        return null;
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

    public ResponseEntity restTemplate(String url, Map<String, Object> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("ShopId", "190298");
        headers.set("Token", "10a16ebf-7fa0-11ee-8bfa-8a2dda8ec551");

        HttpEntity httpEntity = new HttpEntity(body, headers);

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange("https://dev-online-gateway.ghn.vn/shiip/public-api/v2" + url, HttpMethod.POST, httpEntity, Map.class);
    }
}
