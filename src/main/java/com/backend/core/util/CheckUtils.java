package com.backend.core.util;

import com.backend.core.repository.CustomerRepository;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Pattern;

public class CheckUtils {
    @Autowired
    CustomerRepository customerRepo;


    //check String has space or not
    public static boolean checkValidStringType(String content, EnumsList type) {
        if(type == EnumsList.HAS_NO_SPACE) {
            return !isStringWithSpace(content);
        }
        else if (type == EnumsList.HAS_SPACE) {
            return isStringWithSpace(content);
        }

        else return false;
    }


    //check phone number is valid or not
    public static boolean isValidPhoneNumber(String number) {
        Phonenumber.PhoneNumber phoneNumber = new Phonenumber.PhoneNumber();

        return PhoneNumberUtil.getInstance().isValidNumber(phoneNumber);
    }


    //check email has been used or not
    public boolean isUsedEmail(String email) {
        return customerRepo.getCustomerByEmail(email) != null;
    }


    //check email is valid or not
    //    It allows numeric values from 0 to 9.
    //    Both uppercase and lowercase letters from a to z are allowed.
    //    Allowed are underscore “_”, hyphen “-“, and dot “.”
    //    Dot isn't allowed at the start and end of the local part.
    //    Consecutive dots aren't allowed.
    //    For the local part, a maximum of 64 characters are allowed.
    public static boolean isValidEmail(String email) {
        return patternMatches(email, "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    }


    //check if there is any space in a String
    public static boolean isStringWithSpace(String content) {
        char[] charArr = trimmedInputString(content).toCharArray();

        for (char c : charArr) {
            if (Character.isWhitespace(c)) {
                return true;
            }
        }
        return false;
    }


    //remove spaces at the beginning of the input text
    public static String trimmedInputString(String input) {
        int count = 0;

        char[] charArr = input.toCharArray();
        char[] resultCharArr = new char[charArr.length];

        for(int i = 0; i < charArr.length - 1; i++) {
            if(i > 0) {
                if(charArr[i] != ' ' || (charArr[i] == ' ' && charArr[i - 1] != ' ')) {
                    resultCharArr[count] = charArr[i];
                    count++;
                }
            }
            else {
                if(charArr[i] != ' ') {
                    resultCharArr[count] = charArr[i];
                    count++;
                }
            }
        }

        return String.copyValueOf(resultCharArr).trim();
    }


    //check if the text has special sign
    public static boolean hasSpecialSign (String input) {
        char[] testCharArr = input.toCharArray();

        for(int i = 0; i < testCharArr.length - 1; i++) {
            if(testCharArr[i] != 32 && testCharArr[i] < 65 && testCharArr[i] > 90 && testCharArr[i] < 97 && testCharArr[i] > 122) {
                return true;
            }
        }

        return false;
    }


    //check if String matches using Regex
    public static boolean patternMatches(String content, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(content)
                .matches();
    }
}
