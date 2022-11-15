package myweb.secondboard.domain.boards;

import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.LAZY;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Comment;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.boards.BoardAbstract;
import myweb.secondboard.domain.comments.NoticeComment;
import myweb.secondboard.dto.NoticeSaveForm;
import myweb.secondboard.dto.NoticeUpdateForm;

@Entity
@Getter @Setter
public class Notice extends BoardAbstract {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "notice_id")
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @OneToMany(mappedBy = "notice", cascade = REMOVE)
  private List<NoticeComment> comments = new ArrayList<>();

  @Column(columnDefinition = "integer default 0")
  private Long likeCount;

  public static Notice createNotice(NoticeSaveForm form, Member member) {
    Notice notice = new Notice();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    notice.setTitle(form.getTitle());
    notice.setContent(form.getContent());
    notice.setAuthor(member.getNickname());
    notice.setViews(0);
    notice.setLikeCount(0L);
    notice.setCreatedDate(LocalDateTime.now().format(dtf));
    notice.setModifiedDate(LocalDateTime.now().format(dtf));
    notice.setMember(member);
    return notice;
  }

  public void updateNotice(Notice notice, NoticeUpdateForm form, Member member) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    notice.setId(form.getId());
    notice.setTitle(form.getTitle());
    notice.setContent(form.getContent());
    notice.setAuthor(member.getNickname());
    notice.setModifiedDate(LocalDateTime.now().format(dtf));
    notice.setMember(member);
  }

  public void updateView(Notice notice) {
    notice.setViews(notice.getViews() + 1);
  }

}
