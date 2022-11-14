package myweb.secondboard.repository;

import myweb.secondboard.domain.boards.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long>{
  @Query("select lk from BoardLike lk where lk.board.id = :boardId and lk.member.id = :memberId")
  Optional<BoardLike> find(@Param("boardId") Long boardId, @Param("memberId") Long memberId);

  void deleteAllByBoardId(Long id);

  Long countByBoardId(Long id);
}
