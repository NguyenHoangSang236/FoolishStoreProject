package com.backend.core.util.process;

import com.backend.core.entities.interfaces.FilterRequest;
import com.backend.core.entities.requestdto.FilterFactory;
import com.backend.core.entities.requestdto.PaginationDTO;
import com.backend.core.entities.requestdto.account.AccountFilterDTO;
import com.backend.core.entities.requestdto.cart.CartItemFilterDTO;
import com.backend.core.entities.requestdto.comment.CommentRequestDTO;
import com.backend.core.entities.requestdto.invoice.InvoiceFilterDTO;
import com.backend.core.entities.requestdto.notification.NotificationFilterDTO;
import com.backend.core.entities.requestdto.product.ProductFilterDTO;
import com.backend.core.entities.requestdto.refund.RefundFilterDTO;
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


    //get customer id from request
    public int getCustomerOrStaffIdFromRequest(HttpServletRequest request) {
        try {
            Account currentUser = getCurrentAccountFromRequest(request);

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


    // get current account from request
    public Account getCurrentAccountFromRequest(HttpServletRequest request) {
        try {
            String jwt = jwtUtils.getJwtFromRequest(request);
            String userName = jwtUtils.getUserNameFromJwt(jwt);

            Account currentUser = accountRepo.getAccountByUserName(userName);

            return currentUser;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //create a query for invoice binding filter
    public String invoiceFilterQuery(InvoiceFilterDTO invoiceFilter, PaginationDTO pagination, HttpServletRequest request) {
        String paymentStatus = invoiceFilter.getPaymentStatus();
        String adminAcceptance = invoiceFilter.getAdminAcceptance();
        String orderStatus = invoiceFilter.getOrderStatus();
        String paymentMethod = invoiceFilter.getPaymentMethod();
        Date startInvoiceDate = invoiceFilter.getStartInvoiceDate();
        Date endInvoiceDate = invoiceFilter.getEndInvoiceDate();
        int page = pagination.getPage();
        int limit = pagination.getLimit();

        String result = "select * from invoice where ";

        Account currentAcc = getCurrentAccountFromRequest(request);

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
            result += "Invoice_Date >= '" + formatDateToString(startInvoiceDate, "yyyy-MM-dd") + "' and ";
        }

        if (endInvoiceDate != null) {
            result += "Invoice_Date <= '" + formatDateToString(endInvoiceDate, "yyyy-MM-dd") + "' and ";
        }

        // remove the final 'and' word in the query
        result = result.substring(0, result.lastIndexOf("and"));

        result += "order by id desc limit " + (limit * (page - 1)) + ", " + limit;

        System.out.println(result);

        return result;
    }


    // create a query for notification binding filter
    public String notificationFilterQuery(NotificationFilterDTO notiFilter, PaginationDTO pagination, Account account) {
        String result;

        if (account.getRole().equals(RoleEnum.CUSTOMER.name())) {
            result = "select n.* from notification n join login_accounts la on la.user_name = n.topic where topic = '" + account.getUsername() + "' and ";
        } else result = "select * from notification where topic = 'admin' and ";

        // from 00:00:00 of start date
        Date startDate = resetDate(notiFilter.getStartDate());

        // to 23:59:59 of end date
        Date endDate = new Date(resetDate(notiFilter.getEndDate()).getTime() + (1000 * 60 * 60 * 24) - 1000);

        int page = pagination.getPage();
        int limit = pagination.getLimit();

        if (startDate != null) {
            result += "notification_date >= '" + formatDateToString(startDate, "yyyy-MM-dd HH:mm:ss") + "' and ";
        }

        if (endDate != null) {
            result += "notification_date <= '" + formatDateToString(endDate, "yyyy-MM-dd HH:mm:ss") + "' and ";
        }

        // remove the final 'and' word in the query
        result = result.substring(0, result.lastIndexOf("and"));

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

        if (brand != null && !brand.isBlank()) {
            result += " piu.brand = '" + brand + "' and ";
        }

        if (catalogs == null) {
            catalogsLength = 0;
        } else catalogsLength = catalogs.length;

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
                result += "date = '" + formatDateToString(refundDate, "yyyy-MM-dd") + "' and ";
            }

            if (invoiceId > 0) {
                result += "invoice_id = " + invoiceId + " and ";
            }

            if (adminId > 0) {
                result += "in_charge_admin_id = " + adminId + " and ";
            }

            result = result.substring(0, result.lastIndexOf("and"));
        } else {
            result = "select * from refund ";
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

            Account currentAccount = getCurrentAccountFromRequest(request);

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
                        filterQuery = cartItemFilterQuery((CartItemFilterDTO) filter, pagination);
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


    // reset Date's hour value to 0
    public Date resetDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }
}