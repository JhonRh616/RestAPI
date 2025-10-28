package com.scotiabank.articles.service.util.mapper;

import com.scotiabank.articles.service.dto.ArticleDto;
import com.scotiabank.articles.service.model.Article;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    ArticleDto toDto(Article article);

    Article toEntity(ArticleDto articleDto);
}
