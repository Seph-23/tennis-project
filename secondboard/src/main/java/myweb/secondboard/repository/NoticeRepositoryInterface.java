package myweb.secondboard.repository;

import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.boards.Notice;

public interface NoticeRepositoryInterface {

  Notice findOne(Long noticeId);

  void updateView(Long noticeId);
}
