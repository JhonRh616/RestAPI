package com.scotiabank.articles.service.dto.filters;

import lombok.Data;

@Data
public class ArticleFilterDto {

    private String title;
    private String author;
    private String content;
    private String publishedDate;
}
