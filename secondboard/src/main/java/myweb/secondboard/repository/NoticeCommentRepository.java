package myweb.secondboard.repository;

import java.util.List;
import myweb.secondboard.domain.comments.NoticeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoticeCommentRepository extends JpaRepository<NoticeComment, Long> {

    @Query("select c from NoticeComment c where c.notice.id = :noticeId")
    List<NoticeComment> findComments(@Param("noticeId") Long noticeId);
}