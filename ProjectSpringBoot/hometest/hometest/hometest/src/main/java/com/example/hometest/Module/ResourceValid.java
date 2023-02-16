package com.example.hometest.Module;

import com.google.common.base.Strings;

public class ResourceValid {

    public enum typeOBJECT {
        STRING, DATE, NUMBER, BOOLEAN, SYMBOL
    }

    public static boolean TypeIsError(typeOBJECT typeObject, Object Value) {
        try {
            switch (typeObject) {
                case STRING:
                    return Value.getClass().getSimpleName() != "string";
                case DATE:
                    return Value.getClass().getSimpleName() != "date";
                case NUMBER:
                    return Value.getClass().getSimpleName() != "number";
                case BOOLEAN:
                    return Value.getClass().getSimpleName() != "boolean";
                case SYMBOL:
                    return Value.getClass().getSimpleName() != "symbol";
                default:
                    return Value.getClass().getSimpleName() != "undefined";
            }
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());
        }
    }

    public static boolean StrIsError(String string) {
        try {
            return Strings.isNullOrEmpty(string) || string == "null" || string.trim() == "NULL";
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());
        }
    }

    public static boolean ObjectIsError(typeOBJECT typeObject, Object Value) {
        try {
            return StrIsError(String.valueOf((Value))) || TypeIsError(typeObject, Value);
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());
        }
    }

    public enum typeERROR {
        REQUEST, FIELD, DIFFERENT, NOTEXISTED, EXISTED
    }

    public static String StringError(typeERROR typeString, String resourceName) {
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
