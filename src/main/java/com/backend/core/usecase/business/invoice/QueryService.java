package com.backend.core.usecase.business.invoice;

import com.backend.core.entity.FilterFactory;
import com.backend.core.entity.FilterRequest;
import com.backend.core.entity.account.gateway.AccountFilterDTO;
import com.backend.core.entity.account.model.Account;
import com.backend.core.entity.api.PaginationDTO;
import com.backend.core.entity.cart.gateway.CartItemFilterDTO;
import com.backend.core.entity.comment.gateway.CommentRequestDTO;
import com.backend.core.entity.invoice.gateway.InvoiceFilterDTO;
import com.backend.core.entity.notification.gateway.NotificationFilterDTO;
import com.backend.core.entity.product.gateway.ProductFilterDTO;
import com.backend.core.entity.refund.gateway.RefundFilterDTO;
import com.backend.core.usecase.statics.CartEnum;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.statics.FilterTypeEnum;
import com.backend.core.usecase.statics.RoleEnum;
import com.backend.core.usecase.util.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;

@Service
public class QueryService {
    @Autowired
    ValueRenderUtils valueRenderUtils;


    // get list of data from filter
    public String getFilterQuery(FilterRequest paramObj, FilterTypeEnum filterType, HttpServletRequest request, boolean authen) {
        String filterQuery = ErrorTypeEnum.TECHNICAL_ERROR.name();

        // initiate filter type
        FilterRequest filterRequest = FilterFactory.getFilterRequest(filterType);

        // determine filter request class type
        Class<? extends FilterRequest> filterRequestEntity = filterRequest.getClass();

        // convert paramObj
        if (filterRequestEntity.isInstance(paramObj)) {
            filterRequest = filterRequestEntity.cast(paramObj);

            Object filter = filterRequest.getFilter();

            PaginationDTO pagination = filterRequest.getPagination();
            int page = filterRequest.getPagination().getPage();
            int limit = filterRequest.getPagination().getLimit();

            Account currentAccount = valueRenderUtils.getCurrentAccountFromRequest(request);

            try {
                switch (filterType) {
                    case INVOICE -> {
                        filterQuery = invoiceFilterQuery((InvoiceFilterDTO) filter, pagination, request);
                    }
                    case PRODUCT -> {
                        filterQuery = productFilterQuery((ProductFilterDTO) filter, pagination);
                    }
                    case NOTIFICATION -> {
                        filterQuery = notificationFilterQuery((NotificationFilterDTO) filter, pagination, currentAccount);
                    }
                    case CART_ITEMS -> {
                        filterQuery = cartItemFilterQuery((CartItemFilterDTO) filter, pagination, currentAccount);
                    }
                    case COMMENT -> {
                        filterQuery = commentFilterQuery((CommentRequestDTO) filter, pagination);
                    }
                    case ACCOUNT -> {
                        filterQuery = accountFilterQuery((AccountFilterDTO) filter, pagination);
                    }
                    case REFUND -> {
                        filterQuery = refundFilterQuery((RefundFilterDTO) filter, pagination);
                    }
                    default -> {
                        return filterQuery;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return filterQuery;
    }


    //create a query for invoice binding filter
    public String invoiceFilterQuery(InvoiceFilterDTO invoiceFilter, PaginationDTO pagination, HttpServletRequest request) {
        String paymentStatus = invoiceFilter != null ? invoiceFilter.getPaymentStatus() : null;
        String adminAcceptance = invoiceFilter != null ? invoiceFilter.getAdminAcceptance() : null;
        String orderStatus = invoiceFilter != null ? invoiceFilter.getOrderStatus() : null;
        String paymentMethod = invoiceFilter != null ? invoiceFilter.getPaymentMethod() : null;
        Date startInvoiceDate = invoiceFilter != null ? invoiceFilter.getStartInvoiceDate() : null;
        Date endInvoiceDate = invoiceFilter != null ? invoiceFilter.getEndInvoiceDate() : null;
        int page = pagination.getPage();
        int limit = pagination.getLimit();

        String result = "select * from invoice where ";

        Account currentAcc = valueRenderUtils.getCurrentAccountFromRequest(request);

        if (currentAcc.getRole().equals(RoleEnum.CUSTOMER.name())) {
            result += "Customer_ID = " + currentAcc.getCustomer().getId() + " and ";
        }

        if (paymentStatus != null && !paymentStatus.isBlank()) {
            result += "Payment_Status = '" + paymentStatus + "' and ";
        }

        if (adminAcceptance != null && !adminAcceptance.isBlank()) {
            result += "Admin_Acceptance = '" + adminAcceptance + "' and ";
        }

        if (orderStatus != null && !orderStatus.isBlank()) {
            result += "order_status = '" + orderStatus + "' and ";
        }

        if (paymentMethod != null && !paymentMethod.isBlank()) {
            result += "Payment_Method = '" + paymentMethod + "' and ";
        }

        if (startInvoiceDate != null) {
            result += "Invoice_Date >= '" + valueRenderUtils.formatDateToString(startInvoiceDate, "yyyy-MM-dd") + "' and ";
        }

        if (endInvoiceDate != null) {
            result += "Invoice_Date <= '" + valueRenderUtils.formatDateToString(endInvoiceDate, "yyyy-MM-dd") + "' and ";
        }

        // remove the final 'and' word in the query
        result = result.contains("and")
                ? result.substring(0, result.lastIndexOf("and"))
                : result.substring(0, result.lastIndexOf("where"));

        result += "order by id desc limit " + (limit * (page - 1)) + ", " + limit;

        return result;
    }


    // create a query for notification binding filter
    public String notificationFilterQuery(NotificationFilterDTO notiFilter, PaginationDTO pagination, Account account) {
        String result;

        if (account.getRole().equals(RoleEnum.CUSTOMER.name())) {
            result = "select n.* from notification n join login_accounts la on la.user_name = n.topic where topic = '" + account.getUsername() + "' and ";
        } else result = "select * from notification where topic = 'admin' and ";

        // from 00:00:00 of start date
        Date startDate = notiFilter != null
                ? valueRenderUtils.resetDate(notiFilter.getStartDate())
                : null;
        // to 23:59:59 of end date
        Date endDate = notiFilter != null
                ? new Date(valueRenderUtils.resetDate(notiFilter.getEndDate()).getTime() + (1000 * 60 * 60 * 24) - 1000)
                : null;
        int page = pagination.getPage();
        int limit = pagination.getLimit();

        if (startDate != null) {
            result += "notification_date >= '" + valueRenderUtils.formatDateToString(startDate, "yyyy-MM-dd HH:mm:ss") + "' and ";
        }

        if (endDate != null) {
            result += "notification_date <= '" + valueRenderUtils.formatDateToString(endDate, "yyyy-MM-dd HH:mm:ss") + "' and ";
        }

        // remove the final 'and' word in the query
        result = result.substring(0, result.lastIndexOf("and"));

        result += "order by id desc limit " + (limit * (page - 1)) + ", " + limit;

        return result;
    }


    // create a query for comments binding filter
    public String commentFilterQuery(CommentRequestDTO commentRequest, PaginationDTO pagination) {
        String result = "select * from comment_info_for_ui where ";
        int productId = commentRequest != null ? commentRequest.getProductId() : 0;
        int replyOn = commentRequest != null ? commentRequest.getReplyOn() : -1;
        String productColor = commentRequest != null ? commentRequest.getProductColor() : null;
        int page = pagination.getPage();
        int limit = pagination.getLimit();

        if (productColor != null && !productColor.isBlank()) {
            result += "product_color = '" + productColor.toLowerCase() + "' and ";
        }

        if (productId > 0) {
            result += "product_id = " + productId + " and ";
        }

        if (replyOn >= 0) {
            result += "reply_on = " + replyOn + " limit 0, " + limit * page + " and ";
        }

        // remove the final 'and' word in the query
        result = result.substring(0, result.lastIndexOf(result.contains("and") ? "and" : "where"));

        result += " limit " + (limit * (page - 1)) + ", " + limit;

        return result;
    }


    // create a query for products binding filter
    public String productFilterQuery(ProductFilterDTO productFilter, PaginationDTO pagination) {
        String result = "SELECT piu.*\n" +
                "FROM product_info_for_ui piu JOIN catalogs_with_products cwp ON piu.product_id = cwp.product_id\n" +
                "                             JOIN catalog c ON c.id = cwp.catalog_id\n" +
                "WHERE ";
        String[] catalogs = productFilter != null && productFilter.getCategories() != null ? productFilter.getCategories() : null;
        int catalogsLength = productFilter != null && catalogs != null ? catalogs.length : 0;
        String brand = productFilter != null ? productFilter.getBrand() : null;
        String name = productFilter != null ? productFilter.getName() : null;
        double price1 = productFilter != null ? productFilter.getMinPrice() : 0;
        double price2 = productFilter != null ? productFilter.getMaxPrice() : 0;
        int page = pagination.getPage();
        int limit = pagination.getLimit();

        if (brand != null && !brand.isBlank()) {
            result += " piu.brand = '" + brand + "' and ";
        }

        if (catalogsLength > 0) {
            result += "(";

            for (int i = 0; i < catalogsLength; i++) {
                if (i == catalogsLength - 1) {
                    result += "c.name = '" + catalogs[i] + "'";
                } else {
                    result += "c.name = '" + catalogs[i] + "' or ";
                }
            }

            result += ") and ";
        }


        if (price1 >= 0 && price2 > 0) {
            result += " piu.selling_price >= " + price1 + " and piu.selling_price <= " + price2 + " and ";
        }

        if (name != null && !name.isBlank()) {
            String[] subNameList = name.split(" ");

            for (String nameItem : subNameList) {
                result += "piu.name like '%" + nameItem + "%' and ";
            }
        }

        // remove the final 'and' word in the query
        result = result.substring(0, result.lastIndexOf(result.contains("and") ? "and" : "WHERE"));

        result += " ORDER BY piu.id desc LIMIT " + (limit * (page - 1)) + ", " + limit;

        return result;
    }


    // create a query for account binding filter
    public String accountFilterQuery(AccountFilterDTO accountFilter, PaginationDTO pagination) {
        String result;
        String status = accountFilter != null ? accountFilter.getStatus() : null;
        String city = accountFilter != null ? accountFilter.getCity() : null;
        String address = accountFilter != null ? accountFilter.getAddress() : null;
        String country = accountFilter != null ? accountFilter.getCountry() : null;
        String phoneNumber = accountFilter != null ? accountFilter.getPhoneNumber() : null;
        String email = accountFilter != null ? accountFilter.getEmail() : null;
        String name = accountFilter != null ? accountFilter.getName() : null;
        String userName = accountFilter != null ? accountFilter.getUserName() : null;
        int page = pagination.getPage();
        int limit = pagination.getLimit();

        if (pagination.getType().equals(RoleEnum.ADMIN.name())) {
            result = "select * from staff_info_for_ui where ";
            result += "position = '" + pagination.getType() + "' and ";
        } else if (pagination.getType().equals(RoleEnum.CUSTOMER.name())) {
            result = "select * from customer_info_for_ui where ";

            if (address != null && !address.isBlank()) {
                result += " address = '" + address + "' and ";
            }

            if (city != null && !city.isBlank()) {
                result += " city = '" + city + "' and ";
            }

            if (country != null && !country.isBlank()) {
                result += " country = '" + country + "' and ";
            }
        } else return null;

        if (status != null && !status.isBlank()) {
            result += " status = '" + status + "' and ";
        }

        if (name != null && !name.isBlank()) {
            result += " name = '" + name + "' and ";
        }

        if (userName != null && !userName.isBlank()) {
            result += " user_name = '" + userName + "' and ";
        }

        if (email != null && !email.isBlank()) {
            result += " email = '" + email + "' and ";
        }

        if (phoneNumber != null && !phoneNumber.isBlank()) {
            result += " phone_number = '" + phoneNumber + "' and ";
        }

        // remove the final 'and' word in the query
        result = result.substring(0, result.lastIndexOf(result.contains("and") ? "and" : "where"));

        if (page != 0 && limit != 0) {
            result += " ORDER BY id desc LIMIT " + (limit * (page - 1)) + ", " + limit;
        }

        return result;
    }


    // create a query for cart items binding filter
    public String cartItemFilterQuery(CartItemFilterDTO cartItemFilter, PaginationDTO pagination, Account account) {
        String result = "select * from cart_item_info_for_ui where buying_status = 'NOT_BOUGHT_YET' and customer_id = " + account.getCustomer().getId() + " and";
        String brand = cartItemFilter != null ? cartItemFilter.getBrand() : null;
        String name = cartItemFilter != null ? cartItemFilter.getName() : null;
        String[] status = cartItemFilter != null ? cartItemFilter.getStatus() : null;
        int page = pagination.getPage();
        int limit = pagination.getLimit();

        if (brand != null && !brand.isBlank()) {
            result += " brand = '" + brand.toLowerCase() + "' and";
        }

        if (name != null && !name.isBlank()) {
            result += " name = '" + name.toLowerCase() + "' and";
        }

        if (status != null && status.length > 0) {
            if (Arrays.stream(status).anyMatch(elem -> elem.equals(CartEnum.DISCOUNT.name()))) {
                result += " discount > 0 and";
            }

            if (Arrays.stream(status).anyMatch(elem -> elem.equals(CartEnum.SELECTED.name()))) {
                result += " select_status = 1 and";
            }
        }

        result = result.substring(0, result.lastIndexOf("and"));

        if (page != 0 && limit != 0) {
            result += " ORDER BY id desc LIMIT " + (limit * (page - 1)) + ", " + limit;
        }

        return result;
    }


    // create a query for refund binding filter
    public String refundFilterQuery(RefundFilterDTO refundFilter, PaginationDTO pagination) {
        String result = "";

        int limit = pagination.getLimit();
        int page = pagination.getPage();

        if (refundFilter != null) {
            result = "select r.* from refund r join invoice i on r.invoice_id = i.id where ";
            Date refundDate = refundFilter.getRefundDate();
            String paymentMethod = refundFilter.getPaymentMethod();
            String reason = refundFilter.getReason();
            int invoiceId = refundFilter.getInvoiceId();
            int adminId = refundFilter.getAdminId();

            if (paymentMethod != null && !paymentMethod.isBlank()) {
                result += "payment_method = '" + paymentMethod + "' and ";
            }

            if (reason != null && !reason.isBlank()) {
                result += "reason = '" + reason + "' and ";
            }

            if (refundDate != null) {
                result += "DATE(date) = '" + valueRenderUtils.formatDateToString(refundDate, "yyyy-MM-dd") + "' and ";
            }

            if (invoiceId > 0) {
                result += "invoice_id = " + invoiceId + " and ";
            }

            if (adminId > 0) {
                result += "in_charge_admin_id = " + adminId + " and ";
            }

            result = result.substring(0, result.lastIndexOf(result.contains("and") ? "and" : "where"));
        } else {
            result = "select * from refund ";
        }

        if (page != 0 && limit != 0) {
            result += " ORDER BY id desc LIMIT " + (limit * (page - 1)) + ", " + limit;
        }

        return result;
    }
}
