package com.scotiabank.articles.service.controller.advice;

import com.scotiabank.articles.service.dto.ApiGeneralResponse;
import com.scotiabank.articles.service.exception.CustomApplicationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static com.scotiabank.articles.service.util.ApiConstants.ApiErrorResponses.*;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiGeneralResponse<Map<String, String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(ApiGeneralResponse.error(ERROR_PARAMETERS, errors));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiGeneralResponse<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest()
                .body(ApiGeneralResponse.error(INVALID_REQUEST_FORMAT, ex.getMostSpecificCause().getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiGeneralResponse<String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiGeneralResponse.error(DATA_INTEGRITY_VIOLATION, ex.getLocalizedMessage()));
    }

    @ExceptionHandler(CustomApplicationException.class)
    public ResponseEntity<ApiGeneralResponse<String>> handleCustomApplicationException(CustomApplicationException ex) {
        return ResponseEntity.status(ex.getHttpStatus())
                .body(ApiGeneralResponse.error(ex.getBusinessMessage(), ex.getLocalizedMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiGeneralResponse<String>> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiGeneralResponse.error(UNEXPECTED_ERROR, ex.getLocalizedMessage()));
    }
}
