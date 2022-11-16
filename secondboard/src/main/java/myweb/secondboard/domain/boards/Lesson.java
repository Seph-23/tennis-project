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
import myweb.secondboard.domain.comments.LessonComment;
import myweb.secondboard.dto.BoardSaveForm;
import myweb.secondboard.dto.BoardUpdateForm;
import myweb.secondboard.dto.LessonSaveForm;
import myweb.secondboard.dto.LessonUpdateForm;
import myweb.secondboard.dto.NoticeSaveForm;
import myweb.secondboard.dto.NoticeUpdateForm;

@Entity
@Getter @Setter
public class Lesson extends BoardAbstract {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "lesson_id")
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @OneToMany(mappedBy = "lesson", cascade = REMOVE)
  private List<LessonComment> comments = new ArrayList<>();

  @Column(columnDefinition = "integer default 0")
  private Long likeCount;

  public static Lesson createLesson(LessonSaveForm form, Member member) {
    Lesson lesson = new Lesson();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    lesson.setTitle(form.getTitle());
    lesson.setContent(form.getContent());
    lesson.setAuthor(member.getNickname());
    lesson.setViews(0);
    lesson.setCreatedDate(LocalDateTime.now().format(dtf));
    lesson.setModifiedDate(LocalDateTime.now().format(dtf));
    lesson.setLikeCount(0L);
    lesson.setMember(member);
    return lesson;
  }

  public void updateLesson(Lesson lesson, LessonUpdateForm form, Member member) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    lesson.setId(form.getId());
    lesson.setTitle(form.getTitle());
    lesson.setContent(form.getContent());
    lesson.setAuthor(member.getNickname());
    lesson.setModifiedDate(LocalDateTime.now().format(dtf));
    lesson.setMember(member);
  }

  public void updateView(Lesson lesson) {
    lesson.setViews(lesson.getViews() + 1);
  }

}
