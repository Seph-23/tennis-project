package myweb.secondboard.repository;

import myweb.secondboard.domain.boards.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

}
