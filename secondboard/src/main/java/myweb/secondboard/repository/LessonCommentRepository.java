package myweb.secondboard.repository;

import java.util.List;
import myweb.secondboard.domain.comments.LessonComment;
import myweb.secondboard.domain.comments.NoticeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LessonCommentRepository extends JpaRepository<LessonComment, Long> {

  @Query("select c from LessonComment c where c.lesson.id = :lessonId")
  List<LessonComment> findComments(@Param("lessonId") Long lessonId);

}
