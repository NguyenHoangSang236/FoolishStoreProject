package com.backend.core.usecase.business.comment;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.comment.gateway.CommentFilterRequestDTO;
import com.backend.core.infrastructure.business.comment.dto.CommentRenderInfoDTO;
import com.backend.core.infrastructure.config.database.CustomQueryRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.FilterTypeEnum;
import com.backend.core.usecase.util.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class FilterCommentUseCase extends UseCase<FilterCommentUseCase.InputValue, ApiResponse> {
    @Autowired
    ValueRenderUtils valueRenderUtils;
    @Autowired
    CustomQueryRepository customQueryRepo;


    @Override
    public ApiResponse execute(InputValue input) {
        String filterQuery = valueRenderUtils.getFilterQuery(
                input.getCommentFilterRequest(),
                FilterTypeEnum.COMMENT,
                input.getHttpRequest(),
                false
        );

        List<CommentRenderInfoDTO> commentList = customQueryRepo.getBindingFilteredList(filterQuery, CommentRenderInfoDTO.class);
        Collections.reverse(commentList);

        return new ApiResponse("success", commentList, HttpStatus.OK);
    }

    @Value
    public static class InputValue implements InputValues {
        CommentFilterRequestDTO commentFilterRequest;
        HttpServletRequest httpRequest;
    }
}
