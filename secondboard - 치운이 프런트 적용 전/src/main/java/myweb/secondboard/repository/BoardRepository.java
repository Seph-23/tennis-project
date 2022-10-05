package myweb.secondboard.repository;

import java.util.List;
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

}
