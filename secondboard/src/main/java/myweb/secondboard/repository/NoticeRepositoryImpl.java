package myweb.secondboard.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.boards.Notice;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryInterface {

  @PersistenceContext
  private final EntityManager em;

  public Notice findOne(Long noticeId) {
    return em.find(Notice.class, noticeId);
  }
  public void updateView(Long noticeId) {
    Notice notice = findOne(noticeId);
    notice.updateView(notice);
  }
}