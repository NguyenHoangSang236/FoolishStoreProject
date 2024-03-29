package com.backend.core.usecase.business.comment;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.comment.model.Comment;
import com.backend.core.infrastructure.business.comment.repository.CommentRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.util.process.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class DeleteCommentByIdUseCase extends UseCase<DeleteCommentByIdUseCase.InputValue, ApiResponse> {
    @Autowired
    ValueRenderUtils valueRenderUtils;
    @Autowired
    CommentRepository commentRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(input.getHttpRequest());
        int commentId = input.getCommentId();

        Comment comment = commentRepo.getCommentById(commentId);

        if (customerId != comment.getCustomer().getId()) {
            return new ApiResponse("failed", ErrorTypeEnum.UNAUTHORIZED.name(), HttpStatus.UNAUTHORIZED);
        }

        commentRepo.deleteCommentById(commentId);
        commentRepo.deleteReplyCommentByReplyId(commentId);

        return new ApiResponse("success", "Delete comment successfully", HttpStatus.OK);
    }

    @Value
    public static class InputValue implements InputValues {
        int commentId;
        HttpServletRequest httpRequest;
    }
}
