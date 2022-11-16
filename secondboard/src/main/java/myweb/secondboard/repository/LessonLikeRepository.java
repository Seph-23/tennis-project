package myweb.secondboard.repository;

import myweb.secondboard.domain.boards.BoardLike;
import myweb.secondboard.domain.boards.LessonLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LessonLikeRepository extends JpaRepository<LessonLike, Long>{

  @Query("select lk from LessonLike lk where lk.lesson.id = :lessonId and lk.member.id = :memberId")
  Optional<LessonLike> find(@Param("lessonId") Long lessonId, @Param("memberId") Long memberId);

  void deleteAllByLessonId(Long id);

  Long countByLessonId(Long id);
}
