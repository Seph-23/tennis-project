package myweb.secondboard.domain.comments;

import static javax.persistence.FetchType.LAZY;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.boards.Notice;

@Entity
@Getter
@Setter
public class NoticeComment extends CommentAbstract{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "notice_comment_id")
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "notice_id")
  private Notice notice;

  public static NoticeComment createComment(Map<String, Object> param, Notice notice,
      Member member) {
    NoticeComment noticeComment = new NoticeComment();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    noticeComment.setAuthor(member.getNickname());
    noticeComment.setContent(param.get("content").toString());
    noticeComment.setNotice(notice);
    noticeComment.setMember(member);
    noticeComment.setCreatedDate(LocalDateTime.now().format(dtf));
    noticeComment.setModifiedDate(LocalDateTime.now().format(dtf));
    return noticeComment;
  }

  public void updateComment(Long commentId, Map<String, Object> param,
      NoticeComment noticeComment) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
    noticeComment.setContent(param.get("content").toString());
    noticeComment.setModifiedDate(LocalDateTime.now().format(dtf));
  }
}
