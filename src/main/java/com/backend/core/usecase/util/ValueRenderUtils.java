package com.backend.core.usecase.util;

import com.backend.core.entity.account.model.Account;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.infrastructure.business.account.repository.AccountRepository;
import com.backend.core.infrastructure.business.product.repository.ProductRenderInfoRepository;
import com.backend.core.infrastructure.config.database.CustomQueryRepository;
import com.backend.core.usecase.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    final JwtService jwtService;


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


    //format string to link
    public String stringToLink(String link) {
        String result = " ";
        char[] linkCharArr = link.toCharArray();

        for (int i = 0; i < linkCharArr.length; i++) {
            if (linkCharArr[i] == ' ') {
                linkCharArr[i] = '_';
            }
        }
        result = String.valueOf(linkCharArr);

        return result;
    }


    //format link to string
    public String linkToString(String link) {
        String result = " ";
        char[] linkCharArr = link.toCharArray();

        for (int i = 0; i < linkCharArr.length; i++) {
            if (linkCharArr[i] == '_') {
                linkCharArr[i] = ' ';
            }
        }
        result = String.valueOf(linkCharArr);

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
        double kb = (double) size / n;
        double mb = kb / n;
        double gb = mb / n;
        double tb = gb / n;
        if (size < n) {
            s = size + " Bytes";
        } else if (size < n * n) {
            s = String.format("%.1f", kb) + " KB";
        } else if (size < n * n * n) {
            s = String.format("%.1f", mb) + " MB";
        } else if (size < n * n * n * n) {
            s = String.format("%.2f", gb) + " GB";
        } else {
            s = String.format("%.2f", tb) + " TB";
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
            String jwt = jwtService.getJwtFromRequest(request);
            String userName = jwtService.getUserNameFromJwt(jwt);

            Account currentUser = accountRepo.getAccountByUserName(userName);

            return currentUser;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
        return "https://drive.google.com/uc?export=view&id=" + fileId;
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


    // parse object to json string
    public String parseObjectToString(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse res = new ApiResponse("failed", "Error at JSON parsing", HttpStatus.INTERNAL_SERVER_ERROR);
            return res.toString();
        }

    }


    // parse json string to object
    public <T> T parseJsonStringToObject(String json, Class<T> objectClass) {
        try {
            return new Gson().fromJson(json, objectClass);
        } catch (Exception e) {
            return null;
        }
    }
}