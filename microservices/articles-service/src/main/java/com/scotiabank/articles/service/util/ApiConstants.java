package com.scotiabank.articles.service.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiConstants {

    public static final String API_BASE_PATH = "/api";
    public static final String ARTICLES_BASE_PATH = API_BASE_PATH + "/articles";

    @UtilityClass
    public static class ApiResponses {
        public static final String ARTICLES_RETRIEVED = "Articles retrieved successfully";
        public static final String ARTICLE_RETRIEVED = "Article retrieved successfully";
        public static final String ARTICLE_CREATED = "Article created successfully";
        public static final String ARTICLES_UPDATED = "Article updated successfully";
        public static final String ARTICLES_DELETED = "Article deleted successfully";
    }

    @UtilityClass
    public static class ApiErrorResponses {
        public static final String DATA_INTEGRITY_VIOLATION = "";
        public static final String UNEXPECTED_ERROR = "An unexpected error occurred in the application";
        public static final String ERROR_PARAMETERS = "Some parameters are null or invalid";
        public static final String INVALID_REQUEST_FORMAT = "Invalid request format. Please verify and try again";
    }
}
