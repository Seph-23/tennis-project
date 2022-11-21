package myweb.secondboard.service;

import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.boards.BoardReport;
import myweb.secondboard.repository.BoardReportRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardReportService {

  private final BoardReportRepository boardReportRepository;

  @Transactional
  public void addReport(Board board, Member member, String content) {
    BoardReport report = BoardReport.createReport(board, member, content);
    boardReportRepository.save(report);
  }

  public String checkReport(Long boardId, Long memberId) {
    Optional<BoardReport> reportCheck = boardReportRepository.find(boardId, memberId);
    if (reportCheck.isEmpty()) {
      return "not";
    } else {
      return "is";
    }
  }

  public Long getReportCount(Long boardId) {
//    return boardReportRepository.findAll().stream().filter(report -> report.getBoard().getId() == boardId).toList().size();
  return boardReportRepository.countByBoardId(boardId);
  }

  public Page<BoardReport> findAll(Pageable page) {
    return boardReportRepository.findAll(page);
  }
}
