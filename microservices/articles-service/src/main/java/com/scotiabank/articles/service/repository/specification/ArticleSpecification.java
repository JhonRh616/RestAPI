package com.scotiabank.articles.service.repository.specification;

import com.scotiabank.articles.service.dto.filters.ArticleFilterDto;
import com.scotiabank.articles.service.model.Article;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleSpecification implements SpecificationBuilder<Article, ArticleFilterDto> {

    @Override
    public Specification<Article> builder(ArticleFilterDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Optional.ofNullable(filter.getTitle())
                    .filter(s -> !s.isBlank())
                    .map(s -> cb.like(cb.lower(root.get("title")), "%" + s.toLowerCase() + "%"))
                    .ifPresent(predicates::add);

            Optional.ofNullable(filter.getAuthor())
                    .filter(s -> !s.isBlank())
                    .map(s -> cb.like(cb.lower(root.get("author")), "%" + s.toLowerCase() + "%"))
                    .ifPresent(predicates::add);

            Optional.ofNullable(filter.getContent())
                    .filter(s -> !s.isBlank())
                    .map(s -> cb.like(cb.lower(root.get("content")), "%" + s.toLowerCase() + "%"))
                    .ifPresent(predicates::add);

            Optional.ofNullable(filter.getPublishedDate())
                    .filter(s -> !s.isBlank())
                    .map(s -> cb.equal(root.get("publishedDate"), s))
                    .ifPresent(predicates::add);

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
