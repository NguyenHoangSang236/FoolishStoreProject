package com.backend.core.repository.comment;

import com.backend.core.entities.embededkey.CommentLikePrimaryKeys;
import com.backend.core.entities.tableentity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikePrimaryKeys> {
    @Query(value = "select comment_id from comment_like ck join comments c on c.id = ck.comment_id where ck.customer_id = :customerId and product_color = :productColor and product_id = :productId", nativeQuery = true)
    List<Integer> getCommentIdListByCustomerIdAndProductColorAndProductId(@Param("customerId") int customerId, @Param("productColor") String productColor, @Param("productId") int productId);
}
