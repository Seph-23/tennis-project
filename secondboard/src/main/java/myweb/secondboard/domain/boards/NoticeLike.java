package myweb.secondboard.domain.boards;

import static javax.persistence.FetchType.LAZY;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Member;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class NoticeLike {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "like_id")
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "notice_id")
  private Notice notice;

  @NotNull
  @CreatedDate
  @Column(length = 40)
  private String createdDate;

  public static NoticeLike createLike(Notice notice, Member member) {
    NoticeLike like = new NoticeLike();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    like.setNotice(notice);
    like.setMember(member);
    like.setCreatedDate(LocalDateTime.now().format(dtf));

    return like;
  }


}
