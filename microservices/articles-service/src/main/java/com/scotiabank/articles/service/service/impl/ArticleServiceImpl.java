package com.scotiabank.articles.service.service.impl;

import com.scotiabank.articles.service.dto.ArticleDto;
import com.scotiabank.articles.service.enums.BusinessErrorEnum;
import com.scotiabank.articles.service.exception.CustomApplicationException;
import com.scotiabank.articles.service.model.Article;
import com.scotiabank.articles.service.repository.ArticleRepository;
import com.scotiabank.articles.service.service.ArticleService;
import com.scotiabank.articles.service.util.mapper.ArticleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    @Override
    public List<ArticleDto> getAllArticles() {
        return articleRepository.findAll().stream()
                .map(articleMapper::toDto)
                .toList();
    }

    @Override
    public ArticleDto findArticleById(Long id) {
        return articleRepository.findById(id)
                .map(articleMapper::toDto)
                .orElseThrow(() -> new CustomApplicationException(BusinessErrorEnum.ARTICLE_NOT_FOUND));
    }

    @Override
    public ArticleDto createArticle(ArticleDto articleDto) {
        Article article = articleMapper.toEntity(articleDto);
        return articleMapper.toDto(articleRepository.save(article));
    }

    @Override
    public ArticleDto updateArticle(ArticleDto articleDto, Long id) {
        Article article = articleMapper.toEntity(articleDto);
        article.setId(id);
        return articleMapper.toDto(articleRepository.save(article));
    }

    @Override
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}
