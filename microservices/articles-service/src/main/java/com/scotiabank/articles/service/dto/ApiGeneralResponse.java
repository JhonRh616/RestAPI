package com.scotiabank.articles.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiGeneralResponse<T> {

    private boolean success;
    private String message;
    private T body;

    public static <T> ApiGeneralResponse<T> success(String message, T body) {
        return ApiGeneralResponse.<T>builder()
                .success(true)
                .message(message)
                .body(body)
                .build();
    }

    public static <T> ApiGeneralResponse<T> error(String message, T body) {
        return ApiGeneralResponse.<T>builder()
                .success(false)
                .message(message)
                .body(body)
                .build();
    }
}
