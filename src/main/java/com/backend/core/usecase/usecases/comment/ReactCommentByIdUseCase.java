package com.backend.core.usecase.usecases.comment;

import com.backend.core.entity.account.model.Customer;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.comment.gateway.CommentRequestDTO;
import com.backend.core.entity.comment.key.CommentLikePrimaryKeys;
import com.backend.core.entity.comment.model.Comment;
import com.backend.core.entity.comment.model.CommentLike;
import com.backend.core.infrastructure.business.account.repository.CustomerRepository;
import com.backend.core.infrastructure.business.comment.repository.CommentLikeRepository;
import com.backend.core.infrastructure.business.comment.repository.CommentRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.util.process.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ReactCommentByIdUseCase extends UseCase<ReactCommentByIdUseCase.InputValue, ApiResponse> {
    @Autowired
    ValueRenderUtils valueRenderUtils;
    @Autowired
    CommentRepository commentRepo;
    @Autowired
    CustomerRepository customerRepo;
    @Autowired
    CommentLikeRepository commentLikeRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        int commentId = input.getCommentId();
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(input.getHttpRequest());

        if (commentId == 0) {
            return new ApiResponse("failed", ErrorTypeEnum.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST);
        } else {
            Comment comment = commentRepo.getCommentById(commentId);

            Customer currentCustomer = customerRepo.getCustomerById(customerId);

            if (comment != null && currentCustomer != null) {
                CommentLike existedCommentLike = commentLikeRepo.getCommentLikeByCustomerIdAndCommentId(customerId, comment.getId());

                if (existedCommentLike == null) {
                    comment.likeComment();
                    commentRepo.save(comment);

                    CommentLike cmtLike = new CommentLike(
                            new CommentLikePrimaryKeys(comment.getId(), customerId),
                            currentCustomer,
                            comment
                    );
                    commentLikeRepo.save(cmtLike);

                    return new ApiResponse("success", "Like comment successfully", HttpStatus.OK);
                } else {
                    comment.unlikeComment();
                    commentRepo.save(comment);

                    commentLikeRepo.deleteById(new CommentLikePrimaryKeys(comment.getId(), customerId));

                    return new ApiResponse("success", "Unlike comment successfully", HttpStatus.OK);
                }
            } else {
                return new ApiResponse("failed", ErrorTypeEnum.NO_DATA_ERROR.name(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @Value
    public static class InputValue implements InputValues {
        int commentId;
        HttpServletRequest httpRequest;
    }
}
