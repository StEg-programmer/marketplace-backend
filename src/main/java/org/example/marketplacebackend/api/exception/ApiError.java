package org.example.marketplacebackend.api.exception;

import java.time.Instant;

public class ApiError {
    public String timestamp;
    public int status;
    public String error;
    public String message;
    public String path;

    public ApiError(int status, String error, String message, String path) {
        this.timestamp = Instant.now().toString();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}