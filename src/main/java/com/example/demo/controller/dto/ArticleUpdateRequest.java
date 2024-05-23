package com.example.demo.controller.dto;

public record ArticleUpdateRequest(
    Long boardId,
    String title,
    String description
) {

}
