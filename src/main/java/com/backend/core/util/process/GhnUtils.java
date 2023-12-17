package com.backend.core.util.process;

import com.backend.core.entities.requestdto.cart.AddressNameDTO;
import com.backend.core.entities.requestdto.cart.CartCheckoutDTO;
import com.backend.core.entities.responsedto.AddressCodeDTO;
import com.backend.core.entities.responsedto.CartRenderInfoDTO;
import com.backend.core.repository.cart.CartRenderInfoRepository;
import com.backend.core.util.staticValues.GlobalDefaultStaticVariables;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GhnUtils {
    @Autowired
    CartRenderInfoRepository cartRenderInfoRepo;

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

            System.out.println(requestMap.toString());

            ResponseEntity<Map> shippingFeeResponse = networkUtils.getGhnPostResponse(GlobalDefaultStaticVariables.shippingFeeUrl, requestMap);

            if (shippingFeeResponse.getStatusCode().equals(HttpStatus.OK)) {
                Map<String, Object> resMap = (Map<String, Object>) shippingFeeResponse.getBody().get("data");

                System.out.println(resMap.get("total"));

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

            ResponseEntity<Map> districtListRes = networkUtils.getGhnPostResponse(GlobalDefaultStaticVariables.availableServicesUrl, requestMap);

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
            ResponseEntity<Map> provinceListRes = networkUtils.getGhnGetResponse(GlobalDefaultStaticVariables.provinceDataListUrl);

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
            ResponseEntity<Map> districtListRes = networkUtils.getGhnPostResponse(GlobalDefaultStaticVariables.districtDataListUrl, requestMap);

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
            ResponseEntity<Map> wardListRes = networkUtils.getGhnPostResponse(GlobalDefaultStaticVariables.wardDataListUrl, requestMap);

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
}
