package com.backend.core.usecase.business.cart;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.api.ListRequestDTO;
import com.backend.core.entity.api.PaginationDTO;
import com.backend.core.entity.cart.gateway.AddressNameDTO;
import com.backend.core.entity.cart.gateway.CartCheckoutDTO;
import com.backend.core.entity.cart.gateway.CartItemDTO;
import com.backend.core.entity.cart.gateway.CartItemFilterRequestDTO;
import com.backend.core.entity.cart.model.Cart;
import com.backend.core.entity.product.model.ProductManagement;
import com.backend.core.infrastructure.business.account.repository.CustomerRepository;
import com.backend.core.infrastructure.business.cart.dto.CartCheckoutInfoDTO;
import com.backend.core.infrastructure.business.cart.dto.CartRenderInfoDTO;
import com.backend.core.infrastructure.business.cart.repository.CartRenderInfoRepository;
import com.backend.core.infrastructure.business.cart.repository.CartRepository;
import com.backend.core.infrastructure.business.delivery.dto.AddressCodeDTO;
import com.backend.core.infrastructure.business.product.repository.ProductManagementRepository;
import com.backend.core.infrastructure.config.database.CustomQueryRepository;
import com.backend.core.usecase.service.CrudService;
import com.backend.core.usecase.statics.CartEnum;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.statics.FilterTypeEnum;
import com.backend.core.usecase.statics.RenderTypeEnum;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import usecase.util.process.GhnUtils;
import usecase.util.process.NetworkUtils;
import usecase.util.process.ValueRenderUtils;

import java.util.ArrayList;
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

    @Autowired
    GhnUtils ghnUtils;


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

                    if (cartItemDTO.getQuantity() < 1) {
                        return new ResponseEntity<>(new ApiResponse("failed", "Cart item quantity must be higher than 0"), HttpStatus.BAD_REQUEST);
                    }

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

                return new ResponseEntity<>(new ApiResponse("success", cartItemList), HttpStatus.OK);
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

                double shippingFee = ghnUtils.calculateShippingFee(cartCheckout, selectedCartItemList);

                double subtotal = 0;

                // calculate subtotal price
                for (CartRenderInfoDTO cartRenderInfoDTO : selectedCartItemList) {
                    subtotal += cartRenderInfoDTO.getTotalPrice();
                }

                // init new checkout object
                CartCheckoutInfoDTO checkoutInfo = new CartCheckoutInfoDTO(subtotal, subtotal + shippingFee, shippingFee);

                return new ResponseEntity<>(new ApiResponse("success", checkoutInfo), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        // if the param is address name -> get address code from GHN api using address returned by Google map
        else if (paramObj instanceof AddressNameDTO addressCodeRequest) {
            try {
                // get address code using GHN apis
                AddressCodeDTO addressCode = ghnUtils.getGhnAddressCode(addressCodeRequest);

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
                List<Map> serviceMapList = ghnUtils.getAvailableServiceList(addressCode.getFromDistrictId(), addressCode.getToDistrictId());

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
}
