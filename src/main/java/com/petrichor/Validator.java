package com.petrichor;

import java.util.regex.Pattern;

public class Validator {
    private static final Pattern FULLNAME_PATTERN = Pattern.compile("^[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+$");
    private static final String FEMALE_NAME_ENDINGS = "^(?!Никита$|Власта$|Лука$|Савва$|Юлиа$|Мануэла$|Закхея$|Илья$|Фома$).*[ая]$";

    public boolean isValidFullName(String fullName) {
        return FULLNAME_PATTERN.matcher(fullName).matches();
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }

        String digitsOnly = phoneNumber.replaceAll("[^0-9]", "");
        if (digitsOnly.startsWith("7") || digitsOnly.startsWith("8")) {
            digitsOnly = digitsOnly.substring(1);
        }
        return digitsOnly.length() == 10;
    }

    public boolean isFemale(String fullName) {
        String[] parts = fullName.split(" ");
        if (parts.length != 3) {
            return false;
        }
        return parts[1].matches(FEMALE_NAME_ENDINGS);
    }
}