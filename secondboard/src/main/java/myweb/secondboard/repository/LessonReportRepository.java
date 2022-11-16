package myweb.secondboard.repository;

import myweb.secondboard.domain.boards.LessonReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LessonReportRepository extends JpaRepository<LessonReport, Long> {

  @Query("select lp from LessonReport lp where lp.lesson.id = :lessonId and lp.member.id = :memberId")
  Optional<LessonReport> find(@Param("lessonId") Long lessonId, @Param("memberId") Long memberId);

  void deleteAllByLessonId(Long id);

  Long countByLessonId(Long id);
}
