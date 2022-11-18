package myweb.secondboard.repository;

import java.time.LocalDate;
import java.util.List;
import myweb.secondboard.domain.Board;

public interface BoardRepositoryInterface {

  Board findOne(Long boardId);

  void updateView(Long boardId);

  List<Long> getHotBoards(LocalDate date);
}
