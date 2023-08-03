package com.backend.core.serviceimpl.customer;

import com.backend.core.entities.dto.ApiResponse;
import com.backend.core.entities.dto.ListRequestDTO;
import com.backend.core.entities.dto.invoice.InvoiceFilterRequestDTO;
import com.backend.core.entities.dto.invoice.InvoicesWithProducts;
import com.backend.core.entities.dto.invoice.OnlinePaymentReceiverDTO;
import com.backend.core.entities.embededkey.InvoicesWithProductsPrimaryKeys;
import com.backend.core.entities.renderdto.CartRenderInfoDTO;
import com.backend.core.entities.renderdto.InvoiceDetailRenderInfoDTO;
import com.backend.core.entities.renderdto.InvoiceRenderInfoDTO;
import com.backend.core.entities.tableentity.Cart;
import com.backend.core.entities.tableentity.Invoice;
import com.backend.core.entities.tableentity.ProductManagement;
import com.backend.core.enums.*;
import com.backend.core.repository.cart.CartRenderInfoRepository;
import com.backend.core.repository.cart.CartRepository;
import com.backend.core.repository.customQuery.CustomQueryRepository;
import com.backend.core.repository.customer.CustomerRepository;
import com.backend.core.repository.invoice.InvoiceDetailsRenderInfoRepository;
import com.backend.core.repository.invoice.InvoiceRepository;
import com.backend.core.repository.invoice.InvoicesWithProductsRepository;
import com.backend.core.repository.product.ProductManagementRepository;
import com.backend.core.service.CrudService;
import com.backend.core.util.CheckUtils;
import com.backend.core.util.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Qualifier("InvoiceCrudServiceImpl")
public class InvoiceCrudServiceImpl implements CrudService {
    @Autowired
    InvoiceRepository invoiceRepo;

    @Autowired
    InvoiceDetailsRenderInfoRepository invoiceDetailsRenderInfoRepo;

    @Autowired
    CustomQueryRepository customQueryRepo;

    @Autowired
    ProductManagementRepository productManagementRepo;

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    InvoicesWithProductsRepository invoicesWithProductsRepo;

    @Autowired
    CartRepository cartRepo;

    @Autowired
    CartRenderInfoRepository cartRenderInfoRepo;


    public InvoiceCrudServiceImpl() {
    }


    @Override
    public synchronized ApiResponse singleCreationalResponse(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        int customerId = ValueRenderUtils.getCustomerIdByHttpSession(session);
        String paymentMethod = (String) paramObj;
        String responseSuccessMessage = "New order has been created successfully";
        Invoice newInvoice;

        // check if logged in or not
        if (!CheckUtils.loggedIn(session)) {
            return new ApiResponse("failed", ErrorTypeEnum.LOGIN_FIRST.name());
        } else {
            try {
                int newInvoiceId = invoiceRepo.getLastestInvoiceId() + 1;

                if (paymentMethod.equals(PaymentMethodEnum.COD.name())) {
                    newInvoice = new Invoice(
                            newInvoiceId,
                            new Date(),
                            DeliveryTypeEnum.ACCEPTANCE_WAITING.name(),
                            0,
                            paymentMethod,
                            CurrencyEnum.USD.name(),
                            "",
                            "",
                            0,
                            0,
                            "",
                            "",
                            AdminAcceptanceEnum.WAITING.name(),
                            null,
                            null,
                            customerRepo.getCustomerById(customerId)
                    );
                    responseSuccessMessage += ", please wait for Admin accept your order!";
                } else if (isOnlinePaymentMethod(paymentMethod)) {
                    newInvoice = new Invoice(
                            newInvoiceId,
                            new Date(),
                            DeliveryTypeEnum.PAYMENT_WAITING.name(),
                            0,
                            paymentMethod,
                            CurrencyEnum.USD.name(),
                            "",
                            "",
                            0,
                            0,
                            "",
                            "",
                            AdminAcceptanceEnum.WAITING.name(),
                            null,
                            null,
                            customerRepo.getCustomerById(customerId)
                    );
                    responseSuccessMessage += ", please transfer money to the given banking information for us to continue processing your order!";
                } else {
                    return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
                }

                createNewInvoice(newInvoice, customerId);
            } catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
            }
        }

        return new ApiResponse("success", responseSuccessMessage);
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
        Invoice invoice = new Invoice();
        String message = "Cancel order successfully, ";
        int customerId = ValueRenderUtils.getCustomerIdByHttpSession(session);

        // check if logged in or not
        if (!CheckUtils.loggedIn(session)) {
            return new ApiResponse("failed", ErrorTypeEnum.LOGIN_FIRST.name());
        } else if (id == 0) {
            return new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name());
        }
        else {
            try {
                invoice = invoiceRepo.getInvoiceById(id);

                // check if this customer is the owner or not
                if(customerId != invoice.getCustomer().getId()) {
                    return new ApiResponse("failed", ErrorTypeEnum.UNAUTHORIZED.name());
                }

                // if online payment and shipper has not accepted the order yet -> refund 50%
                if (!invoice.getPaymentMethod().equals(PaymentMethodEnum.COD.name()) &&
                    !invoice.getDeliveryStatus().equals(DeliveryTypeEnum.CANCEL.name()) &&
                        (invoice.getDeliveryStatus().equals(DeliveryTypeEnum.SHIPPER_WAITING.name()) ||
                         invoice.getDeliveryStatus().equals(DeliveryTypeEnum.PACKING.name()) ||
                         invoice.getDeliveryStatus().equals(DeliveryTypeEnum.PAYMENT_WAITING.name()))) {
                    invoice.setRefundPercentage(50);
                    invoice.setReason("Customer cancels order before shipper takes over, refund 50%");
                    message += "you will be refunded 50% of the total order value, we will send it within 24 hours!";
                }
                // if COD payment -> error
                else if(invoice.getPaymentMethod().equals(PaymentMethodEnum.COD.name())) {
                    return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
                }
                else {
                    invoice.setReason("Customer cancels order, no refund");
                    message += "the shipper has already been on the way, so you will not have any refund!";
                }
                invoice.setDeliveryStatus(DeliveryTypeEnum.CANCEL.name());
                invoiceRepo.save(invoice);

            } catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
            }
        }

        return new ApiResponse("success", message);
    }

    @Override
    public ApiResponse updatingResponseByRequest(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ApiResponse readingFromSingleRequest(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        int customerId = ValueRenderUtils.getCustomerIdByHttpSession(session);

        List<InvoiceRenderInfoDTO> invoiceRenderList = new ArrayList<>();
        List<Invoice> invoiceList = new ArrayList<>();

        // check if logged in or not
        if (!CheckUtils.loggedIn(session)) {
            return new ApiResponse("failed", ErrorTypeEnum.LOGIN_FIRST.name());
        } else {
            // filter orders
            if (paramObj instanceof InvoiceFilterRequestDTO invoiceFilterRequest) {
                return filterInvoice(invoiceFilterRequest, session);
            }
            // get receiver bank info for online payment
            else if (paramObj instanceof Invoice paramInvoice) {
                return getReceiverBankInfo(paramInvoice);
            }
        }

        return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
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
    public ApiResponse readingById(int invoiceId, HttpSession session, HttpServletRequest httpRequest) {
        int customerId = ValueRenderUtils.getCustomerIdByHttpSession(session);

        if (!CheckUtils.loggedIn(session)) {
            return new ApiResponse("failed", ErrorTypeEnum.LOGIN_FIRST.name());
        } else if (!isInvoiceOwner(customerId, invoiceId)) {
            return new ApiResponse("failed", ErrorTypeEnum.UNAUTHORIZED.name());
        } else {
            try {
                List<InvoiceDetailRenderInfoDTO> invoiceItemsList = invoiceDetailsRenderInfoRepo.getInvoiceItemsByInvoiceId(invoiceId);
                return new ApiResponse("success", invoiceItemsList);
            } catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
            }
        }
    }


    public boolean isInvoiceOwner(int customerId, int invoiceId) {
        return (invoiceRepo.getInvoiceCountByInvoiceIdAndCustomerId(invoiceId, customerId) > 0);
    }


    public boolean isOnlinePaymentMethod(String method) {
        List choices = Arrays.asList(PaymentMethodEnum.values());

        //compare with enum value
        if (choices.contains(PaymentMethodEnum.MOMO.name()) ||
                choices.contains(PaymentMethodEnum.BANK_TRANSFER.name()) ||
                choices.contains(PaymentMethodEnum.PAYPAL.name())) {
            return true;
        }

        return false;
    }


    // filter invoices
    public ApiResponse filterInvoice(InvoiceFilterRequestDTO invoiceFilterRequest, HttpSession session) {
        try {
            String filterQuery = ValueRenderUtils.getFilterQuery(invoiceFilterRequest, FilterTypeEnum.INVOICE, session);

            // get list from query
            List<InvoiceRenderInfoDTO> invoiceRenderList = customQueryRepo.getBindingFilteredList(filterQuery, InvoiceRenderInfoDTO.class);

            return new ApiResponse("success", invoiceRenderList);
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            return new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name());
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
        }
    }


    // get receiver banking info
    public ApiResponse getReceiverBankInfo(Invoice invoice) {
        Invoice currentInvoice = invoiceRepo.getInvoiceById(invoice.getId());
        String currentPaymentMethod = invoice.getPaymentMethod();
        OnlinePaymentReceiverDTO receiver = new OnlinePaymentReceiverDTO();

        if (currentInvoice.getPaymentStatus() == 0 &&
                currentInvoice.getDeliveryStatus().equals(DeliveryTypeEnum.PAYMENT_WAITING.name()) &&
                currentInvoice.getAdminAcceptance().equals(AdminAcceptanceEnum.WAITING.name())) {
            if (currentPaymentMethod.equals(PaymentMethodEnum.PAYPAL.name())) {
                receiver = new OnlinePaymentReceiverDTO(
                        "Pay for invoice " + currentInvoice.getId(),
                        "03365672301",
                        "NGUYEN HOANG SANG",
                        "TP BANK - Tien Phong Bank",
                        currentInvoice.getTotalPrice()
                );
            } else if (currentPaymentMethod.equals(PaymentMethodEnum.MOMO.name())) {
                receiver = new OnlinePaymentReceiverDTO(
                        "Pay for invoice " + currentInvoice.getId(),
                        "0977815809",
                        "NGUYEN HOANG SANG",
                        "",
                        currentInvoice.getTotalPrice()
                );
            }
        }

        return new ApiResponse("success", receiver);
    }


    // create a new invoice process
    public void createNewInvoice(Invoice newInvoice, int customerId) {
        double invoiceTotalPrice = 0;
        List<CartRenderInfoDTO> cartItemList = cartRenderInfoRepo.getSelectedCartItemListByCustomerId(customerId);

        // save new invoice first to take its ID as the foreign key for InvoicesWithProducts to progress
        invoiceRepo.save(newInvoice);

        // modify data to tables
        for (CartRenderInfoDTO item : cartItemList) {
            invoiceTotalPrice += item.getTotalPrice();

            Cart tblCart = cartRepo.getCartById(item.getId());

            // set cart item from Cart table to buying_status = BOUGHT and select_status = 0
            tblCart.setSelectStatus(0);
            tblCart.setBuyingStatus(CartEnum.BOUGHT.name());
            cartRepo.save(tblCart);

            // get ProductManagement by id, color and size
            ProductManagement pm = productManagementRepo.getProductsManagementByProductIDAndColorAndSize(
                    item.getProductId(),
                    item.getColor(),
                    item.getSize()
            );

            InvoicesWithProducts invoicesWithProducts = new InvoicesWithProducts(
                    new InvoicesWithProductsPrimaryKeys(pm.getId(), newInvoice.getId()),
                    pm,
                    newInvoice,
                    item.getQuantity()
            );

            // insert to InvoicesWithProducts table
            invoicesWithProductsRepo.insertInvoicesWithProducts(invoicesWithProducts);
        }

        newInvoice.setTotalPrice(invoiceTotalPrice);
        invoiceRepo.save(newInvoice);
    }


    // subtract available quantity and add sold quantity of the product
    public void soldProductQuantityProcess(int productId, String productColor, String productSize, int productQuantity) {
        // get ProductManagement by id, color and size
        ProductManagement pm = productManagementRepo.getProductsManagementByProductIDAndColorAndSize(
                productId,
                productColor,
                productSize
        );

        // subtract available quantity
        pm.subtractQuantity(ProductManagementQuantityTypeEnum.AVAILABLE_QUANTITY.name(), productQuantity);
        // add sold quantity
        pm.addQuantity(ProductManagementQuantityTypeEnum.SOLD_QUANTITY.name(), productQuantity);

        productManagementRepo.save(pm);

    }
}
