package myweb.secondboard.repository;

import myweb.secondboard.domain.boards.Lesson;
import myweb.secondboard.domain.boards.Question;

public interface QuestionRepositoryInterface {

  Question findOne(Long questionId);

  void updateView(Long questionId);

}
