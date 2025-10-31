package com.scotiabank.search.service.controller;

import com.scotiabank.search.service.dto.ApiGeneralResponseDto;
import com.scotiabank.search.service.dto.ArticleDto;
import com.scotiabank.search.service.dto.ArticleFilterDto;
import com.scotiabank.search.service.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.scotiabank.search.service.util.ApiConstants.ApiResponses.ARTICLES_RETRIEVED;
import static com.scotiabank.search.service.util.ApiConstants.SEARCH_BASE_PATH;

@RestController
@RequestMapping(value = SEARCH_BASE_PATH)
@RequiredArgsConstructor
@Tag(name = "Search", description = "Endpoints to search articles")
public class SearchController {

    private final SearchService searchService;

    @Operation(summary = "Get all articles by branch", description = "Retrieve a list of articles")
    @ApiResponse(responseCode = "200", description = ARTICLES_RETRIEVED)
    @GetMapping
    public ResponseEntity<ApiGeneralResponseDto<List<ArticleDto>>> getArticlesByBranch(
            @RequestParam("branch") String branch) {
        return ResponseEntity.ok(ApiGeneralResponseDto.success(
                ARTICLES_RETRIEVED, searchService.getArticlesByBranch(branch)));
    }

    @Operation(summary = "Get all articles by filters", description = "Retrieve a list of filtered articles")
    @ApiResponse(responseCode = "200", description = ARTICLES_RETRIEVED)
    @PostMapping
    public ResponseEntity<ApiGeneralResponseDto<List<ArticleDto>>> getArticlesByFilters(
            @RequestBody ArticleFilterDto articleFilters) {
        return ResponseEntity.ok(ApiGeneralResponseDto.success(
                ARTICLES_RETRIEVED, searchService.getArticlesByFilters(articleFilters)));
    }

}
