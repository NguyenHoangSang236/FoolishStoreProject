package com.backend.core.util.process;

import com.backend.core.entities.interfaces.FilterRequest;
import com.backend.core.entities.requestdto.FilterFactory;
import com.backend.core.entities.requestdto.cart.CartItemFilterDTO;
import com.backend.core.entities.requestdto.comment.CommentRequestDTO;
import com.backend.core.entities.requestdto.invoice.InvoiceFilterDTO;
import com.backend.core.entities.requestdto.product.ProductFilterDTO;
import com.backend.core.entities.tableentity.Account;
import com.backend.core.enums.CartEnum;
import com.backend.core.enums.ErrorTypeEnum;
import com.backend.core.enums.FilterTypeEnum;
import com.backend.core.repository.account.AccountRepository;
import com.backend.core.repository.customQuery.CustomQueryRepository;
import com.backend.core.repository.product.ProductRenderInfoRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ValueRenderUtils {
    final CustomQueryRepository customQueryRepo;

    final ProductRenderInfoRepository productRenderInfoRepo;

    final AccountRepository accountRepo;

    final JwtUtils jwtUtils;

    final CheckUtils checkUtils;



    //format person's full name (trim, remove unnecessary spaces and capitalize fist letters)
    public String formattedPersonFullName(String name) {
        name = checkUtils.trimmedInputString(name);
        char[] charArr = name.toCharArray();
        char[] resultCharArr = new char[charArr.length];
        int resultIndex = 0;

        for (int i = 0; i < charArr.length; i++) {
            if (i == 0) {
                if (Character.isLowerCase(charArr[i])) {
                    resultCharArr[resultIndex] = Character.toUpperCase(charArr[i]);
                    resultIndex++;
                }
            } else if (i < charArr.length - 1) {
                if (!Character.isWhitespace(charArr[i - 1]) && Character.isUpperCase(charArr[i])) {
                    resultCharArr[resultIndex] = Character.toLowerCase(charArr[i]);
                    resultIndex++;
                } else if (!Character.isWhitespace(charArr[i - 1]) && Character.isLowerCase(charArr[i])) {
                    resultCharArr[resultIndex] = charArr[i];
                    resultIndex++;
                } else if (Character.isWhitespace(charArr[i - 1]) && Character.isLowerCase(charArr[i])) {
                    resultCharArr[resultIndex] = Character.toUpperCase(charArr[i]);
                    resultIndex++;
                } else if (Character.isWhitespace(charArr[i]) && !Character.isWhitespace(charArr[i - 1])) {
                    resultCharArr[resultIndex] = ' ';
                    resultIndex++;
                }
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
            s = String.format("%.1f", kb) + " KB";
        } else if (size >= (n * n) && size < (n * n * n)) {
            s = String.format("%.1f", mb) + " MB";
        } else if (size >= (n * n * n) && size < (n * n * n * n)) {
            s = String.format("%.2f", gb) + " GB";
        } else if (size >= (n * n * n * n)) {
            s = String.format("%.2f", tb) + " TB";
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
    public String invoiceFilterQuery(int customerId, String adminAcceptance, int paymentStatus, String deliveryStatus, Date startInvoiceDate, Date endInvoiceDate, String paymentMethod, int page, int limit) {
        String result = "select * from invoice where Customer_ID = " + customerId + " and Payment_Status = " + paymentStatus;

        if (adminAcceptance != null) {
            result += " and Admin_Acceptance = '" + adminAcceptance + "' ";
        }

        if (deliveryStatus != null) {
            result += " and Delivery_Status = '" + deliveryStatus + "' ";
        }

        if (paymentMethod != null) {
            result += " and Payment_Method = '" + paymentMethod + "' ";
        }

        if (startInvoiceDate != null) {
            result += " and Invoice_Date >= '" + formatDateToString(startInvoiceDate, "yyyy-MM-dd") + "' ";
        }

        if (endInvoiceDate != null) {
            result += " and Invoice_Date <= '" + formatDateToString(endInvoiceDate, "yyyy-MM-dd") + "' ";
        }

        result += "limit " + (limit * (page - 1)) + ", " + limit;

        return result + ';';
    }


    // create a query for comments binding filter
    public String commentFilterQuery(int productId, String productColor, int replyOn, int page, int limit) {
        String result = "select * from comments where ";

        if (productColor != null && !productColor.isEmpty() && !productColor.isBlank()) {
            result += "product_color = '" + productColor.toLowerCase() + "' and ";
        }

        if (productId > 0) {
            result += "product_id = " + productId + " and ";
        }

        result += "reply_on = " + replyOn + " limit " + (limit * (page - 1)) + ", " + limit;

        return result;
    }


    // create a query for products binding filter
    public String productFilterQuery(String[] catalogs, String name, String brand, double price1, double price2, int page, int limit) {
        String result = "SELECT piu.*\n" +
                "FROM product_info_for_ui piu JOIN catalogs_with_products cwp ON piu.product_id = cwp.product_id\n" +
                "                             JOIN catalog c ON c.id = cwp.catalog_id\n" +
                "WHERE ";
        StringBuilder dynamicConditions = new StringBuilder();
        int catalogsLength;

        if (catalogs == null) {
            catalogsLength = 0;
        } else catalogsLength = catalogs.length;

        if (brand != null && !brand.isBlank() && !brand.isEmpty()) {
            dynamicConditions.append("and piu.brand = '").append(brand).append("' ");
        }

        for (int i = 0; i < catalogsLength; i++) {
            if (i == 0) {
                dynamicConditions.append("and (c.name = '").append(catalogs[i]).append("'");
            } else dynamicConditions.append(" or c.name = '").append(catalogs[i]).append("'");

            if (i == catalogsLength - 1) {
                dynamicConditions.append(") ");
            }
        }

        if (price1 >= 0 && price2 > 0) {
            dynamicConditions.append(" and piu.selling_price >= ").append(price1).append(" and piu.selling_price <= ").append(price2);
        }

        if (name != null && !name.isBlank() && !name.isEmpty()) {
            dynamicConditions.append(" and piu.name like '%").append(name).append("%'");
        }

        dynamicConditions = new StringBuilder(dynamicConditions.substring(4));

        result += dynamicConditions + " ORDER BY piu.id desc LIMIT " + (limit * (page - 1)) + ", " + limit;

        return result;
    }


    //create a query for cart items binding filter
    public String cartItemFilterQuery(String name, String[] status, String brand, int page, int limit) {
        String result = "select * from cart_item_info_for_ui where ";

        if (brand != null && !brand.isEmpty() && !brand.isBlank()) {
            result += "brand = '" + brand.toLowerCase() + "' and ";
        }

        if (name != null && !name.isEmpty() && !name.isBlank()) {
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

        if (page != 0 && limit != 0) {
            result += " ORDER BY id desc LIMIT " + (limit * (page - 1)) + ", " + limit;
        }

        int lastIndex = result.lastIndexOf("and");
        if (lastIndex >= 0) {
            result = result.substring(0, lastIndex) + result.substring(lastIndex + "and".length());
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
    public String getFilterQuery(Object paramObj, FilterTypeEnum filterType, HttpServletRequest request) {
        // get current customer id
        int customerId = getCustomerOrStaffIdFromRequest(request);

        String filterQuery = ErrorTypeEnum.TECHNICAL_ERROR.name();

        // initiate filter type
        FilterRequest filterRequest = FilterFactory.getFilterRequest(filterType);

        // determine filter request class type
        Class<? extends FilterRequest> filterRequestEntity = filterRequest.getClass();

        // convert paramObj
        if (filterRequestEntity.isInstance(paramObj)) {
            filterRequest = filterRequestEntity.cast(paramObj);

            Object filter = filterRequest.getFilter();

            int page = filterRequest.getPagination().getPage();
            int limit = filterRequest.getPagination().getLimit();

            try {
                switch (filterType) {
                    case INVOICE -> {
                        InvoiceFilterDTO invoiceFilter = (InvoiceFilterDTO) filter;

                        filterQuery = invoiceFilterQuery(
                                customerId,
                                invoiceFilter.getAdminAcceptance(),
                                invoiceFilter.getPaymentStatus(),
                                invoiceFilter.getDeliveryStatus(),
                                invoiceFilter.getStartInvoiceDate(),
                                invoiceFilter.getEndInvoiceDate(),
                                invoiceFilter.getPaymentMethod(),
                                page, limit
                        );
                    }
                    case PRODUCT -> {
                        ProductFilterDTO productFilter = (ProductFilterDTO) filter;

                        // product filter does not have Name field and other fields must have value -> binding filter
                        if ((productFilter.getName() == null || productFilter.getName().isBlank()) &&
                                (productFilter.getCategories() != null ||
                                        productFilter.getBrand() != null ||
                                        (productFilter.getMinPrice() > 0 &&
                                                productFilter.getMaxPrice() > 0 &&
                                                productFilter.getMinPrice() < productFilter.getMaxPrice()))) {
                            // get filter query
                            filterQuery = productFilterQuery(
                                    productFilter.getCategories(),
                                    productFilter.getName(),
                                    productFilter.getBrand(),
                                    productFilter.getMinPrice(),
                                    productFilter.getMaxPrice(),
                                    page, limit
                            );
                        }
                        // else -> search product by Name
                        else {
                            if (Objects.equals(productFilter.getName(), "")) {
                                return filterQuery;
                            } else {
                                filterQuery = productFilterQuery(
                                        productFilter.getCategories(),
                                        productFilter.getName(),
                                        productFilter.getBrand(),
                                        productFilter.getMinPrice(),
                                        productFilter.getMaxPrice(),
                                        page, limit
                                );
                            }
                        }
                    }
                    case CART_ITEMS -> {
                        CartItemFilterDTO cartItemFilter = (CartItemFilterDTO) filter;

                        String name = cartItemFilter.getName();
                        String brand = cartItemFilter.getBrand();
                        String[] status = cartItemFilter.getStatus();

                        filterQuery = cartItemFilterQuery(name, status, brand, page, limit);

                    }
                    case DELIVERY -> {
                    }
                    case COMMENT -> {
                        CommentRequestDTO commentRequest = (CommentRequestDTO) filter;

                        int productId = commentRequest.getProductId();
                        int replyOn = commentRequest.getReplyOn();
                        String productColor = commentRequest.getProductColor();

                        filterQuery = commentFilterQuery(productId, productColor, replyOn, page, limit);
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
}
