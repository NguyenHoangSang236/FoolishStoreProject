package com.backend.core.usecase.usecases.comment;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.comment.gateway.CommentRequestDTO;
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

import java.util.Date;

@Component
public class UpdateCommentUseCase extends UseCase<UpdateCommentUseCase.InputValue, ApiResponse> {
    @Autowired
    ValueRenderUtils valueRenderUtils;
    @Autowired
    CommentRepository commentRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(input.getHttpRequest());

        CommentRequestDTO request = input.getCommentRequest();
        Comment comment = commentRepo.getCommentById(request.getId());

        if (customerId != comment.getCustomer().getId()) {
            return new ApiResponse("failed", ErrorTypeEnum.UNAUTHORIZED.name(), HttpStatus.UNAUTHORIZED);
        }

        comment.setCommentContent(request.getCommentContent());
        comment.setCommentDate(new Date());

        commentRepo.save(comment);

        return new ApiResponse("success", "Update comment successfully", HttpStatus.OK);
    }

    @Value
    public static class InputValue implements InputValues {
        CommentRequestDTO commentRequest;
        HttpServletRequest httpRequest;
    }
}
