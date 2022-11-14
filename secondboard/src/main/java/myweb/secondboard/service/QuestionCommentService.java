package myweb.secondboard.service;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.boards.Notice;
import myweb.secondboard.domain.boards.Question;
import myweb.secondboard.domain.comments.NoticeComment;
import myweb.secondboard.domain.comments.QuestionComment;
import myweb.secondboard.repository.QuestionCommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionCommentService {

  private final QuestionCommentRepository questionCommentRepository;

  public List<QuestionComment> findComments(Long questionId) {
    return questionCommentRepository.findComments(questionId);
  }


  @Transactional
  public void save(Map<String, Object> param, Question question, Member member) {
    QuestionComment questionComment = QuestionComment.createComment(param, question, member);
    questionCommentRepository.save(questionComment);
  }

  @Transactional
  public void deleteById(Long commentId) {
    questionCommentRepository.deleteById(commentId);
  }

  @Transactional
  public void updateComment(Long commentId, Map<String, Object> param) {
    QuestionComment questionComment = questionCommentRepository.findById(commentId).get();
    questionComment.updateComment(commentId, param, questionComment);
  }

  @Transactional
  public void updateCommentCancel(Long commentId) {
    QuestionComment questionComment = questionCommentRepository.findById(commentId).get();
  }



}
