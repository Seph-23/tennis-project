package myweb.secondboard.dto;

import lombok.Getter;
import lombok.Setter;
import myweb.secondboard.domain.Local;
import myweb.secondboard.web.CourtType;
import myweb.secondboard.web.MatchType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter
public class MatchSaveForm {

  @NotNull @Size(min = 1, max = 30, message = "제목은 1 ~ 30 자 이내여야 합니다.")
  private String matchTitle;

  @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate matchDate;

  @NotNull @DateTimeFormat(pattern = "HH:mm")
  private LocalTime matchTime;

  @NotNull
  private MatchType matchType;

  @NotNull
  private CourtType courtType;

  @NotNull @Size(min = 1, max = 30, message = "장소는 1 ~ 30 자 이내여야 합니다.")
  private String matchPlace;

  private String team;
}
