package com.backend.core.usecase.business.comment;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.comment.gateway.CommentRequestDTO;
import com.backend.core.infrastructure.business.comment.repository.CommentLikeRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.util.process.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ViewCommentIdYouLikedUseCase extends UseCase<ViewCommentIdYouLikedUseCase.InputValue, ApiResponse> {
    @Autowired
    CommentLikeRepository commentLikeRepo;
    @Autowired
    ValueRenderUtils valueRenderUtils;


    @Override
    public ApiResponse execute(InputValue input) {
        CommentRequestDTO commentRequest = input.getCommentRequest();
        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(input.getHttpRequest());

        List<Integer> commentIdList = commentLikeRepo.getCommentIdListByCustomerIdAndProductColorAndProductId(
                customerId,
                commentRequest.getProductColor(),
                commentRequest.getProductId()
        );

        return new ApiResponse("success", commentIdList, HttpStatus.OK);
    }

    @Value
    public static class InputValue implements InputValues {
        CommentRequestDTO commentRequest;
        HttpServletRequest httpRequest;
    }
}
