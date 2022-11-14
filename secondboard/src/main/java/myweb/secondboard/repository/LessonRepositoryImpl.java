package myweb.secondboard.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.boards.Lesson;
import myweb.secondboard.domain.boards.Notice;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LessonRepositoryImpl implements LessonRepositoryInterface {

  @PersistenceContext
  private final EntityManager em;

  public Lesson findOne(Long lessonId) {
    return em.find(Lesson.class, lessonId);
  }
  public void updateView(Long lessonId) {
    Lesson lesson = findOne(lessonId);
    lesson.updateView(lesson);
  }
}