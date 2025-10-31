package com.scotiabank.articles.service.model;

import com.scotiabank.articles.service.config.BranchEntityListener;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "article")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(BranchEntityListener.class)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String content;
    private LocalDate publishedDate;
    private String branch;
}
