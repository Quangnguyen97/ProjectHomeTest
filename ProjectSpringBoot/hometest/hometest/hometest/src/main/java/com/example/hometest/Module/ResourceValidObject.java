package com.example.hometest.Module;

import com.google.common.base.Strings;

public class ResourceValidObject {
    public static boolean StringIsError(String string) {
        try {
            if (Strings.isNullOrEmpty(string) || string == "null" || string.trim() == "NULL") {
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new ResourceRuntimeException(e.getMessage());
        }
    }

    public enum TypeString {
        errorRequest, errorField, differentRequest, dataNotExist, dataExist
    }

    public static String StringErrorField(TypeString typeString, String fieldName) {
        try {
            switch (typeString) {
                case errorRequest:
                    return String.format("%s have error with request body %s", fieldName);
                case errorField:
                    return String.format("%s have error with field %s", fieldName);
                case differentRequest:
                    return String.format("%s is different request body with field %s", fieldName);
                case dataNotExist:
                    return String.format("%s does not exist with field %s", fieldName);
                case dataExist:
                    return String.format("%s have exist with field %s", fieldName);
                default:
                    return String.format("%s have exception with  %s : '%s'", fieldName);
            }
        } catch (Exception e) {
            throw new ResourceRuntimeException(e.getMessage());
        }
    }
}
