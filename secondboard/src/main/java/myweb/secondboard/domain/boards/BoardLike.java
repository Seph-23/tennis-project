package myweb.secondboard.domain.boards;

import static javax.persistence.FetchType.LAZY;

import java.time.LocalDate;
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
public class BoardLike {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "like_id")
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "board_id")
  private Board board;

  @NotNull
  @CreatedDate
  @Column(length = 40)
  private String createdDate;

  public static BoardLike createLike(Board board, Member member) {
    BoardLike boardLike = new BoardLike();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");

    boardLike.setBoard(board);
    boardLike.setMember(member);
    boardLike.setCreatedDate(LocalDate.now().format(dtf));
    
    return boardLike;
  }
}
