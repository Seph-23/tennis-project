package myweb.secondboard.domain;

import static javax.persistence.CascadeType.*;
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
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myweb.secondboard.domain.boards.BoardReport;
import myweb.secondboard.dto.BoardSaveForm;
import myweb.secondboard.dto.BoardUpdateForm;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Board {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "board_id")
  private Long id;

  @NotNull
  @Column(length = 31)
  private String title;

  @NotNull
  @Column(length = 2147483647)
  private String content;

  @NotNull
  @Column(length = 11)
  private String author;   //member nickname

  @Column(columnDefinition = "integer default 0")
  private Integer views;

  @CreatedDate
  @Column(length = 40)
  private String createdDate;
  @LastModifiedDate
  @Column(length = 40)
  private String modifiedDate;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @Column(columnDefinition = "integer default 0")
  private Long likeCount;

  @OneToMany(mappedBy = "board", cascade = REMOVE)
  private List<Comment> comments = new ArrayList<>();

  @OneToMany(mappedBy = "board", cascade = REMOVE)
  private List<BoardReport> boardReports = new ArrayList<>();

  //생성 메서드
  public static Board createBoard(BoardSaveForm form, Member member) {
    Board board = new Board();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    board.setTitle(form.getTitle());
    board.setContent(form.getContent());
    board.setAuthor(member.getNickname());
    board.setViews(0);
    board.setLikeCount(0L);
    board.setCreatedDate(LocalDateTime.now().format(dtf));
    board.setModifiedDate(LocalDateTime.now().format(dtf));
    board.setMember(member);
    return board;
  }

  public void updateBoard(Board board, BoardUpdateForm form, Member member) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    board.setId(form.getId());
    board.setTitle(form.getTitle());
    board.setContent(form.getContent());
    board.setAuthor(member.getNickname());
    board.setModifiedDate(LocalDateTime.now().format(dtf));
    board.setMember(member);
  }

  //조회수 증가
  public void updateView(Board board) {
    board.setViews(board.getViews() + 1);
  }
}
