package com.backend.core.usecase.service;

import com.backend.core.entity.account.model.Customer;
import com.backend.core.entity.cart.gateway.AddressNameDTO;
import com.backend.core.entity.cart.gateway.CartCheckoutDTO;
import com.backend.core.entity.delivery.model.Delivery;
import com.backend.core.entity.invoice.model.Invoice;
import com.backend.core.entity.invoice.model.InvoicesWithProducts;
import com.backend.core.entity.product.model.Product;
import com.backend.core.infrastructure.business.cart.dto.CartRenderInfoDTO;
import com.backend.core.infrastructure.business.cart.repository.CartRenderInfoRepository;
import com.backend.core.infrastructure.business.delivery.controller.DeliveryRepository;
import com.backend.core.infrastructure.business.delivery.dto.AddressCodeDTO;
import com.backend.core.infrastructure.business.product.repository.ProductRepository;
import com.backend.core.infrastructure.config.constants.ConstantValue;
import com.backend.core.usecase.statics.PaymentEnum;
import com.backend.core.usecase.util.NetworkUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class GhnService {
    @Autowired
    CartRenderInfoRepository cartRenderInfoRepo;

    @Autowired
    ProductRepository productRepo;

    @Autowired
    DeliveryRepository deliveryRepo;

    @Autowired
    NetworkUtils networkUtils;

    public double calculateShippingFee(CartCheckoutDTO cartCheckout, List<CartRenderInfoDTO> cartItemList) {
        double shippingFee = 0;
        Map<String, Object> requestMap = new HashMap<>();
        int length = 0;
        int width = 0;
        int weight = 0;
        int height = 0;

        try {
            // get the highest value of length, width and stack the value of weight, height
            for (CartRenderInfoDTO cartItem : cartItemList) {
                int productId = cartItem.getProductId();
                int cartItemWidth = cartRenderInfoRepo.getCartItemWidth(productId);
                int cartItemLength = cartRenderInfoRepo.getCartItemLength(productId);

                weight += cartRenderInfoRepo.getCartItemWeight(productId);
                height += cartRenderInfoRepo.getCartItemHeight(productId);

                if (width < cartItemWidth) {
                    width = cartItemWidth;
                }

                if (length < cartItemLength) {
                    length = cartItemLength;
                }
            }

            requestMap.put("from_district_id", cartCheckout.getFromDistrictId());
            requestMap.put("to_district_id", cartCheckout.getToDistrictId());
            requestMap.put("from_ward_code", cartCheckout.getFromWardCode());
            requestMap.put("to_ward_code", cartCheckout.getToWardCode());
            requestMap.put("service_id", cartCheckout.getServiceId());
            requestMap.put("height", height);
            requestMap.put("weight", weight);
            requestMap.put("width", width);
            requestMap.put("length", length);

            ResponseEntity<Map> shippingFeeResponse = networkUtils.getGhnResponse(ConstantValue.GHN_SHIPPING_FEE_URL, requestMap);

            if (shippingFeeResponse.getStatusCode().equals(HttpStatus.OK)) {
                Map<String, Object> resMap = (Map<String, Object>) shippingFeeResponse.getBody().get("data");

                // convert VND to USD
                shippingFee = (int) resMap.get("total") / 24300;
            }

            return shippingFee;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public AddressCodeDTO getGhnAddressCode(AddressNameDTO addressCodeRequest) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // data from request
            String fromProvince = addressCodeRequest.getFromProvince();
            String fromDistrict = addressCodeRequest.getFromDistrict();
            String fromWard = addressCodeRequest.getFromWard();
            String toProvince = addressCodeRequest.getToProvince();
            String toDistrict = addressCodeRequest.getToDistrict();
            String toWard = addressCodeRequest.getToWard();

            // selected data to call GHN api
            int selectedFromProvinceId = getProvinceID(fromProvince);
            int selectedToProvinceId = getProvinceID(toProvince);

            if (selectedFromProvinceId != 0 && selectedToProvinceId != 0) {
                int selectedFromDistrictId = getDistrictID(selectedFromProvinceId, fromDistrict);
                int selectedToDistrictId = getDistrictID(selectedToProvinceId, toDistrict);

                if (selectedFromDistrictId != 0 && selectedToDistrictId != 0) {
                    String selectedFromWardCode = getWardCode(selectedFromDistrictId, fromWard);
                    String selectedToWardCode = getWardCode(selectedToDistrictId, toWard);

                    if (selectedToWardCode != "0" && selectedFromWardCode != "0") {
                        return AddressCodeDTO.builder()
                                .fromProvinceId(selectedFromProvinceId)
                                .toProvinceId(selectedToProvinceId)
                                .fromDistrictId(selectedFromDistrictId)
                                .toDistrictId(selectedToDistrictId)
                                .fromWardCode(selectedFromWardCode)
                                .toWardCode(selectedToWardCode)
                                .build();
                    }
                }
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Map> getAvailableServiceList(int fromDistrictId, int toDistrictId) {
        Map<String, Object> requestMap = new HashMap<>();

        try {
            requestMap.put("shop_id", 190298);
            requestMap.put("from_district", fromDistrictId);
            requestMap.put("to_district", toDistrictId);

            ResponseEntity<Map> districtListRes = networkUtils.getGhnResponse(ConstantValue.GHN_AVAILABLE_GHN_SERVICES_URL, requestMap);

            if (districtListRes.getStatusCode().equals(HttpStatus.OK)) {
                // get 'data' field from request
                return (List<Map>) districtListRes.getBody().get("data");
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getProvinceID(String provinceName) {
        ObjectMapper objectMapper = new ObjectMapper();
        int selectedProvinceId = 0;

        try {
            // get response from GHN api
            ResponseEntity<Map> provinceListRes = networkUtils.getGhnResponse(ConstantValue.PROVINCE_LIST_URL, null);

            if (provinceListRes.getStatusCode().equals(HttpStatus.OK)) {
                // get 'data' field from request
                List<Map> provinceMapList = (List<Map>) provinceListRes.getBody().get("data");

                // get the item in the list having the province name = fromProvince
                for (Map map : provinceMapList) {
                    List<String> nameExtensionList = (List<String>) map.get("NameExtension");

                    if (nameExtensionList.contains(provinceName)) {
                        // get the province ID
                        selectedProvinceId = (int) map.get("ProvinceID");
                        break;
                    }
                }
            }
            return selectedProvinceId;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getDistrictID(int provinceId, String districtName) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> requestMap = new HashMap<>();
        int selectedDistrictId = 0;

        try {
            // get response from GHN api
            requestMap.put("province_id", provinceId);
            ResponseEntity<Map> districtListRes = networkUtils.getGhnResponse(ConstantValue.DISTRICT_LIST_URL, requestMap);

            if (districtListRes.getStatusCode().equals(HttpStatus.OK)) {
                // get 'data' field from request
                List<Map> districtMapList = (List<Map>) districtListRes.getBody().get("data");

                // get the item in the list having the province id = provinceId
                for (Map map : districtMapList) {
                    String district = (String) map.get("DistrictName");

                    if (district.contains(districtName)) {
                        selectedDistrictId = (int) map.get("DistrictID");

                        break;
                    }
                }
            }
            return selectedDistrictId;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getWardCode(int districtId, String wardName) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> requestMap = new HashMap<>();
        String selectedWardCode = "0";

        try {
            // get response from GHN api
            requestMap.put("district_id", districtId);
            ResponseEntity<Map> wardListRes = networkUtils.getGhnResponse(ConstantValue.WARD_LIST_URL, requestMap);

            if (wardListRes.getStatusCode().equals(HttpStatus.OK)) {
                // get 'data' field from request
                List<Map> wardMapList = (List<Map>) wardListRes.getBody().get("data");

                // get the item in the list having the province id = provinceId
                for (Map map : wardMapList) {
                    String ward = (String) map.get("WardName");

                    if (ward.contains(wardName)) {
                        selectedWardCode = map.get("WardCode").toString();

                        break;
                    }
                }
            }
            return selectedWardCode;
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }


    // create a new GHN shipping order
    public Delivery getNewGhnShippingOrderCode(Invoice invoice) {
        Delivery delivery = new Delivery();

        try {
            Map<String, Object> request = new HashMap<>();

            int length = 0;
            int width = 0;
            int weight = 0;
            int height = 0;

            Customer customer = invoice.getCustomer();
            List<InvoicesWithProducts> invoiceProductList = invoice.getInvoicesWithProducts();
            List<Map> items = new ArrayList<>();

            // get the highest value of length, width and stack the value of weight, height
            for (InvoicesWithProducts invoiceProduct : invoiceProductList) {
                int productId = invoiceProduct.getProductManagement().getProduct().getId();

                Product product = productRepo.getProductById(productId);

                int productWidth = product.getWidth();
                int productLength = product.getLength();

                weight += product.getWeight();
                height += product.getHeight();

                if (width < productWidth) {
                    width = productWidth;
                }

                if (length < productLength) {
                    length = productLength;
                }

                items.add(invoiceProduct.getGhnItemJson());
            }

            request.put("from_name", "Foolish Fashion Store");
            request.put("from_phone", "0977815809");
            request.put("from_address", "72 Thành Thái, Phường 14, Quận 10, Hồ Chí Minh, Vietnam");
            request.put("from_ward_name", "Phường 14");
            request.put("from_district_name", "Quận 10");
            request.put("from_province_name", "Hồ Chí Minh");
            request.put("to_name", customer.getName());
            request.put("to_address", customer.getName());
            request.put("to_ward_code", invoice.getWardCode());
            request.put("to_district_id", invoice.getDistrictId());
            request.put("to_phone", customer.getPhoneNumber());
            request.put("length", length);
            request.put("width", width);
            request.put("height", height);
            request.put("weight", weight);
            request.put("service_type_id", 2);
            request.put("service_id", 0);
            request.put("payment_type_id", invoice.getPaymentMethod().equals(PaymentEnum.COD.name()) ? 2 : 1);
            request.put("note", invoice.getNote());
            request.put("required_note", "KHONGCHOXEMHANG");
            request.put("items", items);

            System.out.println(request);

            ResponseEntity<Map> newOrderRes = networkUtils.getGhnResponse(ConstantValue.GHN_CREATE_ORDER_URL, request);

            if (newOrderRes.getStatusCode().equals(HttpStatus.OK)) {
                Map response = (Map) newOrderRes.getBody().get("data");

                String orderCode = (String) response.get("order_code");
                String expectedTimeStr = (String) response.get("expected_delivery_time");

                DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
                Instant instant = Instant.from(formatter.parse(expectedTimeStr));

                Date expectedTime = Date.from(instant);

                delivery.setInvoice(invoice);
                delivery.setExpectedDeliveryTime(expectedTime);
                delivery.setShippingOrderCode(orderCode);
                deliveryRepo.save(delivery);
            }

            return delivery;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}