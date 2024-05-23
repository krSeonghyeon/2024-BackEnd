package com.example.demo.domain;

import java.time.LocalDateTime;

public class Article {

    private Long id;
    private Long authorId;
    private Long boardId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public Article(Long authorId, Long boardId, String title, String description, LocalDateTime createdAt) {
        this.authorId = authorId;
        this.boardId = boardId;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

    public void update(Long boardId, String title, String description) {
        this.boardId = boardId;
        this.title = title;
        this.description = description;
        this.modifiedAt = LocalDateTime.now();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Long getBoardId() {
        return boardId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }
}
