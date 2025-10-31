package com.scotiabank.search.service.service;

import com.scotiabank.search.service.dto.ArticleDto;
import com.scotiabank.search.service.dto.ArticleFilterDto;

import java.util.List;

public interface SearchService {

    List<ArticleDto> getArticlesByBranch(String branch);
    List<ArticleDto> getArticlesByFilters(ArticleFilterDto filterDto);
}
