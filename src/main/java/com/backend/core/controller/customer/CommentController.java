package com.backend.core.controller.customer;

import com.backend.core.abstractClasses.CrudController;
import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.requestdto.comment.CommentFilterRequestDTO;
import com.backend.core.entities.requestdto.comment.CommentRequestDTO;
import com.backend.core.service.CrudService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(consumes = {"*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
public class CommentController extends CrudController {
    public CommentController(@Autowired @Qualifier("CommentCrudServiceImpl") CrudService commentCrudServiceImpl) {
        super(commentCrudServiceImpl);
        super.crudService = commentCrudServiceImpl;
    }


    @PostMapping("/authen/comment/add")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Override
    public ResponseEntity<ApiResponse> addNewItem(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CommentRequestDTO commentRequest = objectMapper.readValue(json, CommentRequestDTO.class);

        return crudService.singleCreationalResponse(commentRequest, httpRequest);
    }

    @PostMapping("/authen/comment/update")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Override
    public ResponseEntity<ApiResponse> updateItem(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CommentRequestDTO commentRequest = objectMapper.readValue(json, CommentRequestDTO.class);

        return crudService.updatingResponseByRequest(commentRequest, httpRequest);
    }

    @Override
    public ResponseEntity<ApiResponse> readSelectedItemById(int id, HttpServletRequest httpRequest) throws IOException {
        return null;
    }

    @GetMapping("/authen/comment/delete_comment_id={id}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Override
    public ResponseEntity<ApiResponse> deleteSelectedItemById(@PathVariable int id, HttpServletRequest httpRequest) {
        return crudService.removingResponseById(id, httpRequest);
    }

    @GetMapping("/authen/comment/react_comment_id={id}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Override
    public ResponseEntity<ApiResponse> updateSelectedItemById(@PathVariable int id, HttpServletRequest httpRequest) throws IOException {
        return crudService.updatingResponseById(id, httpRequest);
    }

    @PostMapping("/authen/comment/commentsYouLiked")
    @Override
    public ResponseEntity<ApiResponse> getListOfItems(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CommentRequestDTO comment = objectMapper.readValue(json, CommentRequestDTO.class);
        return crudService.readingFromSingleRequest(comment, httpRequest);
    }

    @PostMapping("/unauthen/comment/commentList")
    @Override
    public ResponseEntity<ApiResponse> getListOfItemsFromFilter(@RequestBody String json, HttpServletRequest httpRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CommentFilterRequestDTO commentFilterRequest = objectMapper.readValue(json, CommentFilterRequestDTO.class);
        return crudService.readingFromSingleRequest(commentFilterRequest, httpRequest);
    }
}
