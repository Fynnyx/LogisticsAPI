package ch.fwesterath.logisticsapi.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionResponseHandler {
    @ExceptionHandler(ApiExceptionResponse.class)
    public ResponseEntity<ApiMessageResponse> handleApiException(ApiExceptionResponse ex) {
        ApiMessageResponse errorResponse = new ApiMessageResponse(getErrorMessage(ex.getMessage()), ex.getStatus());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    private String getErrorMessage(String fullErrorMessage) {
        int index = fullErrorMessage.lastIndexOf(":");
        if (index != -1 && index < fullErrorMessage.length() - 1) {
            return fullErrorMessage.substring(index + 2);
        }
        return fullErrorMessage;
    }

}
