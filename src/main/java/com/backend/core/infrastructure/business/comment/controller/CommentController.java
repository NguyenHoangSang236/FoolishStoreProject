package com.backend.core.infrastructure.business.comment.controller;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.comment.gateway.CommentFilterRequestDTO;
import com.backend.core.entity.comment.gateway.CommentRequestDTO;
import com.backend.core.infrastructure.config.api.ResponseMapper;
import com.backend.core.usecase.UseCaseExecutor;
import com.backend.core.usecase.usecases.comment.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class CommentController {
    final UseCaseExecutor useCaseExecutor;
    final AddNewCommentUseCase addNewCommentUseCase;
    final UpdateCommentUseCase updateCommentUseCase;
    final DeleteCommentByIdUseCase deleteCommentByIdUseCase;
    final ReactCommentByIdUseCase reactCommentByIdUseCase;
    final FilterCommentUseCase filterCommentUseCase;
    final ViewCommentIdYouLikedUseCase viewCommentIdYouLikedUseCase;


    @PostMapping("/authen/comment/add")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public CompletableFuture<ResponseEntity<ApiResponse>> addNewComment(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CommentRequestDTO commentRequest = objectMapper.readValue(json, CommentRequestDTO.class);

        return useCaseExecutor.execute(
                addNewCommentUseCase,
                new AddNewCommentUseCase.InputValue(commentRequest, httpRequest),
                ResponseMapper::map
        );
    }

    @PostMapping("/authen/comment/update")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public CompletableFuture<ResponseEntity<ApiResponse>> updateComment(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CommentRequestDTO commentRequest = objectMapper.readValue(json, CommentRequestDTO.class);

        return useCaseExecutor.execute(
                updateCommentUseCase,
                new UpdateCommentUseCase.InputValue(commentRequest, httpRequest),
                ResponseMapper::map
        );
    }

    @GetMapping("/authen/comment/delete_comment_id={id}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public CompletableFuture<ResponseEntity<ApiResponse>> deleteCommentById(@PathVariable int id, HttpServletRequest httpRequest) {
        return useCaseExecutor.execute(
                deleteCommentByIdUseCase,
                new DeleteCommentByIdUseCase.InputValue(id, httpRequest),
                ResponseMapper::map
        );
    }

    @GetMapping("/authen/comment/react_comment_id={id}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public CompletableFuture<ResponseEntity<ApiResponse>> reactCommentById(@PathVariable int id, HttpServletRequest httpRequest) throws IOException {
        return useCaseExecutor.execute(
                reactCommentByIdUseCase,
                new ReactCommentByIdUseCase.InputValue(id, httpRequest),
                ResponseMapper::map
        );
    }

    @PostMapping("/authen/comment/commentsYouLiked")
    public CompletableFuture<ResponseEntity<ApiResponse>> getCommentIdListThatYouLiked(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CommentRequestDTO commentRequest = objectMapper.readValue(json, CommentRequestDTO.class);

        return useCaseExecutor.execute(
                viewCommentIdYouLikedUseCase,
                new ViewCommentIdYouLikedUseCase.InputValue(commentRequest, httpRequest),
                ResponseMapper::map
        );
    }

    @PostMapping("/unauthen/comment/commentList")
    public CompletableFuture<ResponseEntity<ApiResponse>> filterComments(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CommentFilterRequestDTO commentFilterRequest = objectMapper.readValue(json, CommentFilterRequestDTO.class);

        return useCaseExecutor.execute(
                filterCommentUseCase,
                new FilterCommentUseCase.InputValue(commentFilterRequest, httpRequest),
                ResponseMapper::map
        );
    }
}
