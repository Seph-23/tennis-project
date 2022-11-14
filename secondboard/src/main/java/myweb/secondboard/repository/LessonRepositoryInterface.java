package myweb.secondboard.repository;

import myweb.secondboard.domain.boards.Lesson;
import myweb.secondboard.domain.boards.Notice;

public interface LessonRepositoryInterface {

  Lesson findOne(Long lessonId);

  void updateView(Long lessonId);
}
