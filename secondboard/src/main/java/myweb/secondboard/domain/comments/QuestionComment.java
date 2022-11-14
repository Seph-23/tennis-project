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
import myweb.secondboard.domain.boards.Question;

@Entity
@Getter
@Setter
public class QuestionComment extends CommentAbstract{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "notice_comment_id")
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "question_id")
  private Question question;

  public static QuestionComment createComment(Map<String, Object> param, Question question,
      Member member) {
    QuestionComment questionComment = new QuestionComment();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    questionComment.setAuthor(member.getNickname());
    questionComment.setContent(param.get("content").toString());
    questionComment.setQuestion(question);
    questionComment.setMember(member);
    questionComment.setCreatedDate(LocalDateTime.now().format(dtf));
    questionComment.setModifiedDate(LocalDateTime.now().format(dtf));
    return questionComment;
  }

  public void updateComment(Long commentId, Map<String, Object> param,
      QuestionComment questionComment) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
    questionComment.setContent(param.get("content").toString());
    questionComment.setModifiedDate(LocalDateTime.now().format(dtf));
  }


}
