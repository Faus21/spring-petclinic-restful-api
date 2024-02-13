package com.project.s24521.DTOs.exception;

import lombok.Data;

import java.util.Date;

@Data
public class WebAppException {
    private int status;

    private String message;

    private Date timestamp;

    public WebAppException(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
