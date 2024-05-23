package com.example.demo.controller.dto;

import java.time.LocalDateTime;

import com.example.demo.domain.Article;
import com.example.demo.domain.Board;
import com.example.demo.domain.Member;

public record ArticleResponse(
    Long id,
    String title,
    String content,
    String board,
    String author,
    LocalDateTime date
) {

    public static ArticleResponse of(Article article, Member member, Board board) {
        return new ArticleResponse(
            article.getId(),
            article.getTitle(),
            article.getDescription(),
            board.getName(),
            member.getName(),
            article.getCreatedAt()
        );
    }
}
