package com.example.hometest.Module;

import com.google.common.base.Strings;

public class ResourceValid {
    public static boolean StrIsError(String string) {
        try {
            if (Strings.isNullOrEmpty(string) || string == "null" || string.trim() == "NULL") {
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());
        }
    }

    public enum TypeString {
        REQUEST, FIELD, DIFFERENT, NOTEXISTED, EXISTED
    }

    public static String StringError(TypeString typeString, String resourceName) {
        try {
            switch (typeString) {
                case REQUEST:
                    return String.format("Request body is empty %s", resourceName);
                case FIELD:
                    return String.format("Request body is error with field %s", resourceName);
                case DIFFERENT:
                    return String.format("request body is different with field %s", resourceName);
                case NOTEXISTED:
                    return String.format("Data does not exist with field %s", resourceName);
                case EXISTED:
                    return String.format("Data have exist with field %s", resourceName);
                default:
                    return String.format("Error exception with %s", resourceName);
            }
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());
        }
    }
}
