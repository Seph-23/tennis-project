package myweb.secondboard.domain;

import static javax.persistence.FetchType.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import myweb.secondboard.dto.BoardSaveForm;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter @Setter
public class Board {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "board_id")
  private Long id;

  @NotNull @Column(length = 31)
  private String title;

  @NotNull @Column(length = 145)
  private String content;

  @NotNull @Column(length = 11)
  private String author;   //member nickname

  @Column(columnDefinition = "integer default 0")
  private Integer views;

  @CreatedDate @Column(length = 40)
  private String createdDate;
  @LastModifiedDate @Column(length = 40)
  private String modifiedDate;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  //생성 메서드
  public static Board createBoard(BoardSaveForm form, Member member) {
    Board board = new Board();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy:MM:dd:HH:mm:ss");

    board.setTitle(form.getTitle());
    board.setContent(form.getContent());
    board.setAuthor(member.getNickname());
    board.setViews(0);
    board.setCreatedDate(LocalDateTime.now().format(dtf));
    board.setModifiedDate(LocalDateTime.now().format(dtf));
    board.setMember(member);
    return board;
  }
}
