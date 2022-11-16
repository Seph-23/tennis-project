package myweb.secondboard.service;

import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Comment;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.comments.BoardCommentReport;
import myweb.secondboard.domain.comments.LessonComment;
import myweb.secondboard.domain.comments.LessonCommentReport;
import myweb.secondboard.repository.BoardCommentReportRepository;
import myweb.secondboard.repository.LessonCommentReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LessonCommentReportService {

  private final LessonCommentReportRepository lessonCommentReportRepository;

  @Transactional
  public void addReport(LessonComment comment, Member member, String content) {
    LessonCommentReport report = LessonCommentReport.createReport(comment, member, content);
    comment.setReportCount(comment.getReportCount() + 1);
    lessonCommentReportRepository.save(report);
  }

  public String checkReport(Long commentId, Long memberId) {
    Optional<LessonCommentReport> reportCheck = lessonCommentReportRepository.find(commentId, memberId);
    if (reportCheck.isEmpty()) {
      return "not";
    } else {
      return "is";
    }
  }

  public Long getReportCount(Long commentId) {
    return lessonCommentReportRepository.countByLessonCommentId(commentId);
  }
}
