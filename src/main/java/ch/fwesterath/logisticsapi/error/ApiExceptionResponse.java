package ch.fwesterath.logisticsapi.error;

import org.springframework.http.HttpStatus;

public class ApiExceptionResponse extends RuntimeException {
    private final String message;
    private final HttpStatus status;

    public ApiExceptionResponse(HttpStatus status, String message) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
