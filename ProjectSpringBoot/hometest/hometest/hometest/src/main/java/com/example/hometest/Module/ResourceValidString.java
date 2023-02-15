package com.example.hometest.Module;

import com.google.common.base.Strings;

public class ResourceValidString {
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
}
