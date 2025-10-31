package com.scotiabank.articles.service.service.impl;

import com.scotiabank.articles.service.dto.ArticleDto;
import com.scotiabank.articles.service.dto.filters.ArticleFilterDto;
import com.scotiabank.articles.service.model.Article;
import com.scotiabank.articles.service.repository.ArticleRepository;
import com.scotiabank.articles.service.repository.specification.ArticleSpecification;
import com.scotiabank.articles.service.service.SearchArticleService;
import com.scotiabank.articles.service.util.mapper.ArticleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchArticleServiceImpl implements SearchArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final ArticleSpecification articleSpecification;

    @Override
    public List<ArticleDto> getArticlesByBranch(String branch) {
        return articleRepository.findByBranch(branch).stream().map(articleMapper::toDto).toList();
    }

    @Override
    public List<ArticleDto> getArticlesByFilters(ArticleFilterDto filterDto) {
        Specification<Article> spec = articleSpecification.builder(filterDto);
        return articleRepository.findAll(spec).stream().map(articleMapper::toDto).toList();
    }
}
