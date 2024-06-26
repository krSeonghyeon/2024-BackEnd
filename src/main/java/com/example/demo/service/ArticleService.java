package com.example.demo.service;

import java.util.List;

import com.example.demo.exception.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.dto.request.ArticleCreateRequest;
import com.example.demo.controller.dto.response.ArticleResponse;
import com.example.demo.controller.dto.request.ArticleUpdateRequest;
import com.example.demo.domain.Article;
import com.example.demo.domain.Board;
import com.example.demo.domain.Member;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.MemberRepository;

import static com.example.demo.exception.PostExceptionType.*;

@Service
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public ArticleService(
        ArticleRepository articleRepository,
        MemberRepository memberRepository,
        BoardRepository boardRepository
    ) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
    }

    public ArticleResponse getById(Long id) {
        Article article = articleRepository.findById(id);
        if (article == null) throw new CustomException(NOT_FOUND_POST);

        Member member = memberRepository.findById(article.getAuthorId());
        Board board = boardRepository.findById(article.getBoardId());

        return ArticleResponse.of(article, member, board);
    }

    public List<ArticleResponse> getByBoardId(Long boardId) {
        List<Article> articles = articleRepository.findAllByBoardId(boardId);
        return articles.stream()
            .map(article -> {
                Member member = memberRepository.findById(article.getAuthorId());
                Board board = boardRepository.findById(article.getBoardId());
                return ArticleResponse.of(article, member, board);
            })
            .toList();
    }

    @Transactional
    public ArticleResponse create(ArticleCreateRequest request) {
        if (!isValid(request))  throw new CustomException(CommonExceptionType.BAD_REQUEST_NULL_VALUE);

        Member existedMember = memberRepository.findById(request.authorId());
        if (existedMember == null) throw new CustomException(INVALID_MEMBER_REFERENCE);

        Board existedBoard = boardRepository.findById(request.boardId());
        if (existedBoard == null) throw new CustomException(INVALID_BOARD_REFERENCE);

        Article article = new Article(
            request.authorId(),
            request.boardId(),
            request.title(),
            request.description()
        );

        Article saved = articleRepository.insert(article);
        Member member = memberRepository.findById(saved.getAuthorId());
        Board board = boardRepository.findById(saved.getBoardId());

        return ArticleResponse.of(saved, member, board);
    }

    private boolean isValid(ArticleCreateRequest request) {
        return request.authorId() != null
                && request.boardId() != null
                && request.title() != null
                && request.description() != null;
    }

    @Transactional
    public ArticleResponse update(Long id, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(id);
        if (article == null) throw new CustomException(NOT_FOUND_POST);

        Board existedBoard = boardRepository.findById(request.boardId());
        if (existedBoard == null)  throw new CustomException(INVALID_BOARD_REFERENCE);

        article.update(request.boardId(), request.title(), request.description());

        Article updated = articleRepository.update(article);
        Member member = memberRepository.findById(updated.getAuthorId());
        Board board = boardRepository.findById(article.getBoardId());

        return ArticleResponse.of(updated, member, board);
    }

    @Transactional
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }
}
