package com.scotiabank.articles.service.controller;

import com.scotiabank.articles.service.dto.ApiGeneralResponseDto;
import com.scotiabank.articles.service.dto.ArticleDto;
import com.scotiabank.articles.service.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.scotiabank.articles.service.util.ApiConstants.ARTICLES_BASE_PATH;
import static com.scotiabank.articles.service.util.ApiConstants.ApiErrorResponses.ERROR_PARAMETERS;
import static com.scotiabank.articles.service.util.ApiConstants.ApiResponses.*;

@RestController
@RequestMapping(value = ARTICLES_BASE_PATH)
@RequiredArgsConstructor
@Tag(name = "Articles", description = "Endpoints to manage the articles")
public class ArticleController {

    private final ArticleService articleService;

    @Operation(summary = "Get all articles", description = "Retrieve a list of all the articles")
    @ApiResponse(responseCode = "200", description = ARTICLES_RETRIEVED)
    @GetMapping
    public ResponseEntity<ApiGeneralResponseDto<List<ArticleDto>>> getAllArticles() {
        return ResponseEntity.ok(ApiGeneralResponseDto.success(ARTICLES_RETRIEVED, articleService.getAllArticles()));
    }

    @Operation(summary = "Get an article by id", description = "Retrieve one article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ARTICLE_RETRIEVED),
            @ApiResponse(responseCode = "404", description = "Article not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiGeneralResponseDto<ArticleDto>> getArticleById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ApiGeneralResponseDto.success(ARTICLE_RETRIEVED, articleService.findArticleById(id)));
    }

    @Operation(summary = "Create a new article", description = "Create a new article with the given data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = ARTICLE_CREATED),
            @ApiResponse(responseCode = "400", description = ERROR_PARAMETERS)
    })
    @PostMapping
    public ResponseEntity<ApiGeneralResponseDto<ArticleDto>> createArticle(@Valid @RequestBody ArticleDto articleDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiGeneralResponseDto.success(ARTICLE_CREATED, articleService.createArticle(articleDto)));
    }

    @Operation(summary = "Update an article", description = "Update an article by id and the body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = ARTICLES_UPDATED),
            @ApiResponse(responseCode = "400", description = ERROR_PARAMETERS)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiGeneralResponseDto<ArticleDto>> updateArticle(@Valid @RequestBody ArticleDto articleDto, @PathVariable("id") Long id) {
        return ResponseEntity.ok(ApiGeneralResponseDto.success(ARTICLES_UPDATED, articleService.updateArticle(articleDto, id)));
    }

    @Operation(summary = "Delete an article", description = "Delete an article by id")
    @ApiResponse(responseCode = "200", description = ARTICLES_DELETED)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiGeneralResponseDto<Void>> deleteArticle(@PathVariable("id") Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.ok(ApiGeneralResponseDto.success(ARTICLES_DELETED, null));
    }
}

