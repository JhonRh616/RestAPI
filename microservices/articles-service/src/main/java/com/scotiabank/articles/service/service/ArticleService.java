package com.scotiabank.articles.service.service;

import com.scotiabank.articles.service.dto.ArticleDto;
import java.util.List;

public interface ArticleService {

    List<ArticleDto> getAllArticles();

    ArticleDto findArticleById(Long id);

    ArticleDto createArticle(ArticleDto articleDto);

    ArticleDto updateArticle(ArticleDto articleDto, Long id);

    void deleteArticle(Long id);
}
