package myweb.secondboard.repository;

import myweb.secondboard.domain.boards.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> , LessonRepositoryInterface{
  Page<Lesson> findByTitleContainingOrAuthorContaining(String title, String author, Pageable pageable);
}
