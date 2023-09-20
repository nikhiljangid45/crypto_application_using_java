package com.example.projectcryptoapp.model;

public class Status {
    private int credit_count;
    private String elapsed;
    private String error_code;
    private String error_message;
    private String timestamp;

    public Status(int credit_count, String elapsed, String error_code, String error_message, String timestamp) {
        this.credit_count = credit_count;
        this.elapsed = elapsed;
        this.error_code = error_code;
        this.error_message = error_message;
        this.timestamp = timestamp;
    }

    public int getCredit_count() {
        return credit_count;
    }

    public String getElapsed() {
        return elapsed;
    }

    public String getError_code() {
        return error_code;
    }

    public String getError_message() {
        return error_message;
    }

    public String getTimestamp() {
        return timestamp;
    }
}

