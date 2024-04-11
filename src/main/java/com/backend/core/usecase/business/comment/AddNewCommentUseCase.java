package com.backend.core.usecase.business.comment;

import com.backend.core.entity.account.model.Account;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.entity.comment.gateway.CommentRequestDTO;
import com.backend.core.entity.comment.model.Comment;
import com.backend.core.entity.product.model.Product;
import com.backend.core.entity.product.model.ProductManagement;
import com.backend.core.entity.websocket.WebSocketMessage;
import com.backend.core.infrastructure.business.account.repository.CustomerRepository;
import com.backend.core.infrastructure.business.comment.dto.CommentRenderInfoDTO;
import com.backend.core.infrastructure.business.comment.repository.CommentRenderInfoRepository;
import com.backend.core.infrastructure.business.comment.repository.CommentRepository;
import com.backend.core.infrastructure.business.product.repository.ProductManagementRepository;
import com.backend.core.infrastructure.business.product.repository.ProductRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.util.ValueRenderUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class AddNewCommentUseCase extends UseCase<AddNewCommentUseCase.InputValue, ApiResponse> {
    @Autowired
    ValueRenderUtils valueRenderUtils;
    @Autowired
    ProductRepository productRepo;
    @Autowired
    ProductManagementRepository productManagementRepo;
    @Autowired
    CustomerRepository customerRepo;
    @Autowired
    CommentRenderInfoRepository commentRenderInfoRepository;
    @Autowired
    CommentRepository commentRepo;
    @Autowired
    private SimpMessageSendingOperations messagingOperation;


    @Override
    public ApiResponse execute(InputValue input) {
        Comment newComment = new Comment();
        CommentRequestDTO request = input.getCommentRequest();

        int customerId = valueRenderUtils.getCustomerOrStaffIdFromRequest(input.getHttpRequest());
        Product product = productRepo.getProductById(request.getProductId());
        int replyOn = request.getReplyOn();
        List<ProductManagement> pmList = productManagementRepo.getProductsManagementListByProductIDAndColor(
                request.getProductId(),
                request.getProductColor()
        );

        if (replyOn < 0 || product == null || pmList.isEmpty() || request.getCommentContent().isBlank()) {
            return new ApiResponse("failed", ErrorTypeEnum.TECHNICAL_ERROR.name(), HttpStatus.BAD_REQUEST);
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

        Account currentAccount = valueRenderUtils.getCurrentAccountFromRequest(input.getHttpRequest());
        CommentRenderInfoDTO renderComment = new CommentRenderInfoDTO();
        renderComment.getDataFromComment(newComment);

        WebSocketMessage msg = WebSocketMessage
                .builder()
                .sender(currentAccount.getUsername())
                .type(WebSocketMessage.MessageType.POST_COMMENT)
                .content(renderComment)
                .build();

        messagingOperation.convertAndSend("/comment/" + request.getProductId() + "/" + request.getProductColor(), msg);

        return new ApiResponse("success", "Add new comment successfully", HttpStatus.OK);
    }

    public int getOriginalCommentId(int commentId) {
        CommentRenderInfoDTO commentRenderInfo = commentRenderInfoRepository.getCommentById(commentId);

        if (commentRenderInfo.getReplyOn() == 0) {
            return commentRenderInfo.getId();
        } else {
            return getOriginalCommentId(commentRenderInfo.getReplyOn());
        }
    }

    @Value
    public static class InputValue implements InputValues {
        CommentRequestDTO commentRequest;
        HttpServletRequest httpRequest;
    }
}
