package com.backend.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.core.entity.Comment;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{
    @Query(value = "select c.* from comments c join products p on c.product_id = p.id where p.name = :nameVal and p.color = :colorVal order by id asc", nativeQuery = true)
    List<Comment> getCommentsByProductNameAndColor(@Param("nameVal") String name, @Param("colorVal") String color);
    
    
    @Query(value = "select id from comments order by id desc limit 1", nativeQuery = true)
    int getLastestCommentId();
}
