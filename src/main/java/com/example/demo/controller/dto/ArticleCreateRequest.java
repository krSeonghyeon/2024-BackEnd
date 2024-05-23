package com.example.demo.controller.dto;

public record ArticleCreateRequest(
    Long authorId,
    Long boardId,
    String title,
    String description
) {

}
