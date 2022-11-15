package myweb.secondboard.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.boards.BoardLike;
import myweb.secondboard.domain.boards.Notice;
import myweb.secondboard.domain.boards.NoticeLike;
import myweb.secondboard.repository.NoticeLikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeLikeService {

  private final NoticeLikeRepository noticeLikeRepository;

  public Long getLikeCount(Long noticeId) {
    return noticeLikeRepository.countByNoticeId(noticeId);
  }

  public String checkLike(Long noticeId, Long memberId) {
    Optional<NoticeLike> likeCheck = noticeLikeRepository.find(noticeId, memberId);

    if (likeCheck.isEmpty()) {
      return "not";
    } else {
      return "is";
    }
  }

  @Transactional
  public int clickLike(Notice notice, Member member) {
    NoticeLike likeCheck = noticeLikeRepository.find(notice.getId(), member.getId()).orElse(null);
    if (likeCheck == null) {
      NoticeLike like = NoticeLike.createLike(notice, member);
      noticeLikeRepository.save(like);
      notice.setLikeCount(notice.getLikeCount() + 1);
      return 1;
    } else {
      noticeLikeRepository.delete(likeCheck);
      notice.setLikeCount(notice.getLikeCount() - 1);
      return 0;
    }
  }

  public void deleteById(Long noticeId) {
    noticeLikeRepository.deleteById(noticeId);
  }
}
