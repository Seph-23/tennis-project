package myweb.secondboard.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myweb.secondboard.domain.boards.Notice;
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

    @NotNull
    private Integer reportCount;

    //연관관계 편의 메서드
    public void setBoard(Board board) {
        this.board = board;
        board.getComments().add(this);
    }

    //생성 메서드
    public static Comment createComment(Map<String, Object> param, Board board, Member member) {
        Comment comment = new Comment();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

        comment.setAuthor(member.getNickname());
        comment.setContent(param.get("content").toString());
        comment.setBoard(board);
        comment.setMember(member);
        comment.setCreatedDate(LocalDateTime.now().format(dtf));
        comment.setModifiedDate(LocalDateTime.now().format(dtf));
        comment.setReportCount(0);
        return comment;
    }

    public void updateComment(Long commentId, Map<String, Object> param, Comment comment) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        comment.setContent(param.get("content").toString());
        comment.setModifiedDate(LocalDateTime.now().format(dtf));
    }
}
