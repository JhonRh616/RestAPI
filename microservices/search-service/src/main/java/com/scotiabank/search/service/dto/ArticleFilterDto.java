package com.scotiabank.search.service.dto;

import lombok.Data;

@Data
public class ArticleFilterDto {

    private String title;
    private String author;
    private String content;
    private String publishedDate;
}
