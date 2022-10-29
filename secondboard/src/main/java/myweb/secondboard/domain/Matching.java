package myweb.secondboard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myweb.secondboard.dto.BoardUpdateForm;
import myweb.secondboard.dto.MatchSaveForm;
import myweb.secondboard.dto.MatchUpdateForm;
import myweb.secondboard.web.CourtType;
import myweb.secondboard.web.MatchCondition;
import myweb.secondboard.web.MatchType;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor
@DynamicInsert
public class Matching {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "match_id")
  private Long id;

  @NotNull
  @Column(length = 31)
  private String matchTitle;

  @CreatedDate
  @Column(length = 40)
  private LocalDate matchDate;

  @LastModifiedDate
  @Column(length = 40)
  private LocalDate modifiedDate;

  @NotNull
  @Column(length = 20)
  private LocalTime matchTime;

  @Enumerated(EnumType.STRING)
  private MatchType matchType;

  @Enumerated(EnumType.STRING)
  private CourtType courtType;

  @NotNull
  @Column(length = 30)
  private String matchPlace;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @Column(columnDefinition = "integer default 1")
  private Integer playerNumber;

  @Enumerated(EnumType.STRING)
  private MatchCondition matchCondition;

  public static Matching createMatch(MatchSaveForm form, Member member) {
    Matching matching = new Matching();

    matching.setMatchTitle(form.getMatchTitle());
    matching.setMatchDate(form.getMatchDate());
    matching.setMatchTime(form.getMatchTime());
    matching.setMatchType(form.getMatchType());
    matching.setCourtType(form.getCourtType());
    matching.setMatchPlace(form.getMatchPlace());
    matching.setMember(member);
    matching.setMatchCondition(MatchCondition.AVAILABLE);
    matching.setPlayerNumber(form.getPlayerNumber());
    return matching;
  }

  public void updateMatch(Matching matching, MatchUpdateForm form, Member member) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    matching.setId(form.getId());
    matching.setMatchTitle(form.getMatchTitle());
    matching.setMatchDate(form.getMatchDate());
    matching.setMatchTime(form.getMatchTime());
    matching.setMatchType(form.getMatchType());
    matching.setCourtType(form.getCourtType());
    matching.setMatchPlace(form.getMatchPlace());
    matching.setMember(member);
  }

}
