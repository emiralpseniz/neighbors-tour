package com.vmware.neighborstour.model;

import java.io.Serializable;

public class ApiError implements Serializable {
    public static final long serialVersionUID = 1L;

    private int httpStatus;
    private String message;

    public ApiError(int httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
