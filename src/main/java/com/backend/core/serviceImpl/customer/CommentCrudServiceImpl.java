package com.backend.core.serviceImpl.customer;

import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.requestdto.ListRequestDTO;
import com.backend.core.entities.requestdto.comment.CommentFilterRequestDTO;
import com.backend.core.entities.requestdto.comment.CommentRequestDTO;
import com.backend.core.entities.tableentity.Comment;
import com.backend.core.entities.tableentity.Product;
import com.backend.core.enums.ErrorTypeEnum;
import com.backend.core.enums.FilterTypeEnum;
import com.backend.core.repository.comment.CommentRepository;
import com.backend.core.repository.customQuery.CustomQueryRepository;
import com.backend.core.repository.customer.CustomerRepository;
import com.backend.core.repository.product.ProductRepository;
import com.backend.core.service.CrudService;
import com.backend.core.util.process.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Qualifier("CommentCrudServiceImpl")
public class CommentCrudServiceImpl implements CrudService {
    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    CommentRepository commentRepo;

    @Autowired
    ProductRepository productRepo;

    @Autowired
    CustomQueryRepository customQueryRepo;

    @Autowired
    ValueRenderUtils valueRenderUtils;


    @Override
    public ResponseEntity<ApiResponse> singleCreationalResponse(Object paramObj, HttpServletRequest httpRequest) {
        CommentRequestDTO request;
        request = (CommentRequestDTO) paramObj;

        return addNewComment(request, httpRequest);
    }


    @Override
    public ResponseEntity<ApiResponse> listCreationalResponse(List<Object> objList, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ResponseEntity<ApiResponse> removingResponseByRequest(Object paramObj, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> removingResponseById(int id, HttpServletRequest httpRequest) {
        Comment comment;
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(httpRequest);

        try {
            comment = commentRepo.getCommentById(id);

            if (customerId != comment.getCustomer().getId()) {
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.UNAUTHORIZED.name()), HttpStatus.UNAUTHORIZED);
            }

            commentRepo.deleteById(id);

            return new ResponseEntity<>(new ApiResponse("success", "Update comment successfully"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<ApiResponse> updatingResponseByList(ListRequestDTO listRequestDTO, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> updatingResponseById(int id, HttpServletRequest httpRequest) {
        Comment comment;
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(httpRequest);

        if (id == 0) {
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.BAD_REQUEST);
        } else {
            try {
                comment = commentRepo.getCommentById(id);

                comment.likeComment();
                commentRepo.save(comment);

                return new ResponseEntity<>(new ApiResponse("success", "Like comment successfully"), HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Override
    public ResponseEntity<ApiResponse> updatingResponseByRequest(Object paramObj, HttpServletRequest httpRequest) {
        CommentRequestDTO request;
        Comment comment;
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(httpRequest);

        try {
            request = (CommentRequestDTO) paramObj;
            comment = commentRepo.getCommentById(request.getId());

            if (customerId != comment.getCustomer().getId()) {
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.UNAUTHORIZED.name()), HttpStatus.UNAUTHORIZED);
            }

            comment.setCommentContent(request.getCommentContent());
            comment.setCommentDate(new Date());

            commentRepo.save(comment);

            return new ResponseEntity<>(new ApiResponse("success", "Delete comment successfully"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<ApiResponse> readingFromSingleRequest(Object paramObj, HttpServletRequest httpRequest) {
        try {
            CommentFilterRequestDTO commentFilterRequest = (CommentFilterRequestDTO) paramObj;

            String filterQuery = valueRenderUtils.getFilterQuery(commentFilterRequest, FilterTypeEnum.COMMENT, httpRequest);

            List<Comment> commentList = customQueryRepo.getBindingFilteredList(filterQuery, Comment.class);

            return new ResponseEntity<>(new ApiResponse("success", commentList), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<ApiResponse> readingFromListRequest(List<Object> paramObjList, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ResponseEntity<ApiResponse> readingResponse(String renderType, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ResponseEntity<ApiResponse> readingById(int id, HttpServletRequest httpRequest) {
        return null;
    }


    // add new comment process
    public ResponseEntity<ApiResponse> addNewComment(CommentRequestDTO request, HttpServletRequest httpServletRequest) {
        Comment newComment = new Comment();
        Product product;
        int customerId;

        try {
            customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(httpServletRequest);
            product = productRepo.getProductById(request.getProductId());

            newComment.setProduct(product);
            newComment.setCommentContent(request.getCommentContent());
            newComment.setProductColor(request.getProductColor());
            newComment.setCustomer(customerRepo.getCustomerById(customerId));
            newComment.setCommentDate(new Date());
            newComment.setReplyOn(request.getReplyOn());
            commentRepo.save(newComment);

            return new ResponseEntity<>(new ApiResponse("success", "Add new comment successfully"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
