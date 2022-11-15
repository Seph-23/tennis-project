package myweb.secondboard.repository;

import java.util.Optional;
import myweb.secondboard.domain.boards.BoardLike;
import myweb.secondboard.domain.boards.NoticeLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoticeLikeRepository extends JpaRepository<NoticeLike, Long> {

  @Query("select lk from NoticeLike lk where lk.notice.id = :noticeId and lk.member.id = :memberId")
  Optional<NoticeLike> find(@Param("noticeId") Long noticeId, @Param("memberId") Long memberId);

  void deleteAllByNoticeId(Long id);

  Long countByNoticeId(Long id);

}
