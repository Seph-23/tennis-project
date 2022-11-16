package myweb.secondboard.domain.boards;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myweb.secondboard.domain.Member;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class LessonLike {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "lesson_like_id")
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "lesson_id")
  private Lesson lesson;

  @NotNull
  @CreatedDate
  @Column(length = 40)
  private String createdDate;

  public static LessonLike createLike(Lesson lesson, Member member) {
    LessonLike like = new LessonLike();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    like.setLesson(lesson);
    like.setMember(member);
    like.setCreatedDate(LocalDateTime.now().format(dtf));

    return like;
  }
}
