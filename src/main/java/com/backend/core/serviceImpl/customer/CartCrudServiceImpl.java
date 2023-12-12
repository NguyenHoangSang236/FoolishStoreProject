package com.backend.core.serviceImpl.customer;

import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.requestdto.ListRequestDTO;
import com.backend.core.entities.requestdto.PaginationDTO;
import com.backend.core.entities.requestdto.cart.AddressNameDTO;
import com.backend.core.entities.requestdto.cart.CartCheckoutDTO;
import com.backend.core.entities.requestdto.cart.CartItemDTO;
import com.backend.core.entities.requestdto.cart.CartItemFilterRequestDTO;
import com.backend.core.entities.responsedto.AddressCodeDTO;
import com.backend.core.entities.responsedto.CartCheckoutInfoDTO;
import com.backend.core.entities.responsedto.CartRenderInfoDTO;
import com.backend.core.entities.tableentity.Cart;
import com.backend.core.entities.tableentity.ProductManagement;
import com.backend.core.enums.CartEnum;
import com.backend.core.enums.ErrorTypeEnum;
import com.backend.core.enums.FilterTypeEnum;
import com.backend.core.enums.RenderTypeEnum;
import com.backend.core.repository.cart.CartRenderInfoRepository;
import com.backend.core.repository.cart.CartRepository;
import com.backend.core.repository.customQuery.CustomQueryRepository;
import com.backend.core.repository.customer.CustomerRepository;
import com.backend.core.repository.product.ProductManagementRepository;
import com.backend.core.service.CrudService;
import com.backend.core.util.process.NetworkUtils;
import com.backend.core.util.process.ValueRenderUtils;
import com.backend.core.util.staticValues.GlobalDefaultStaticVariables;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Qualifier("CartCrudServiceImpl")
public class CartCrudServiceImpl implements CrudService {
    @Autowired
    CartRepository cartRepo;

    @Autowired
    CartRenderInfoRepository cartRenderInfoRepo;

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    CustomQueryRepository customQueryRepo;

    @Autowired
    ProductManagementRepository productRepo;

    @Autowired
    ProductManagementRepository productManagementRepo;

    @Autowired
    ValueRenderUtils valueRenderUtils;

    @Autowired
    NetworkUtils networkUtils;


    public CartCrudServiceImpl() {
    }


    // add a new item to cart
    @Override
    public ResponseEntity<ApiResponse> singleCreationalResponse(Object paramObj, HttpServletRequest httpRequest) {
        CartItemDTO cartItemDTO = (CartItemDTO) paramObj;
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(httpRequest);
        Cart newCartItem;
        ProductManagement productManagement;

        try {
            int pmId = productManagementRepo.getProductsManagementIdByProductIDAndColorAndSize(
                    cartItemDTO.getProductId(),
                    cartItemDTO.getColor(),
                    cartItemDTO.getSize());

            productManagement = productManagementRepo.getProductManagementById(pmId);

            //check available product available quantity is higher than ordered quantity or not
            if (productManagement.getAvailableQuantity() >= cartItemDTO.getQuantity()) {
                // get total quantity of the selected product in customer cart
                int count = cartRepo.getExistedCartItemCountByCustomerIdAndProductManagementId(customerId, productManagement.getId());

                // if it exists in the customer cart -> add its quantity
                if (count > 0) {
                    newCartItem = cartRepo.getCartItemByProductManagementIdAndCustomerId(productManagement.getId(), customerId);
                    newCartItem.addQuantity(cartItemDTO.getQuantity());
                    cartRepo.save(newCartItem);
                }
                // if it does not exist in the customer cart -> create new one
                else {
                    newCartItem = new Cart(
                            customerRepo.getCustomerById(customerId),
                            productManagement,
                            cartItemDTO.getQuantity(),
                            CartEnum.NOT_BOUGHT_YET.name(),
                            0
                    );
                    cartRepo.save(newCartItem);
                }
                return new ResponseEntity<>(new ApiResponse("success", "Add successfully"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse("failed", "Do not have enough quantity for your order"), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<ApiResponse> listCreationalResponse(List<Object> objList, HttpServletRequest httpRequest) {
        return null;
    }


    // remove an item from cart
    @Override
    public ResponseEntity<ApiResponse> removingResponseByRequest(Object paramObj, HttpServletRequest httpRequest) {
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(httpRequest);

        try {
            ListRequestDTO listRequestDTO = (ListRequestDTO) paramObj;

            int[] selectedCartIdArr = listRequestDTO.getIntegerArray();

            if (selectedCartIdArr != null) {
                for (int id : selectedCartIdArr) {
                    Cart cart = cartRepo.getCartById(id);

                    if (cart.getCustomer().getId() == customerId && cart.getBuyingStatus().equals(CartEnum.NOT_BOUGHT_YET.name())) {
                        customQueryRepo.deleteCartById(id);
                    } else
                        return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.UNAUTHORIZED.name()), HttpStatus.UNAUTHORIZED);
                }
                return new ResponseEntity<>(new ApiResponse("success", "Remove successfully"), HttpStatus.OK);
            } else
                return new ResponseEntity<>(new ApiResponse("failed", "Choose items to delete first"), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> removingResponseById(int id, HttpServletRequest httpRequest) {
        return null;
    }


    // update item info from cart
    @Override
    public ResponseEntity<ApiResponse> updatingResponseByList(ListRequestDTO listRequestDTO, HttpServletRequest httpRequest) {
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(httpRequest);

        try {
            List<Object> cartItemList = listRequestDTO.getObjectList();

            if (cartItemList != null) {
                for (Object obj : cartItemList) {
                    // parse json string without any double quotation marks to an object
                    Gson gson = new Gson();
                    CartItemDTO cartItemDTO = gson.fromJson(obj.toString(), CartItemDTO.class);

                    // get cart item by id
                    Cart requestCartItem = cartRepo.getCartById(cartItemDTO.getCartId());

                    int pmId = productManagementRepo.getProductsManagementIdByProductIDAndColorAndSize(
                            cartItemDTO.getProductId(),
                            cartItemDTO.getColor(),
                            cartItemDTO.getSize());

                    ProductManagement pm = productManagementRepo.getProductManagementById(pmId);

                    // check if updated cart quantity is higher than product's available quantity or not
                    if (pm.getAvailableQuantity() < cartItemDTO.getQuantity()) {
                        return new ResponseEntity<>(new ApiResponse("failed", "We only have " + pm.getAvailableQuantity() + " items left!"), HttpStatus.BAD_REQUEST);
                    } else {
                        Cart cartItem = cartRepo.getCartItemByProductManagementIdAndCustomerId(pmId, customerId);

                        // if the selected editing information of a cart item does not match with any others in the cart -> update information for that item
                        if (cartItem == null) {
                            pm = productManagementRepo.getProductManagementById(pmId);

                            requestCartItem.setQuantity(cartItemDTO.getQuantity());
                            requestCartItem.setProductManagement(pm);

                            cartRepo.save(requestCartItem);
                        }
                        // if the updating information of a cart item matches with one in the cart -> add quantity for the matched one -> remove the update-requested cart item
                        else if (cartItem.getId() != cartItemDTO.getCartId()) {
                            cartItem.addQuantity(cartItemDTO.getQuantity());
                            cartRepo.save(cartItem);
                            customQueryRepo.deleteCartById(cartItemDTO.getCartId());
                        }
                        // if the updating information of a cart item does not change in product detail (change quantity or select status only)
                        else {
                            cartItem.setQuantity(cartItemDTO.getQuantity());
                            cartItem.setSelectStatus(cartItemDTO.getSelectStatus());
                            cartRepo.save(cartItem);
                        }
                    }
                }

                return new ResponseEntity<>(new ApiResponse("success", "Update cart items successfully"), HttpStatus.OK);
            } else
                return new ResponseEntity<>(new ApiResponse("failed", "Please select items in cart to update"), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> updatingResponseById(int id, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> updatingResponseByRequest(Object paramObj, HttpServletRequest httpRequest) {
        return null;
    }


    // get cart item list through pagination or filter
    @Override
    public ResponseEntity<ApiResponse> readingFromSingleRequest(Object paramObj, HttpServletRequest httpRequest) {
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(httpRequest);
        List<CartRenderInfoDTO> cartItemList = new ArrayList<>();

        // if the param is pagination only -> show full cart items
        if (paramObj instanceof PaginationDTO pagination) {
            try {
                cartItemList = cartRenderInfoRepo.getFullCartListByCustomerId(
                        customerId,
                        (pagination.getPage() - 1) * pagination.getLimit(),
                        pagination.getLimit()
                );
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        // if the param is filter -> filter cart items
        else if (paramObj instanceof CartItemFilterRequestDTO cartItemFilterRequest) {
            try {
                String filterQuery = valueRenderUtils.getFilterQuery(cartItemFilterRequest, FilterTypeEnum.CART_ITEMS, httpRequest, true);

                cartItemList = customQueryRepo.getBindingFilteredList(filterQuery, CartRenderInfoDTO.class);

                return new ResponseEntity<>(new ApiResponse("success", cartItemList), HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        // if the param is a cart checkout -> checkout cart
        else if (paramObj instanceof CartCheckoutDTO cartCheckout) {
            try {
                List<CartRenderInfoDTO> selectedCartItemList = cartRenderInfoRepo.getSelectedCartItemListByCustomerId(customerId);

                double shippingFee = calculateShippingFee(cartCheckout, selectedCartItemList);

                double subtotal = 0;

                // calculate subtotal price
                for (CartRenderInfoDTO cartRenderInfoDTO : selectedCartItemList) {
                    subtotal += cartRenderInfoDTO.getTotalPrice();
                }

                // init new checkout object
                CartCheckoutInfoDTO checkout = new CartCheckoutInfoDTO(subtotal, subtotal + shippingFee, shippingFee);

                return new ResponseEntity<>(new ApiResponse("success", checkout), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        // if the param is address name -> get address code from GHN api using address returned by Google map
        else if (paramObj instanceof AddressNameDTO addressCodeRequest) {
            try {
                // get address code using GHN apis
                AddressCodeDTO addressCode = getGhnAddressCode(addressCodeRequest);

                if (addressCode != null) {
                    return new ResponseEntity<>(new ApiResponse("success", addressCode), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.NO_CONTENT);
                }

            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        // if the param is address code -> get available shipping service map list using GHN api
        else if (paramObj instanceof AddressCodeDTO addressCode) {
            try {
                // get service map list from ids of districts from GHN api
                List<Map> serviceMapList = getAvailableServiceList(addressCode.getFromDistrictId(), addressCode.getToDistrictId());

                if (serviceMapList != null && !serviceMapList.isEmpty()) {
                    return new ResponseEntity<>(new ApiResponse("success", serviceMapList), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.NO_CONTENT);
                }

            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.BAD_REQUEST);
    }


    @Override
    public ResponseEntity<ApiResponse> readingFromListRequest(List<Object> paramObjList, HttpServletRequest httpRequest) {

        return null;
    }


    @Override
    public ResponseEntity<ApiResponse> readingResponse(String renderType, HttpServletRequest httpRequest) {
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(httpRequest);

        // get cart item total quantity
        if (renderType.equals(RenderTypeEnum.TOTAL_CART_ITEM_QUANTITY.name())) {
            int totalQuantity = 0;

            try {
                totalQuantity = cartRepo.getCartQuantityByCustomerId(customerId);

                return new ResponseEntity<>(new ApiResponse("success", totalQuantity), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public ResponseEntity<ApiResponse> readingById(int id, HttpServletRequest httpRequest) {
        return null;
    }

    private double calculateShippingFee(CartCheckoutDTO cartCheckout, List<CartRenderInfoDTO> cartItemList) {
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

    private AddressCodeDTO getGhnAddressCode(AddressNameDTO addressCodeRequest) {
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

    private List<Map> getAvailableServiceList(int fromDistrictId, int toDistrictId) {
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

    private int getProvinceID(String provinceName) {
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

    private int getDistrictID(int provinceId, String districtName) {
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

    private String getWardCode(int districtId, String wardName) {
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
