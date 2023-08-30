package com.backend.core.repository.comment;

import com.backend.core.entities.renderdto.CommentRenderInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRenderInfoRepo extends JpaRepository<CommentRenderInfoDTO, Integer> {
    @Query(value = "select * from comment_info_for_ui where id = :idVal", nativeQuery = true)
    CommentRenderInfoDTO getCommentById(@Param("idVal") int id);
}
