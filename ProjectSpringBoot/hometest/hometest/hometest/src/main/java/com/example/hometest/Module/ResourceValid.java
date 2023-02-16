package com.example.hometest.Module;

import java.util.Date;
import com.google.common.base.Strings;

public class ResourceValid {

    public enum typeOBJECT {
        STRING, DATE, LONG, INTEGER, FLOAT, DOUBLE, BOOLEAN
    }

    public static boolean TypeIsError(typeOBJECT typeObject, Object Value) {
        try {
            switch (typeObject) {
                case STRING:
                    return Value.getClass().getSimpleName() != String.class.getSimpleName();
                case DATE:
                    return Value.getClass().getSimpleName() != Date.class.getSimpleName();
                case LONG:
                    return Value.getClass().getSimpleName() != Long.class.getSimpleName();
                case INTEGER:
                    return Value.getClass().getSimpleName() != Integer.class.getSimpleName();
                case FLOAT:
                    return Value.getClass().getSimpleName() != Float.class.getSimpleName();
                case DOUBLE:
                    return Value.getClass().getSimpleName() != Double.class.getSimpleName();
                case BOOLEAN:
                    return Value.getClass().getSimpleName() != Boolean.class.getSimpleName();
                default:
                    return Value.getClass().getSimpleName() != Object.class.getSimpleName();
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
