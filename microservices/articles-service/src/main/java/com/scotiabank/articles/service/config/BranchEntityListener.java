package com.scotiabank.articles.service.config;

import com.scotiabank.articles.service.model.Article;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BranchEntityListener {

    @Value("${branch.name}")
    private String branchName;

    @PrePersist
    @PreUpdate
    public void beforeSave(Object entity) {
        if (entity instanceof Article article && (article.getBranch() == null || article.getBranch().isEmpty())) {
                article.setBranch(branchName);
            }

    }
}
