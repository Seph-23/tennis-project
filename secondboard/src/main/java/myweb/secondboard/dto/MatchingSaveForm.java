package myweb.secondboard.dto;

import lombok.Getter;
import lombok.Setter;
import myweb.secondboard.web.CourtType;
import myweb.secondboard.web.MatchingType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter
public class MatchingSaveForm {

  @NotNull @Size(min = 1, max = 15, message = "제목은 1 ~ 15자 이내여야 합니다.")
  private String title;

  @NotNull @Size(min = 1, max = 40, message = "장소은 1 ~ 40자 이내여야 합니다.")
  private String place;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @NotNull
  private LocalDate matchingDate;

  @DateTimeFormat(pattern = "HH:mm")
  @NotNull
  private LocalTime matchingTime;

  @NotNull
  private MatchingType matchingType;

  @NotNull
  private CourtType courtType;

  private String team;

}
