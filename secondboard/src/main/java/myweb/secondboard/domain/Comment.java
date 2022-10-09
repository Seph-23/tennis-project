package myweb.secondboard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @NotNull
    @Column(length = 100)
    private String content;

    @NotNull
    @Column(length = 20)
    private String author;

    @CreatedDate
    @Column(length = 40)
    private String createdDate;

    @LastModifiedDate
    @Column(length = 40)
    private String modifiedDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    //연관관계 편의 메서드
    public void setBoard(Board board) {
        this.board = board;
        board.getComments().add(this);
    }
}
