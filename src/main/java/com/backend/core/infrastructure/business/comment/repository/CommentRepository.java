package com.backend.core.infrastructure.business.comment.repository;

import com.backend.core.entity.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query(value = "select c.* from comments c join products p on c.product_id = p.id where p.name = :nameVal and p.color = :colorVal order by id asc", nativeQuery = true)
    List<Comment> getCommentsByProductNameAndColor(@Param("nameVal") String name, @Param("colorVal") String color);

    @Query(value = "select * from comments where id = :idVal", nativeQuery = true)
    Comment getCommentById(@Param("idVal") int id);

    @Modifying
    @Transactional
    @Query(value = "delete from comments where id = :idVal", nativeQuery = true)
    void deleteCommentById(@Param("idVal") int commentId);

    @Modifying
    @Transactional
    @Query(value = "delete from comments where reply_on = :idVal", nativeQuery = true)
    void deleteReplyCommentByReplyId(@Param("idVal") int commentId);
}
