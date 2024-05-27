package com.ns.common.constants;

public enum ErrorCode {

    SIGN_IN_REQUIRED("SIGN_IN_REQUIRED"),
    FORBIDDEN("FORBIDDEN");

    private String code;
    private ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
