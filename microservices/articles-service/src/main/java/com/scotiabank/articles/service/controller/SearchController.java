package com.scotiabank.articles.service.controller;

import com.scotiabank.articles.service.dto.ApiGeneralResponseDto;
import com.scotiabank.articles.service.dto.ArticleDto;
import com.scotiabank.articles.service.dto.filters.ArticleFilterDto;
import com.scotiabank.articles.service.service.SearchArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.scotiabank.articles.service.util.ApiConstants.ApiResponses.ARTICLES_RETRIEVED;
import static com.scotiabank.articles.service.util.ApiConstants.SEARCH_BASE_PATH;

@RestController
@RequestMapping(value = SEARCH_BASE_PATH)
@RequiredArgsConstructor
@Tag(name = "Articles search", description = "Endpoints to search articles")
public class SearchController {

    private final SearchArticleService searchArticleService;

    @Operation(summary = "Get all articles by branch", description = "Retrieve a list of articles")
    @ApiResponse(responseCode = "200", description = ARTICLES_RETRIEVED)
    @GetMapping("/{branch}")
    public ResponseEntity<ApiGeneralResponseDto<List<ArticleDto>>> getArticlesByBranch(
            @PathVariable("branch") String branch) {
        return ResponseEntity.ok(ApiGeneralResponseDto.success(
                ARTICLES_RETRIEVED, searchArticleService.getArticlesByBranch(branch)));
    }

    @Operation(summary = "Get all articles by filters", description = "Retrieve a list of filtered articles")
    @ApiResponse(responseCode = "200", description = ARTICLES_RETRIEVED)
    @PostMapping
    public ResponseEntity<ApiGeneralResponseDto<List<ArticleDto>>> getArticlesByFilters(
            @RequestBody ArticleFilterDto articleFilters) {
        return ResponseEntity.ok(ApiGeneralResponseDto.success(
                ARTICLES_RETRIEVED, searchArticleService.getArticlesByFilters(articleFilters)));
    }
}
