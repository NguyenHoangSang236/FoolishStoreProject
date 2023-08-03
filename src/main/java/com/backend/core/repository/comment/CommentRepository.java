package com.backend.core.repository.comment;

import com.backend.core.entities.tableentity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{
    @Query(value = "select c.* from comments c join products p on c.product_id = p.id where p.name = :nameVal and p.color = :colorVal order by id asc", nativeQuery = true)
    List<Comment> getCommentsByProductNameAndColor(@Param("nameVal") String name, @Param("colorVal") String color);

    @Query(value = "select * from comments where id = :idVal", nativeQuery = true)
    Comment getCommentById(@Param("idVal") int id);
}
