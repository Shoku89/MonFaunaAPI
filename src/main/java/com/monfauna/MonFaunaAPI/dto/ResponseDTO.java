package com.monfauna.MonFaunaAPI.dto;

import java.time.Instant;

public class ResponseDTO {

    private Integer statusCode;
    private String message;
    private String timestamp;

    public ResponseDTO(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = Instant.now().toString();
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp.toString();
    }
}
