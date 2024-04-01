package com.backend.core.usecase.util;

import com.backend.core.infrastructure.business.account.repository.AccountRepository;
import com.backend.core.infrastructure.business.account.repository.CustomerRepository;
import com.backend.core.usecase.service.JwtService;
import com.backend.core.usecase.statics.AdminAcceptanceEnum;
import com.backend.core.usecase.statics.InvoiceEnum;
import com.backend.core.usecase.statics.StringTypeEnum;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CheckUtils {
    final CustomerRepository customerRepo;
    final AccountRepository accountRepo;
    final ValueRenderUtils valueRenderUtils;
    final JwtService jwtService;


    //check String has space or not
    public boolean checkValidStringType(String content, StringTypeEnum type) {
        if (type == StringTypeEnum.HAS_NO_SPACE) {
            return isStringWithSpace(content);
        } else if (type == StringTypeEnum.HAS_SPACE) {
            return !isStringWithSpace(content);
        } else return true;
    }


    //check phone number is valid or not
    public boolean isValidPhoneNumber(String number) {
        Phonenumber.PhoneNumber phoneNumber = new Phonenumber.PhoneNumber();

        return PhoneNumberUtil.getInstance().isValidNumber(phoneNumber);
    }


    //check email is valid or not
    //    It allows numeric values from 0 to 9.
    //    Both uppercase and lowercase letters from a to z are allowed.
    //    Allowed are underscore “_”, hyphen “-“, and dot “.”
    //    Dot isn't allowed at the start and end of the local part.
    //    Consecutive dots aren't allowed.
    //    For the local part, a maximum of 64 characters are allowed.
    public boolean isValidEmail(String email) {
        return patternMatches(email, "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    }


    //check if there is any space in a String
    public boolean isStringWithSpace(String content) {
        char[] charArr = valueRenderUtils.fullyTrimmedString(content).toCharArray();

        for (char c : charArr) {
            if (Character.isWhitespace(c)) {
                return true;
            }
        }
        return false;
    }


    //check if the text has special sign
    public boolean hasSpecialSign(String input) {
        char[] testCharArr = input.toCharArray();

        for (int i = 0; i < testCharArr.length - 1; i++) {
            if (testCharArr[i] != 32 && testCharArr[i] < 65 && testCharArr[i] > 90 && testCharArr[i] < 97 && testCharArr[i] > 122) {
                return true;
            }
        }

        return false;
    }


    //check if String matches using Regex
    public boolean patternMatches(String content, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(content)
                .matches();
    }


    //check email has been used or not
    public boolean isUsedEmail(String email) {
        return customerRepo.getCustomerByEmail(email) != null;
    }


    // check string is in invoice enum or not
    public boolean isInvoiceEnumValueExist(String value) {
        try {
            InvoiceEnum.valueOf(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }


    // check string is in invoice enum or not
    public boolean isAdminAcceptanceEnumValueExist(String value) {
        try {
            AdminAcceptanceEnum.valueOf(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
