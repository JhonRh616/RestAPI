package com.scotiabank.articles.service.repository.specification;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T, F> {
    Specification<T> builder(F filter);
}
