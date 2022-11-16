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
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.boards.Lesson;
import myweb.secondboard.domain.boards.Notice;

@Entity
@Getter @Setter
public class LessonComment extends CommentAbstract{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "lesson_comment_id")
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "lesson_id")
  private Lesson lesson;

  @NotNull
  private Integer reportCount;

  public static LessonComment createComment(Map<String, Object> param, Lesson lesson,
      Member member) {
    LessonComment lessonComment = new LessonComment();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    lessonComment.setAuthor(member.getNickname());
    lessonComment.setContent(param.get("content").toString());
    lessonComment.setLesson(lesson);
    lessonComment.setMember(member);
    lessonComment.setCreatedDate(LocalDateTime.now().format(dtf));
    lessonComment.setModifiedDate(LocalDateTime.now().format(dtf));
    lessonComment.setReportCount(0);
    return lessonComment;
  }

  public void updateComment(Long commentId, Map<String, Object> param,
      LessonComment lessonComment) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
    lessonComment.setContent(param.get("content").toString());
    lessonComment.setModifiedDate(LocalDateTime.now().format(dtf));
  }

}
