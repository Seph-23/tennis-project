package myweb.secondboard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="visitor_id")
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
    @JoinColumn(name = "club_id")
    private Club club;


    //방명록 생성 메서드
    public static Visitor createVisitor(Map<String, Object> param, Club club, Member member){
        Visitor visitor = new Visitor();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

        visitor.setAuthor(member.getNickname());
        visitor.setContent(param.get("content").toString());
        visitor.setClub(club);
        visitor.setMember(member);
        visitor.setCreatedDate(LocalDateTime.now().format(dtf));
        visitor.setModifiedDate(LocalDateTime.now().format(dtf));

        return visitor;
    }


    public void updateVisitor(Long visitorId, Map<String, Object> param, Visitor visitor) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        visitor.setContent(param.get("content").toString());
        visitor.setModifiedDate(LocalDateTime.now().format(dtf));
    }
}
