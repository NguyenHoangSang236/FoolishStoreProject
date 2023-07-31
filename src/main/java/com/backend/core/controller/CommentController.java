package com.backend.core.controller;

import com.backend.core.abstractclasses.CrudController;
import com.backend.core.entities.dto.ApiResponse;
import com.backend.core.entities.dto.comment.CommentFilterRequestDTO;
import com.backend.core.entities.dto.comment.CommentRequestDTO;
import com.backend.core.service.CrudService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/comment", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommentController extends CrudController {
    public CommentController(@Autowired @Qualifier("CommentCrudServiceImpl") CrudService commentCrudServiceImpl) {
        super(commentCrudServiceImpl);
        super.crudService = commentCrudServiceImpl;
    }


    @PostMapping("/add")
    @Override
    public ApiResponse addNewItem(@RequestBody String json, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CommentRequestDTO commentRequest = objectMapper.readValue(json, CommentRequestDTO.class);

        return crudService.singleCreationalResponse(commentRequest, session, httpRequest);
    }

    @PostMapping("/update")
    @Override
    public ApiResponse updateItem(@RequestBody String json, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CommentRequestDTO commentRequest = objectMapper.readValue(json, CommentRequestDTO.class);

        return crudService.updatingResponseByRequest(commentRequest, session, httpRequest);
    }

    @Override
    public ApiResponse readSelectedItemById(int id, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        return null;
    }

    @GetMapping("/delete_comment_id={id}")
    @Override
    public ApiResponse deleteSelectedItemById(@PathVariable int id, HttpSession session, HttpServletRequest httpRequest) {
        return crudService.removingResponseById(id, session, httpRequest);
    }

    @GetMapping("/like_comment_id={id}")
    @Override
    public ApiResponse updateSelectedItemById(@PathVariable int id, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        return crudService.updatingResponseById(id, session, httpRequest);
    }

    @PostMapping("/commentList")
    @Override
    public ApiResponse getListOfItems(@RequestBody String json, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CommentFilterRequestDTO commentFilterRequest = objectMapper.readValue(json, CommentFilterRequestDTO.class);

        return crudService.readingFromSingleRequest(commentFilterRequest, session, httpRequest);
    }

    @Override
    public ApiResponse getListOfItemsFromFilter(String json, HttpSession session, HttpServletRequest httpRequest) throws IOException {
        return null;
    }
}
