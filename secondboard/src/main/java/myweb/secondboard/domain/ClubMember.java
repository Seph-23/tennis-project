package myweb.secondboard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ClubMember {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "club_member_id")
  private Long Id;

  @NotNull
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "club_id")
  private Club club;

  @NotNull
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @NotNull
  @CreatedDate
  @Column(length = 40)
  private String createdDate;

  public static ClubMember createClubMember(Club club, Member member) {
    ClubMember clubMember = new ClubMember();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    clubMember.setClub(club);
    clubMember.setMember(member);
    clubMember.setCreatedDate(LocalDateTime.now().format(dtf));
    return clubMember;
  }
}