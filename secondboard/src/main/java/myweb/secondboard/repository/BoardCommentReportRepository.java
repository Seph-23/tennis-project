package myweb.secondboard.repository;

import myweb.secondboard.domain.boards.BoardReport;
import myweb.secondboard.domain.comments.BoardCommentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardCommentReportRepository extends JpaRepository<BoardCommentReport, Long> {
  @Query("select rp from BoardCommentReport rp where rp.comment.id = :commentId and rp.member.id = :memberId")
  Optional<BoardCommentReport> find(@Param("commentId") Long commentId, @Param("memberId") Long memberId);

  void deleteAllByCommentId(Long id);

  Long countByCommentId(Long id);
}
