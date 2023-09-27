package com.backend.core.util.process;

import com.backend.core.entities.interfaces.FilterRequest;
import com.backend.core.entities.requestdto.FilterFactory;
import com.backend.core.entities.requestdto.PaginationDTO;
import com.backend.core.entities.requestdto.account.AccountFilterDTO;
import com.backend.core.entities.requestdto.cart.CartItemFilterDTO;
import com.backend.core.entities.requestdto.comment.CommentRequestDTO;
import com.backend.core.entities.requestdto.delivery.DeliveryFilterDTO;
import com.backend.core.entities.requestdto.invoice.InvoiceFilterDTO;
import com.backend.core.entities.requestdto.product.ProductFilterDTO;
import com.backend.core.entities.tableentity.Account;
import com.backend.core.enums.CartEnum;
import com.backend.core.enums.ErrorTypeEnum;
import com.backend.core.enums.FilterTypeEnum;
import com.backend.core.enums.RoleEnum;
import com.backend.core.repository.account.AccountRepository;
import com.backend.core.repository.customQuery.CustomQueryRepository;
import com.backend.core.repository.product.ProductRenderInfoRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ValueRenderUtils {
    final CustomQueryRepository customQueryRepo;
    final ProductRenderInfoRepository productRenderInfoRepo;
    final AccountRepository accountRepo;
    final JwtUtils jwtUtils;
    FilterFactory filterFactory;


    //remove spaces at the beginning of the input text
    public String fullyTrimmedString(String input) {
        int count = 0;

        char[] charArr = input.toCharArray();
        char[] resultCharArr = new char[charArr.length];

        for (int i = 0; i < charArr.length - 1; i++) {
            if (i > 0) {
                if (charArr[i] != ' ' || (charArr[i] == ' ' && charArr[i - 1] != ' ')) {
                    resultCharArr[count] = charArr[i];
                    count++;
                }
            } else {
                if (charArr[i] != ' ') {
                    resultCharArr[count] = charArr[i];
                    count++;
                }
            }
        }

        return String.copyValueOf(resultCharArr).trim();
    }


    //format person's full name (trim, remove unnecessary spaces and capitalize fist letters)
    public String capitalizeFirstLetterOfEachWord(String name) {
        name = fullyTrimmedString(name);
        char[] charArr = name.toCharArray();
        char[] resultCharArr = new char[charArr.length];
        int resultIndex = 0;

        for (int i = 0; i < charArr.length; i++) {
            if (i == 0) {
                if (Character.isLowerCase(charArr[i])) {
                    resultCharArr[resultIndex] = Character.toUpperCase(charArr[i]);
                } else {
                    resultCharArr[resultIndex] = charArr[i];
                }
                resultIndex++;
            } else if (i < charArr.length - 1) {
                if (!Character.isWhitespace(charArr[i - 1]) && Character.isUpperCase(charArr[i])) {
                    resultCharArr[resultIndex] = Character.toLowerCase(charArr[i]);
                } else if (Character.isWhitespace(charArr[i - 1]) && Character.isLowerCase(charArr[i])) {
                    resultCharArr[resultIndex] = Character.toUpperCase(charArr[i]);
                } else {
                    resultCharArr[resultIndex] = charArr[i];
                }
                resultIndex++;
            } else {
                if (Character.isUpperCase(charArr[i])) {
                    resultCharArr[resultIndex] = Character.toLowerCase(charArr[i]);
                } else {
                    resultCharArr[resultIndex] = charArr[i];
                }
            }
        }

        return String.valueOf(resultCharArr);
    }


    //encode the password
    public String encodePassword(String pass) {
        char[] charArr = pass.toCharArray();
        String result;

        for (int i = 0; i < charArr.length; i++) {
            charArr[i] = (char) (charArr[i] + 5);
        }
        result = charArr.toString();

        return result;
    }


    //decode the password
    public String decodePassword(String pass) {
        char[] charArr = pass.toCharArray();
        String result;

        for (int i = 0; i < charArr.length; i++) {
            charArr[i] = (char) (charArr[i] - 5);
        }
        result = String.valueOf(charArr);

        return result;
    }


    //format string to link
    public String stringToLink(String link) {
        String result = " ";
        char[] linkCharrArr = link.toCharArray();

        for (int i = 0; i < linkCharrArr.length; i++) {
            if (linkCharrArr[i] == ' ') {
                linkCharrArr[i] = '_';
            }
        }
        result = String.valueOf(linkCharrArr);

        return result;
    }


    //format link to string
    public String linkToString(String link) {
        String result = " ";
        char[] linkCharrArr = link.toCharArray();

        for (int i = 0; i < linkCharrArr.length; i++) {
            if (linkCharrArr[i] == '_') {
                linkCharrArr[i] = ' ';
            }
        }
        result = String.valueOf(linkCharrArr);

        return result;
    }


    //format DateTime
    public String formatDateToString(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }


    //convert String to byte[]
    public byte[] convertStringToByteArray(String input) {
        return input.getBytes(StandardCharsets.UTF_8);
    }


    //convert byte[] to String
    public String convertByteToString(byte[] input) {
        return new String(input, StandardCharsets.UTF_8);
    }


    //convert byte[] to KB, MB, GB, TB
    public String getConvertedDataSize(long size) {
        long n = 1024;
        String s = "";
        double kb = size / n;
        double mb = kb / n;
        double gb = mb / n;
        double tb = gb / n;
        if (size < n) {
            s = size + " Bytes";
        } else if (size >= n && size < (n * n)) {
            s = String.format("%.1f", Double.valueOf(kb)) + " KB";
        } else if (size >= (n * n) && size < (n * n * n)) {
            s = String.format("%.1f", Double.valueOf(mb)) + " MB";
        } else if (size >= (n * n * n) && size < (n * n * n * n)) {
            s = String.format("%.2f", Double.valueOf(gb)) + " GB";
        } else if (size >= (n * n * n * n)) {
            s = String.format("%.2f", Double.valueOf(tb)) + " TB";
        }
        return s;
    }


    //get customer id from HttpSession
    public int getCustomerOrStaffIdFromRequest(HttpServletRequest request) {
        try {
            String jwt = jwtUtils.getJwtFromRequest(request);
            String userName = jwtUtils.getUserNameFromJwt(jwt);

            Account currentUser = accountRepo.getAccountByUserName(userName);

            if (currentUser != null) {
                if (currentUser.getCustomer() != null) {
                    return currentUser.getCustomer().getId();
                } else {
                    return currentUser.getStaff().getId();
                }
            } else return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    //create a query for invoice binding filter
    public String invoiceFilterQuery(int customerId, InvoiceFilterDTO invoiceFilter, PaginationDTO pagination) {
        String paymentStatus = invoiceFilter.getPaymentStatus();
        String adminAcceptance = invoiceFilter.getAdminAcceptance();
        String deliveryStatus = invoiceFilter.getDeliveryStatus();
        String paymentMethod = invoiceFilter.getPaymentMethod();
        Date startInvoiceDate = invoiceFilter.getStartInvoiceDate();
        Date endInvoiceDate = invoiceFilter.getEndInvoiceDate();
        int page = pagination.getPage();
        int limit = pagination.getLimit();

        String result = "select * from invoice where Customer_ID = " + customerId + " ";

        if (paymentStatus != null) {
            result += " and Payment_Status = '" + paymentStatus + "' ";
        }

        if (adminAcceptance != null) {
            result += "and Admin_Acceptance = '" + adminAcceptance + "' ";
        }

        if (deliveryStatus != null) {
            result += "and Delivery_Status = '" + deliveryStatus + "' ";
        }

        if (paymentMethod != null) {
            result += "and Payment_Method = '" + paymentMethod + "' ";
        }

        if (startInvoiceDate != null) {
            result += "and Invoice_Date >= '" + formatDateToString(startInvoiceDate, "yyyy-MM-dd") + "' ";
        }

        if (endInvoiceDate != null) {
            result += "and Invoice_Date <= '" + formatDateToString(endInvoiceDate, "yyyy-MM-dd") + "' ";
        }

        result += "order by id desc limit " + (limit * (page - 1)) + ", " + limit;

        return result;
    }


    // create a query for comments binding filter
    public String commentFilterQuery(CommentRequestDTO commentRequest, PaginationDTO pagination) {
        String result = "select * from comment_info_for_ui where ";
        int productId = commentRequest.getProductId();
        int replyOn = commentRequest.getReplyOn();
        String productColor = commentRequest.getProductColor();
        int page = pagination.getPage();
        int limit = pagination.getLimit();

        if (productColor != null && !productColor.isBlank()) {
            result += "product_color = '" + productColor.toLowerCase() + "' and ";
        }

        if (productId > 0) {
            result += "product_id = " + productId + " and ";
        }

        result += "reply_on = " + replyOn + " limit 0, " + limit * page;

        return result;
    }


    // create a query for products binding filter
    public String productFilterQuery(ProductFilterDTO productFilter, PaginationDTO pagination) {
        String result = "SELECT piu.*\n" +
                "FROM product_info_for_ui piu JOIN catalogs_with_products cwp ON piu.product_id = cwp.product_id\n" +
                "                             JOIN catalog c ON c.id = cwp.catalog_id\n" +
                "WHERE ";
        int catalogsLength;
        String[] catalogs = productFilter.getCategories();
        String brand = productFilter.getBrand();
        String name = productFilter.getName();
        double price1 = productFilter.getMinPrice();
        double price2 = productFilter.getMaxPrice();
        int page = pagination.getPage();
        int limit = pagination.getLimit();

        if (catalogs == null) {
            catalogsLength = 0;
        } else catalogsLength = catalogs.length;

        if (brand != null && !brand.isBlank()) {
            result += "and piu.brand = '" + brand + "' and ";
        }

        for (int i = 0; i < catalogsLength; i++) {
            if (catalogsLength >= 2) {
                if (i == 0) {
                    result += "(c.name = '" + catalogs[i] + "' or ";
                } else if (i == catalogsLength - 1) {
                    result += "c.name = '" + catalogs[i] + "') and ";
                } else {
                    result += "c.name = '" + catalogs[i] + "' or ";
                }
            } else {
                result += "c.name = '" + catalogs[i] + "' and ";
            }
        }

        if (price1 >= 0 && price2 > 0) {
            result += " piu.selling_price >= " + price1 + " and piu.selling_price <= " + price2 + " and ";
        }

        if (name != null && !name.isBlank()) {
            result += "piu.name like '%" + name + "%' and ";
        }

        // remove the final 'and' word in the query
        result = result.substring(0, result.lastIndexOf("and"));

        result += " ORDER BY piu.id desc LIMIT " + (limit * (page - 1)) + ", " + limit;

        return result;
    }


    // create a query for account binding filter
    public String accountFilterQuery(AccountFilterDTO accountFilter, PaginationDTO pagination) {
        String result;
        String status = accountFilter.getStatus();
        String city = accountFilter.getCity();
        String address = accountFilter.getAddress();
        String country = accountFilter.getCountry();
        String phoneNumber = accountFilter.getPhoneNumber();
        String email = accountFilter.getEmail();
        String name = accountFilter.getName();
        String userName = accountFilter.getUserName();
        int page = pagination.getPage();
        int limit = pagination.getLimit();

        if (pagination.getType().equals(RoleEnum.ADMIN.name()) || pagination.getType().equals(RoleEnum.SHIPPER.name())) {
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
        result = result.substring(0, result.lastIndexOf("and"));

        if (page != 0 && limit != 0) {
            result += " ORDER BY id desc LIMIT " + (limit * (page - 1)) + ", " + limit;
        }


        return result;
    }


    // create a query for cart items binding filter
    public String cartItemFilterQuery(CartItemFilterDTO cartItemFilter, PaginationDTO pagination) {
        String result = "select * from cart_item_info_for_ui where buying_status = 'NOT_BOUGHT_YET' and ";
        String brand = cartItemFilter.getBrand();
        String name = cartItemFilter.getName();
        String[] status = cartItemFilter.getStatus();
        int page = pagination.getPage();
        int limit = pagination.getLimit();

        if (brand != null && !brand.isBlank()) {
            result += "brand = '" + brand.toLowerCase() + "' and ";
        }

        if (name != null && !name.isBlank()) {
            result += "name = '" + brand.toLowerCase() + "' and ";
        }

        if (status != null && status.length > 0) {
            if (Arrays.stream(status).anyMatch(elem -> elem.equals(CartEnum.DISCOUNT.name()))) {
                result += "discount > 0 and ";
            }

            if (Arrays.stream(status).anyMatch(elem -> elem.equals(CartEnum.SELECTED.name()))) {
                result += "select_status = 1 and ";
            }
        }

        result = result.substring(0, result.lastIndexOf("and"));

        if (page != 0 && limit != 0) {
            result += " ORDER BY id desc LIMIT " + (limit * (page - 1)) + ", " + limit;
        }

        return result;
    }


    // create a query for delivery binding filter
    public String deliveryFilterQuery(DeliveryFilterDTO deliveryFilter, PaginationDTO pagination, int shipperId) {
        String result = "select * from delivery_info_for_ui where ";

        String country = deliveryFilter.getCountry();
        String address = deliveryFilter.getAddress();
        String city = deliveryFilter.getCity();
        String paymentStatus = deliveryFilter.getPaymentStatus();
        String paymentMethod = deliveryFilter.getPaymentMethod();
        String type = pagination.getType();
        int limit = pagination.getLimit();
        int page = pagination.getPage();

        if (country != null && !country.isBlank()) {
            result += "country = '" + country + "' and ";
        }

        if (address != null && !address.isBlank()) {
            result += "address = '" + address + "' and ";
        }

        if (city != null && !city.isBlank()) {
            result += "city = '" + city + "' and ";
        }

        if (paymentMethod != null && !paymentMethod.isBlank()) {
            result += "payment_method = '" + paymentMethod + "' and ";
        }

        if (paymentStatus != null && !paymentStatus.isBlank()) {
            result += "payment_status = '" + paymentStatus + "' and ";
        }

        switch (type.toUpperCase()) {
            case "SHIP_HISTORY" -> result += "shipper_id = " + shipperId;
            case "MY_ORDERS" ->
                    result += "shipper_id = " + shipperId + " and (current_delivery_status = 'TAKE_ORDER' or current_delivery_status = 'SHIPPING') and shipper_id = " + shipperId;
            case "MY_SUCCESSFUL_ORDERS" ->
                    result += "(current_delivery_status = 'SHIPPED' and invoice_delivery_status = 'SHIPPED') and shipper_id = " + shipperId;
            case "MY_FAILED_ORDERS" ->
                    result += "(current_delivery_status = 'FAILED' and invoice_delivery_status = 'FAILED') and shipper_id = " + shipperId;
        }

        if (page != 0 && limit != 0) {
            result += " ORDER BY id desc LIMIT " + (limit * (page - 1)) + ", " + limit;
        }

        return result;
    }


    //create a random temporary password
    public String randomTemporaryPassword(String userName) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());

            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }


    // get gg drive url from file ID
    public String getGoogleDriveUrlFromFileId(String fileId) {
        return "https://drive.google.com/file/d/" + fileId + "/view?usp=sharing";
    }


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

            int userId = 0;

            if (authen) {
                // get current customer or staff id
                userId = getCustomerOrStaffIdFromRequest(request);
            }

            try {
                switch (filterType) {
                    case INVOICE -> {
                        filterQuery = invoiceFilterQuery(userId, (InvoiceFilterDTO) filter, pagination);
                    }
                    case PRODUCT -> {
                        filterQuery = productFilterQuery((ProductFilterDTO) filter, pagination);
                    }
                    case CART_ITEMS -> {
                        filterQuery = cartItemFilterQuery((CartItemFilterDTO) filter, pagination);
                    }
                    case DELIVERY -> {
                        filterQuery = deliveryFilterQuery((DeliveryFilterDTO) filter, pagination, userId);
                    }
                    case COMMENT -> {
                        filterQuery = commentFilterQuery((CommentRequestDTO) filter, pagination);
                    }
                    case ACCOUNT -> {
                        filterQuery = accountFilterQuery((AccountFilterDTO) filter, pagination);
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


    // generate start line for query from limit and page
    public int getStartLineForQueryPagination(int limit, int page) {
        return (page - 1) * limit;
    }


    // generate a string List from an Array
    public List<String> getStringListFromArray(String[] arr) {
        List<String> result = new ArrayList<>();

        Collections.addAll(result, arr);

        return result;
    }


    // generate an integer List from an Array
    public List<Integer> getIntegerListFromArray(int[] arr) {
        List<Integer> result = new ArrayList<>();

        for (int elem : arr) {
            result.add(Integer.valueOf(elem));
        }

        return result;
    }
}
