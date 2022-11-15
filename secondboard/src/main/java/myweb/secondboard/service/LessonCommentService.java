package myweb.secondboard.service;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.boards.Lesson;
import myweb.secondboard.domain.boards.Notice;
import myweb.secondboard.domain.comments.LessonComment;
import myweb.secondboard.domain.comments.NoticeComment;
import myweb.secondboard.repository.LessonCommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LessonCommentService {

  private final LessonCommentRepository lessonCommentRepository;

  public List<LessonComment> findComments(Long lessonId) {
    return lessonCommentRepository.findComments(lessonId);
  }



  @Transactional
  public void save(Map<String, Object> param, Lesson lesson, Member member) {
    LessonComment lessonComment = LessonComment.createComment(param, lesson, member);
    lessonCommentRepository.save(lessonComment);
  }

  @Transactional
  public void deleteById(Long commentId) {
    lessonCommentRepository.deleteById(commentId);
  }

  @Transactional
  public void updateComment(Long commentId, Map<String, Object> param) {
    LessonComment lessonComment = lessonCommentRepository.findById(commentId).get();
    lessonComment.updateComment(commentId, param, lessonComment);
  }

  @Transactional
  public void updateCommentCancel(Long commentId) {
    LessonComment lessonComment = lessonCommentRepository.findById(commentId).get();
  }




}
