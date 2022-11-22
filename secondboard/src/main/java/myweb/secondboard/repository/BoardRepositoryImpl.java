package myweb.secondboard.repository;

import static myweb.secondboard.domain.QBoard.board;
import static myweb.secondboard.domain.boards.QBoardLike.boardLike;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Board;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryInterface {

  @PersistenceContext
  private final EntityManager em;
  private final JPAQueryFactory queryFactory;

  public Board findOne(Long boardId) {
    return em.find(Board.class, boardId);
  }

  public void updateView(Long boardId) {
    Board board = findOne(boardId);
    board.updateView(board);
  }

  public List<Long> getHotBoards(LocalDate date) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
    String dateFormatted = date.format(dtf);

    List<Tuple> cnt = queryFactory
      .select(board.id, board.id.count().as("cnt"))
      .from(boardLike)
      .groupBy(board.id)
      .orderBy(board.id.count().desc())
      .where(boardLike.createdDate.eq(dateFormatted))
      .limit(10)
      .fetch();

    List<Long> hotBoardList = new ArrayList<>();
    for (Tuple tuple : cnt) {
      hotBoardList.add(tuple.get(board.id));
    }

    return hotBoardList;
  }
}