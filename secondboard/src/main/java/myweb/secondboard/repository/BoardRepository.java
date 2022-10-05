package myweb.secondboard.repository;

import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Board;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

  private final EntityManager em;

  public void save(Board board) {      //보드 엔티티를 디비에 저장.
    em.persist(board);
  }

  public Board findOne(Long boardId) {
    return em.find(Board.class, boardId);
  }

  public void updateView(Long boardId) {
    Board board = findOne(boardId);
    board.updateView(board);
  }
}
