package com.scotiabank.search.service.exception;

import com.scotiabank.search.service.enums.BusinessErrorEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomApplicationException extends RuntimeException {

    private final String businessMessage;
    private final HttpStatus httpStatus;

    public CustomApplicationException(BusinessErrorEnum businessErrorEnum) {
        super();
        this.businessMessage = businessErrorEnum.getMessage();
        this.httpStatus = businessErrorEnum.getHttpStatus();
    }
}
