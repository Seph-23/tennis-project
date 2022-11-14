package myweb.secondboard.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.boards.Lesson;
import myweb.secondboard.domain.boards.Question;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepositoryInterface {
  @PersistenceContext
  private final EntityManager em;

  public Question findOne(Long questionId) {
    return em.find(Question.class, questionId);
  }
  public void updateView(Long questionId) {
    Question question = findOne(questionId);
    question.updateView(question);
  }

}
