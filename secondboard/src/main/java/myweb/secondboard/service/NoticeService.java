package myweb.secondboard.service;

import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.boards.Notice;
import myweb.secondboard.dto.NoticeSaveForm;
import myweb.secondboard.dto.NoticeUpdateForm;
import myweb.secondboard.repository.NoticeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeService {

  private final NoticeRepository noticeRepository;

  public Notice findOne(Long noticeId) {
    return noticeRepository.findOne(noticeId);
  }
  @Transactional
  public Long addNotice(NoticeSaveForm form, Member member) {
    Notice notice = Notice.createNotice(form, member);
    noticeRepository.save(notice);
    return notice.getId();
  }

  @Transactional
  public Page<Notice> getNoticeList(Pageable pageable) {
    return noticeRepository.findAll(pageable);
  }

  @Transactional
  public void increaseView(Long boardId) {
    noticeRepository.updateView(boardId);
  }

  @Transactional
  public void deleteById(Long boardId) {
    noticeRepository.deleteById(boardId);
  }

  @Transactional
  public void update(NoticeUpdateForm form, Member member) {
    Notice notice = noticeRepository.findOne(form.getId()); //조회한 board 엔티티는 영속 상태
    notice.updateNotice(notice, form, member);
    // boardRepository.save(board); 트랜잭션 커밋 시점에 변경 감지(Dirty checking)
  }

  public Page<Notice> searchNotices(String keyword, Pageable pageable) {
    String title = keyword;
    String author = keyword;
    return noticeRepository.findByTitleContainingOrAuthorContaining(title, author, pageable);
  }
}
