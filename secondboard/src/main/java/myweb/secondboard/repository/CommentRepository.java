package myweb.secondboard.repository;

import myweb.secondboard.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryInterface {

    @Query("select c from Comment c where c.board.id = :boardId")
    List<Comment> findComments(@Param("boardId") Long boardId);
}