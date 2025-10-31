package com.scotiabank.articles.service.service;

import com.scotiabank.articles.service.dto.ArticleDto;
import com.scotiabank.articles.service.dto.filters.ArticleFilterDto;

import java.util.List;

public interface SearchArticleService {

    List<ArticleDto> getArticlesByBranch(String branch);
    List<ArticleDto> getArticlesByFilters(ArticleFilterDto filterDto);
}
