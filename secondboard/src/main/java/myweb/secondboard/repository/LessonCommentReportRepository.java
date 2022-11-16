package myweb.secondboard.repository;

import myweb.secondboard.domain.comments.BoardCommentReport;
import myweb.secondboard.domain.comments.LessonCommentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LessonCommentReportRepository extends JpaRepository<LessonCommentReport, Long> {
  @Query("select lp from LessonCommentReport lp where lp.lessonComment.id = :lessonCommentId and lp.member.id = :memberId")
  Optional<LessonCommentReport> find(@Param("lessonCommentId") Long lessonCommentId, @Param("memberId") Long memberId);

  void deleteAllByLessonCommentId(Long id);

  Long countByLessonCommentId(Long id);
}
