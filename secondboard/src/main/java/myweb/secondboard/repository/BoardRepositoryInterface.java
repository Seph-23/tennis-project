package myweb.secondboard.repository;

import myweb.secondboard.domain.Board;

public interface BoardRepositoryInterface {

  void save(Board board);

  Board findOne(Long boardId);

  void updateView(Long boardId);
}
