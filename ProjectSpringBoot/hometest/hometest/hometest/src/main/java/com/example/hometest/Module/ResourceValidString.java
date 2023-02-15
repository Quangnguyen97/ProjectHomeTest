package com.example.hometest.Module;

import com.google.common.base.Strings;

public class ResourceValidString {
    public boolean ValidStringIsOk(String string) {
        try {
            if (Strings.isNullOrEmpty(string) || string == "null" || string.trim() == "NULL") {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
