package com.scotiabank.search.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BusinessErrorEnum {

    ARTICLES_NOT_FOUND("Your search didn't return results", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus httpStatus;
}
