package myweb.secondboard.repository;

import myweb.secondboard.domain.Board;

public interface BoardRepositoryInterface {

  Board findOne(Long boardId);

  void updateView(Long boardId);
}
