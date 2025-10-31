package com.scotiabank.search.service.service.impl;

import com.scotiabank.search.service.dto.ApiGeneralResponseDto;
import com.scotiabank.search.service.dto.ArticleDto;
import com.scotiabank.search.service.dto.ArticleFilterDto;
import com.scotiabank.search.service.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final RestTemplate restTemplate;

    @Value("${articles.service.search}")
    private String searchEndpoint;

    @Override
    public List<ArticleDto> getArticlesByBranch(String branch) {
        ResponseEntity<ApiGeneralResponseDto<List<ArticleDto>>> responseEntity =
                restTemplate.exchange(searchEndpoint+"/"+branch,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

        return responseEntity.getBody().getBody();
    }

    @Override
    public List<ArticleDto> getArticlesByFilters(ArticleFilterDto filterDto) {
        HttpEntity<ArticleFilterDto> requestEntity = new HttpEntity<>(filterDto);
        ResponseEntity<ApiGeneralResponseDto<List<ArticleDto>>> responseEntity =
                restTemplate.exchange(searchEndpoint,
                        HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {});

        return responseEntity.getBody().getBody();
    }
}
