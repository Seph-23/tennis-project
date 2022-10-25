package myweb.secondboard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myweb.secondboard.dto.MatchSaveForm;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Matching {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "match_id")
  private Long id;

  @NotNull
  @Column(length = 31)
  private String matchTitle;

  @NotNull
  @CreatedDate
  @Column(length = 40)
  private String matchDate;

  @LastModifiedDate
  @Column(length = 40)
  private String modifiedDate;

  @NotNull
  @Column(length = 20)
  private String matchTime;

  @NotNull
  @Column(length = 10)
  private String matchType;

  @NotNull
  @Column(length = 10)
  private String courtType;

  @NotNull
  @Column(length = 30)
  private String matchPlace;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  public static Matching createMatch(MatchSaveForm form, Member member) {
    Matching matching = new Matching();

    matching.setMatchTitle(form.getMatchTitle());
    matching.setMatchDate(form.getMatchDate());
    matching.setMatchTime(form.getMatchTime());
    matching.setMatchType(form.getMatchType());
    matching.setCourtType(form.getCourtType());
    matching.setMatchPlace(form.getMatchPlace());
    matching.setMember(member);
    return matching;
  }

}
