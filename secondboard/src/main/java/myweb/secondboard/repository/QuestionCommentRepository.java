package myweb.secondboard.repository;

import java.util.List;
import myweb.secondboard.domain.comments.NoticeComment;
import myweb.secondboard.domain.comments.QuestionComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionCommentRepository extends JpaRepository<QuestionComment, Long> {

    @Query("select c from QuestionComment c where c.question.id = :questionId")
    List<QuestionComment> findComments(@Param("questionId") Long questionId);
}