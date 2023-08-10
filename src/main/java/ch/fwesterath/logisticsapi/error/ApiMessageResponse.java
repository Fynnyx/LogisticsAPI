package ch.fwesterath.logisticsapi.error;

import org.springframework.http.HttpStatus;

public class ApiMessageResponse {
    private final String message;
    private final HttpStatus status;

    public ApiMessageResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public Integer getStatus() {
        return status.value();
    }
}

