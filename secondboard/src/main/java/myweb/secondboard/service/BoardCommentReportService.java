package myweb.secondboard.service;

import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Comment;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.boards.BoardReport;
import myweb.secondboard.domain.comments.BoardCommentReport;
import myweb.secondboard.repository.BoardCommentReportRepository;
import myweb.secondboard.repository.BoardReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardCommentReportService {

  private final BoardCommentReportRepository boardCommentReportRepository;

  @Transactional
  public void addReport(Comment comment, Member member, String content) {
    BoardCommentReport report = BoardCommentReport.createReport(comment, member, content);
    comment.setReportCount(comment.getReportCount() + 1);
    boardCommentReportRepository.save(report);
  }

  public String checkReport(Long commentId, Long memberId) {
    Optional<BoardCommentReport> reportCheck = boardCommentReportRepository.find(commentId, memberId);
    if (reportCheck.isEmpty()) {
      return "not";
    } else {
      return "is";
    }
  }

  public Long getReportCount(Long commentId) {
    return boardCommentReportRepository.countByCommentId(commentId);
  }
}
