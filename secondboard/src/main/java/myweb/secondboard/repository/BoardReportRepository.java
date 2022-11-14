package myweb.secondboard.repository;

import myweb.secondboard.domain.boards.BoardReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardReportRepository extends JpaRepository<BoardReport, Long> {

  @Query("select rp from BoardReport rp where rp.board.id = :boardId and rp.member.id = :memberId")
  Optional<BoardReport> find(@Param("boardId") Long boardId, @Param("memberId") Long memberId);

  void deleteAllByBoardId(Long id);

  Long countByBoardId(Long id);
}
