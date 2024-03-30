package com.backend.core.infrastructure.business.comment.repository;

import com.backend.core.entity.comment.key.CommentLikePrimaryKeys;
import com.backend.core.entity.comment.model.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikePrimaryKeys> {
    @Query(value = "select comment_id from comment_like ck join comments c on c.id = ck.comment_id where ck.customer_id = :customerId and product_color = :productColor and product_id = :productId", nativeQuery = true)
    List<Integer> getCommentIdListByCustomerIdAndProductColorAndProductId(@Param("customerId") int customerId, @Param("productColor") String productColor, @Param("productId") int productId);

    @Query(value = "select * from comment_like where customer_id = :customerId and comment_id = :commentId", nativeQuery = true)
    CommentLike getCommentLikeByCustomerIdAndCommentId(@Param("customerId") int customerId, @Param("commentId") int commentId);
}
