package com.scotiabank.articles.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Article data transfer object")
public class ArticleDto {

    @Schema(description = "Id of the article", example = "1")
    private Long id;

    @NotBlank(message = "Title cannot be null or empty")
    @Schema(description = "Title of the article", example = "Title of the article 1")
    private String title;

    @NotBlank(message = "Author cannot be null or empty")
    @Schema(description = "Author of the article", example = "Author of the article 1")
    private String author;

    @Schema(description = "Content of the article", example = "Content of the article...")
    private String content;

    @Schema(description = "Publish date of the article", example = "2025-12-17")
    private LocalDate publishedDate;

    @Schema(description = "Branch of the article", example = "Medellin")
    private String branch;
}
