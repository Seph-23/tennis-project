package myweb.secondboard.domain.comments;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myweb.secondboard.domain.Comment;
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
public class LessonCommentReport {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "lesson_comment_report_id")
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id")
  private Member member; // 신고한 사람

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "lesson_comment_id")
  private LessonComment lessonComment;

  @NotNull
  @Column(length = 145)
  private String content;

  @NotNull
  @CreatedDate
  @Column(length = 40)
  private String createdDate;

  public static LessonCommentReport createReport(LessonComment comment, Member member, String content) {
    LessonCommentReport report = new LessonCommentReport();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
    report.setContent(content);
    report.setLessonComment(comment);
    report.setMember(member);
    report.setCreatedDate(LocalDateTime.now().format(dtf));

    return report;
  }
}