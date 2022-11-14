package myweb.secondboard.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myweb.secondboard.dto.MatchingSaveForm;
import myweb.secondboard.dto.MatchingUpdateForm;
import myweb.secondboard.dto.ResultAddForm;
import myweb.secondboard.web.*;
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
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter @Setter
@NoArgsConstructor
@DynamicInsert
public class

Matching {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "matching_id")
  private Long id;

  @NotNull
  @Column(length = 11)
  private String author;   //member nickname

  @NotNull
  @Column(length = 31)
  private String title;

  @NotNull
  @Column(length = 145)
  private String place;

  @NotNull
  @Column(length = 40)
  private LocalDate matchingDate;

  @NotNull
  @Column(length = 40)
  private String matchingStartTime;

  @NotNull
  @Column(length = 40)
  private String matchingEndTime;

  @CreatedDate
  @Column(length = 40)
  private String createdDate;

  @LastModifiedDate
  @Column(length = 40)
  private String modifiedDate;

  @Column(columnDefinition = "integer default 1")
  private Integer playerNumber;

  @Enumerated(EnumType.STRING)
  private CourtType courtType;

  @Enumerated(EnumType.STRING)
  private MatchingType matchingType;

  @Enumerated(EnumType.STRING)
  private MatchingCondition matchingCondition;

  @Enumerated(EnumType.STRING)
  private MatchingStatus matchingStatus;

  private String beforeHour;

  private String beforeTwoHour;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @Enumerated(EnumType.STRING)
  private GameResult gameResult;

  private String lat;
  private String lng;

  public static Matching createMatching(MatchingSaveForm form, Member member) {
    Matching matching = new Matching();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    matching.setTitle(form.getTitle());
    matching.setPlace(form.getPlace());
    matching.setAuthor(member.getNickname());
    matching.setMatchingDate(form.getMatchingDate());
    matching.setMatchingStartTime(form.getMatchingStartTime());
    matching.setMatchingEndTime(form.getMatchingEndTime());
    matching.setCreatedDate(LocalDateTime.now().format(dtf));
    matching.setCourtType(form.getCourtType());
    matching.setMatchingType(form.getMatchingType());
    matching.setMatchingCondition(MatchingCondition.AVAILABLE);
    matching.setMatchingStatus(MatchingStatus.BEFORE);
    matching.setBeforeHour(form.getBeforeHour());
    matching.setBeforeTwoHour(form.getBeforeTwoHour());
    matching.setPlayerNumber(matching.getPlayerNumber());
    matching.setMember(member);
    matching.setLat(form.getLat());
    matching.setLng(form.getLng());

    return matching;
  }

  public void updateMatching(Matching matching, MatchingUpdateForm form, Member member) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    matching.setId(form.getId());
    matching.setTitle(form.getTitle());
    matching.setPlace(form.getPlace());
    matching.setAuthor(member.getNickname());
    matching.setMatchingDate(form.getMatchingDate());
    matching.setMatchingStartTime(form.getMatchingStartTime());
    matching.setMatchingEndTime(form.getMatchingEndTime());
    matching.setCourtType(form.getCourtType());
    matching.setModifiedDate(LocalDateTime.now().format(dtf));
    matching.setMember(member);
    matching.setLat(form.getLat());
    matching.setLng(form.getLng());
  }

  public void increasePlayerNumber(Matching matching) {
    matching.setPlayerNumber(matching.getPlayerNumber() + 1);
  }

  public void matchingConditionCheck(Matching matching) {
    if (matching.getPlayerNumber() == 2 && matching.getMatchingType().getTitle() == "단식") {
      matching.setMatchingCondition(MatchingCondition.DONE);
    }
    if (matching.getPlayerNumber() == 4 && matching.getMatchingType().getTitle() == "복식") {
      matching.setMatchingCondition(MatchingCondition.DONE);
    }
  }

  public void updateMatchingResult(ResultAddForm resultAddForm, Matching matching) {
    matching.setId(resultAddForm.getId());
    matching.setGameResult(resultAddForm.getGameResult());
  }

  public void matchingOngoingCheck(Matching matching) {
    matching.setMatchingStatus(MatchingStatus.ONGOING);
  }

  public void matchingAfterCheck(Matching matching) {
    matching.setMatchingStatus(MatchingStatus.AFTER);
  }

  public void matchingBeforeHourCheck(Matching matching) {
    matching.setMatchingStatus(MatchingStatus.HOURBEFORE);
    matching.setMatchingCondition(MatchingCondition.FAIL);
  }

  public void matchingBeforeTwoHourCheck(Matching matching) { matching.setMatchingStatus(MatchingStatus.TWOHOURBEFORE);
  }

  public void matchingAfterWeek(Matching matching) { matching.setMatchingStatus(MatchingStatus.WEEKAFTER);
  }
}
