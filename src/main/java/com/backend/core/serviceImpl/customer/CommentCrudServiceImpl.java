package com.backend.core.serviceImpl.customer;

import com.backend.core.entities.embededkey.CommentLikePrimaryKeys;
import com.backend.core.entities.renderdto.CommentRenderInfoDTO;
import com.backend.core.entities.requestdto.ApiResponse;
import com.backend.core.entities.requestdto.ListRequestDTO;
import com.backend.core.entities.requestdto.comment.CommentFilterRequestDTO;
import com.backend.core.entities.requestdto.comment.CommentRequestDTO;
import com.backend.core.entities.tableentity.*;
import com.backend.core.enums.ErrorTypeEnum;
import com.backend.core.enums.FilterTypeEnum;
import com.backend.core.repository.comment.CommentLikeRepository;
import com.backend.core.repository.comment.CommentRenderInfoRepo;
import com.backend.core.repository.comment.CommentRepository;
import com.backend.core.repository.customQuery.CustomQueryRepository;
import com.backend.core.repository.customer.CustomerRepository;
import com.backend.core.repository.product.ProductManagementRepository;
import com.backend.core.repository.product.ProductRepository;
import com.backend.core.service.CrudService;
import com.backend.core.util.process.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
    ProductManagementRepository productManagementRepo;

    @Autowired
    CommentLikeRepository commentLikeRepo;

    @Autowired
    ValueRenderUtils valueRenderUtils;

    @Autowired
    private CommentRenderInfoRepo commentRenderInfoRepo;


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

                Customer currentCustomer = customerRepo.getCustomerById(customerId);

                if (comment != null && currentCustomer != null) {
                    CommentLike existedCommentLike = commentLikeRepo.getCommentLikeByCustomerIdAndCommentId(customerId, comment.getId());

                    if (existedCommentLike == null) {
                        comment.likeComment();
                        commentRepo.save(comment);

                        commentLikeRepo.save(new CommentLike(
                                new CommentLikePrimaryKeys(comment.getId(), customerId),
                                currentCustomer,
                                comment
                        ));

                        return new ResponseEntity<>(new ApiResponse("success", "Like comment successfully"), HttpStatus.OK);
                    } else {
                        comment.unlikeComment();
                        commentRepo.save(comment);

                        commentLikeRepo.deleteById(new CommentLikePrimaryKeys(comment.getId(), customerId));

                        return new ResponseEntity<>(new ApiResponse("success", "Unlike comment successfully"), HttpStatus.OK);
                    }
                } else {
                    return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.BAD_REQUEST);
                }
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
            if (paramObj instanceof CommentFilterRequestDTO) {
                CommentFilterRequestDTO commentFilterRequest = (CommentFilterRequestDTO) paramObj;

                String filterQuery = valueRenderUtils.getFilterQuery(commentFilterRequest, FilterTypeEnum.COMMENT, httpRequest, false);

                List<CommentRenderInfoDTO> commentList = customQueryRepo.getBindingFilteredList(filterQuery, CommentRenderInfoDTO.class);
                Collections.reverse(commentList);

                return new ResponseEntity<>(new ApiResponse("success", commentList), HttpStatus.OK);
            } else if (paramObj instanceof CommentRequestDTO) {
                CommentRequestDTO comment = (CommentRequestDTO) paramObj;
                int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(httpRequest);

                List<Integer> commentIdList = commentLikeRepo.getCommentIdListByCustomerIdAndProductColorAndProductId(
                        customerId,
                        comment.getProductColor(),
                        comment.getProductId()
                );

                return new ResponseEntity<>(new ApiResponse("success", commentIdList), HttpStatus.OK);
            }

            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name()), HttpStatus.BAD_REQUEST);
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
            int replyOn = request.getReplyOn();
            List<ProductManagement> pmList = productManagementRepo.getProductsManagementListByProductIDAndColor(
                    request.getProductId(),
                    request.getProductColor()
            );

            if (replyOn < 0 || product == null || pmList.isEmpty() || request.getCommentContent().isBlank()) {
                return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.BAD_REQUEST);
            }

            int orgCommentId = request.getReplyOn() > 0
                    ? getOriginalCommentId(replyOn)
                    : replyOn;

            newComment.setProduct(product);
            newComment.setCommentContent(request.getCommentContent());
            newComment.setProductColor(request.getProductColor());
            newComment.setCustomer(customerRepo.getCustomerById(customerId));
            newComment.setCommentDate(new Date());
            newComment.setReplyOn(orgCommentId);
            commentRepo.save(newComment);

            if (request.getReplyOn() > 0) {
                Comment comment = commentRepo.getCommentById(orgCommentId);
                comment.replyComment();
                commentRepo.save(comment);
            }

            return new ResponseEntity<>(new ApiResponse("success", "Add new comment successfully"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get original comment id that other comments reply on
    public int getOriginalCommentId(int commentId) {
        CommentRenderInfoDTO commentRenderInfo = commentRenderInfoRepo.getCommentById(commentId);

        if (commentRenderInfo.getReplyOn() == 0) {
            return commentRenderInfo.getId();
        } else {
            return getOriginalCommentId(commentRenderInfo.getReplyOn());
        }
    }
}
