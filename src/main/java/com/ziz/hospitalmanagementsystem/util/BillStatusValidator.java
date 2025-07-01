package com.ziz.hospitalmanagementsystem.util;

import com.ziz.hospitalmanagementsystem.model.BillStatus;

public class BillStatusValidator {
    public static boolean isValid(String status) {
        try {
            BillStatus.valueOf(status.toUpperCase());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
