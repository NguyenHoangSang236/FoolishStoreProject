package com.backend.core.serviceimpl.customer;

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
import com.backend.core.util.CheckUtils;
import com.backend.core.util.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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


    @Override
    public ApiResponse singleCreationalResponse(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        CommentRequestDTO request;

        if(!CheckUtils.loggedIn(session)) {
            return new ApiResponse("failed", ErrorTypeEnum.LOGIN_FIRST.name());
        }
        else {
            request = (CommentRequestDTO) paramObj;

            return addNewComment(request, session);
        }
    }


    @Override
    public ApiResponse listCreationalResponse(List<Object> objList, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ApiResponse removingResponseByRequest(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ApiResponse removingResponseById(int id, HttpSession session, HttpServletRequest httpRequest) {
        Comment comment;
        int customerId = ValueRenderUtils.getCustomerOrStaffIdByHttpSession(session);

        if(!CheckUtils.loggedIn(session)) {
            return new ApiResponse("failed", ErrorTypeEnum.LOGIN_FIRST.name());
        }
        else {
            try {
                comment = commentRepo.getCommentById(id);

                if(customerId != comment.getCustomer().getId()) {
                    return new ApiResponse("failed", ErrorTypeEnum.UNAUTHORIZED.name());
                }

                commentRepo.deleteById(id);

                return new ApiResponse("success", "Update comment successfully");
            }
            catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
            }
        }
    }


    @Override
    public ApiResponse updatingResponseByList(ListRequestDTO listRequestDTO, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public ApiResponse updatingResponseById(int id, HttpSession session, HttpServletRequest httpRequest) {
        Comment comment;
        int customerId = ValueRenderUtils.getCustomerOrStaffIdByHttpSession(session);

        if(!CheckUtils.loggedIn(session)) {
            return new ApiResponse("failed", ErrorTypeEnum.LOGIN_FIRST.name());
        } else if (id == 0) {
            return new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name());
        }
        else {
            try {
                comment = commentRepo.getCommentById(id);

                comment.likeComment();
                commentRepo.save(comment);

                return new ApiResponse("success", "Like comment successfully");
            }
            catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
            }
        }
    }

    @Override
    public ApiResponse updatingResponseByRequest(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        CommentRequestDTO request;
        Comment comment;
        int customerId = ValueRenderUtils.getCustomerOrStaffIdByHttpSession(session);

        if(!CheckUtils.loggedIn(session)) {
            return new ApiResponse("failed", ErrorTypeEnum.LOGIN_FIRST.name());
        }
        else {
            try {
                request = (CommentRequestDTO) paramObj;
                comment = commentRepo.getCommentById(request.getId());

                if(customerId != comment.getCustomer().getId()) {
                    return new ApiResponse("failed", ErrorTypeEnum.UNAUTHORIZED.name());
                }

                comment.setCommentContent(request.getCommentContent());
                comment.setCommentDate(new Date());

                commentRepo.save(comment);

                return new ApiResponse("success", "Delete comment successfully");
            }
            catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
            }
        }
    }


    @Override
    public ApiResponse readingFromSingleRequest(Object paramObj, HttpSession session, HttpServletRequest httpRequest) {
        try {
            CommentFilterRequestDTO commentFilterRequest = (CommentFilterRequestDTO) paramObj;

            String filterQuery = ValueRenderUtils.getFilterQuery(commentFilterRequest, FilterTypeEnum.COMMENT, session);

            List<Comment> commentList = customQueryRepo.getBindingFilteredList(filterQuery, Comment.class);

            return new ApiResponse("success", commentList);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
        }
    }


    @Override
    public ApiResponse readingFromListRequest(List<Object> paramObjList, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ApiResponse readingResponse(HttpSession session, String renderType, HttpServletRequest httpRequest) {
        return null;
    }


    @Override
    public ApiResponse readingById(int id, HttpSession session, HttpServletRequest httpRequest) {
        return null;
    }



    // add new comment process
    public ApiResponse addNewComment(CommentRequestDTO request, HttpSession session) {
        Comment newComment = new Comment();
        Product product;
        int customerId;

        try {
            customerId = ValueRenderUtils.getCustomerOrStaffIdByHttpSession(session);
            product = productRepo.getProductById(request.getProductId());

            newComment.setProduct(product);
            newComment.setCommentContent(request.getCommentContent());
            newComment.setProductColor(request.getProductColor());
            newComment.setCustomer(customerRepo.getCustomerById(customerId));
            newComment.setCommentDate(new Date());
            newComment.setReplyOn(request.getReplyOn());
            commentRepo.save(newComment);

            return new ApiResponse("success", "Add new comment successfully");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name());
        }
    }
}
