package myweb.secondboard.service;

import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.boards.Question;
import myweb.secondboard.dto.QuestionSaveForm;
import myweb.secondboard.dto.QuestionUpdateForm;
import myweb.secondboard.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {

  private final QuestionRepository questionRepository;

  public Question findOne(Long questionId) {
    return questionRepository.findOne(questionId);
  }

  @Transactional
  public Long addQuestion(QuestionSaveForm form, Member member) {
    Question question = Question.createQuestion(form, member);
    questionRepository.save(question);
    return question.getId();
  }

  @Transactional
  public Page<Question> getQuestionList(Pageable pageable) {
    return questionRepository.findAll(pageable);
  }

  @Transactional
  public void increaseView(Long boardId) {
    questionRepository.updateView(boardId);
  }

  @Transactional
  public void deleteById(Long boardId) {
    questionRepository.deleteById(boardId);
  }

  @Transactional
  public void update(QuestionUpdateForm form, Member member) {
    Question question = questionRepository.findOne(form.getId()); //조회한 board 엔티티는 영속 상태
    question.updateQuestion(question, form, member);
    // boardRepository.save(board); 트랜잭션 커밋 시점에 변경 감지(Dirty checking)
  }
  public Page<Question> searchQuestions(String keyword, Pageable pageable) {
    String title = keyword;
    String author = keyword;
    return questionRepository.findByTitleContainingOrAuthorContaining(title, author, pageable);
  }
}
