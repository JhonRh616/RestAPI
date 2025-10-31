package com.scotiabank.articles.service.controller.advice;

import com.scotiabank.articles.service.dto.ApiGeneralResponseDto;
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
    public ResponseEntity<ApiGeneralResponseDto<Map<String, String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(ApiGeneralResponseDto.error(ERROR_PARAMETERS, errors));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiGeneralResponseDto<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest()
                .body(ApiGeneralResponseDto.error(INVALID_REQUEST_FORMAT, ex.getMostSpecificCause().getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiGeneralResponseDto<String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiGeneralResponseDto.error(DATA_INTEGRITY_VIOLATION, ex.getLocalizedMessage()));
    }

    @ExceptionHandler(CustomApplicationException.class)
    public ResponseEntity<ApiGeneralResponseDto<String>> handleCustomApplicationException(CustomApplicationException ex) {
        return ResponseEntity.status(ex.getHttpStatus())
                .body(ApiGeneralResponseDto.error(ex.getBusinessMessage(), ex.getLocalizedMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiGeneralResponseDto<String>> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiGeneralResponseDto.error(UNEXPECTED_ERROR, ex.getLocalizedMessage()));
    }
}
