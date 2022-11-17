package myweb.secondboard.domain.boards;

import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.LAZY;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.comments.QuestionComment;
import myweb.secondboard.dto.QuestionSaveForm;
import myweb.secondboard.dto.QuestionUpdateForm;
import myweb.secondboard.web.AnswerCondition;

@Entity
@Getter @Setter
public class Question extends BoardAbstract {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "question_id")
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @OneToMany(mappedBy = "question", cascade = REMOVE)
  private List<QuestionComment> comments = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  @Column(name = "question_condition")
  private AnswerCondition condition;

  public static Question createQuestion(QuestionSaveForm form, Member member) {
    Question question = new Question();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    question.setTitle(form.getTitle());
    question.setContent(form.getContent());
    question.setAuthor(member.getNickname());
    question.setViews(0);
    question.setCreatedDate(LocalDateTime.now().format(dtf));
    question.setModifiedDate(LocalDateTime.now().format(dtf));
    question.setMember(member);
    question.setCondition(AnswerCondition.ACCEPT);
    return question;
  }

  public void updateQuestion(Question question, QuestionUpdateForm form, Member member) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    question.setId(form.getId());
    question.setTitle(form.getTitle());
    question.setContent(form.getContent());
    question.setAuthor(member.getNickname());
    question.setModifiedDate(LocalDateTime.now().format(dtf));
    question.setMember(member);
  }

  public void updateView(Question question) {
    question.setViews(question.getViews() + 1);
  }

  public static void updateCondition(Question question) {
    question.setCondition(AnswerCondition.COMPLETE);
  }



}
