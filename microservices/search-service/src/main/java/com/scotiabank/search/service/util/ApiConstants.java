package com.scotiabank.search.service.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiConstants {

    public static final String API_BASE_PATH = "/api";
    public static final String SEARCH_BASE_PATH = API_BASE_PATH + "/search";

    @UtilityClass
    public static class ApiResponses {
        public static final String ARTICLES_RETRIEVED = "Articles retrieved successfully";
    }

    @UtilityClass
    public static class ApiErrorResponses {
        public static final String UNEXPECTED_ERROR = "An unexpected error occurred in the application";
        public static final String ERROR_PARAMETERS = "Some parameters are null or invalid";
        public static final String INVALID_REQUEST_FORMAT = "Invalid request format. Please verify and try again";
    }
}
