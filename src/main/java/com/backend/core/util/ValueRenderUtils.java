package com.backend.core.util;

import com.backend.core.entity.Account;
import jakarta.servlet.http.HttpSession;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class ValueRenderUtils {
    //format person's full name (trim, remove unnecessary spaces and capitalize fist letters)
    public static String formattedPersonFullName(String name) {
        name = CheckUtils.trimmedInputString(name);
        char[] charArr = name.toCharArray();
        char[] resultCharArr = new char[charArr.length];
        int resultIndex = 0;

        for(int i = 0; i < charArr.length; i++) {
            if(i == 0) {
                if(Character.isLowerCase(charArr[i])) {
                    resultCharArr[resultIndex] = Character.toUpperCase(charArr[i]);
                    resultIndex++;
                }
            }
            else if (i < charArr.length - 1){
                if(!Character.isWhitespace(charArr[i - 1]) && Character.isUpperCase(charArr[i])) {
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
            }
            else {
                if(Character.isUpperCase(charArr[i])) {
                    resultCharArr[resultIndex] = Character.toLowerCase(charArr[i]);
                } else {
                    resultCharArr[resultIndex] = charArr[i];
                }
            }
        }
        return String.valueOf(resultCharArr);
    }


    //encode the password
    public static String encodePassword(String pass) {
        char[] charArr = pass.toCharArray();
        String result;

        for(int i = 0; i < charArr.length; i++) {
            charArr[i] = (char) (charArr[i] + 5);
        }
        result = charArr.toString();

        return result;
    }


    //decode the password
    public static String decodePassword(String pass) {
        char[] charArr = pass.toCharArray();
        String result;

        for(int i = 0; i < charArr.length; i++) {
            charArr[i] = (char) (charArr[i] - 5);
        }
        result = String.valueOf(charArr);

        return result;
    }


    //format string to link
    public static String stringToLink(String link) {
        String result = " ";
        char[] linkCharrArr = link.toCharArray();

        for(int i = 0; i < linkCharrArr.length; i++) {
            if(linkCharrArr[i] == ' ') {
                linkCharrArr[i] = '_';
            }
        }
        result = String.valueOf(linkCharrArr);

        return result;
    }


    //format link to string
    public static String linkToString(String link) {
        String result = " ";
        char[] linkCharrArr = link.toCharArray();

        for(int i = 0; i < linkCharrArr.length; i++) {
            if(linkCharrArr[i] == '_') {
                linkCharrArr[i] = ' ';
            }
        }
        result = String.valueOf(linkCharrArr);

        return result;
    }


    //format DateTime
    public static String formatDateDMY(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return simpleDateFormat.format(date);
    }


    //convert String to byte[]
    public static byte[] convertStringToByteArray(String input) {
        return input.getBytes(StandardCharsets.UTF_8);
    }


    //convert byte[] to String
    public static String convertByteToString(byte[] input) {
        return new String(input, StandardCharsets.UTF_8);
    }

    //convert byte[] to KB, MB, GB, TB
    public static String getConvertedDataSize(long size) {
        long n = 1024;
        String s = "";
        double kb = size / n;
        double mb = kb / n;
        double gb = mb / n;
        double tb = gb / n;
        if(size < n) {
            s = size + " Bytes";
        } else if(size >= n && size < (n * n)) {
            s =  String.format("%.1f", kb) + " KB";
        } else if(size >= (n * n) && size < (n * n * n)) {
            s = String.format("%.1f", mb) + " MB";
        } else if(size >= (n * n * n) && size < (n * n * n * n)) {
            s = String.format("%.2f", gb) + " GB";
        } else if(size >= (n * n * n * n)) {
            s = String.format("%.2f", tb) + " TB";
        }
        return s;
    }

    //get customer id from HttpSession
    public static int getCustomerIdByHttpSession(HttpSession session) {
        try {
            Account currentUser = (Account) session.getAttribute("currentUser");

            if(currentUser.getCustomer() != null) {
                return currentUser.getCustomer().getId();
            } else if (currentUser.getStaff() != null) {
                return currentUser.getStaff().getId();
            }
            return 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
