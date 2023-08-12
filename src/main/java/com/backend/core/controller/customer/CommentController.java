package com.backend.core.controller.customer;

import com.backend.core.abstractClasses.CrudController;
import com.backend.core.entities.requestdto.comment.CommentFilterRequestDTO;
import com.backend.core.entities.requestdto.comment.CommentRequestDTO;
import com.backend.core.service.CrudService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/authen/comment", consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommentController extends CrudController {
    public CommentController(@Autowired @Qualifier("CommentCrudServiceImpl") CrudService commentCrudServiceImpl) {
        super(commentCrudServiceImpl);
        super.crudService = commentCrudServiceImpl;
    }


    @PostMapping("/add")
    @Override
    public ResponseEntity addNewItem(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CommentRequestDTO commentRequest = objectMapper.readValue(json, CommentRequestDTO.class);

        return crudService.singleCreationalResponse(commentRequest, httpRequest);
    }

    @PostMapping("/update")
    @Override
    public ResponseEntity updateItem(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CommentRequestDTO commentRequest = objectMapper.readValue(json, CommentRequestDTO.class);

        return crudService.updatingResponseByRequest(commentRequest, httpRequest);
    }

    @Override
    public ResponseEntity readSelectedItemById(int id, HttpServletRequest httpRequest) throws IOException {
        return null;
    }

    @GetMapping("/delete_comment_id={id}")
    @Override
    public ResponseEntity deleteSelectedItemById(@PathVariable int id, HttpServletRequest httpRequest) {
        return crudService.removingResponseById(id, httpRequest);
    }

    @GetMapping("/like_comment_id={id}")
    @Override
    public ResponseEntity updateSelectedItemById(@PathVariable int id, HttpServletRequest httpRequest) throws IOException {
        return crudService.updatingResponseById(id, httpRequest);
    }

    @PostMapping("/commentList")
    @Override
    public ResponseEntity getListOfItems(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CommentFilterRequestDTO commentFilterRequest = objectMapper.readValue(json, CommentFilterRequestDTO.class);

        return crudService.readingFromSingleRequest(commentFilterRequest, httpRequest);
    }

    @Override
    public ResponseEntity getListOfItemsFromFilter(String json, HttpServletRequest httpRequest) throws IOException {
        return null;
    }
}
