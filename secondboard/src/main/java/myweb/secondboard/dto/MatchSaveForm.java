package myweb.secondboard.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
public class MatchSaveForm {

  @NotNull @Size(min = 1, max = 30, message = "제목은 1 ~ 30 자 이내여야 합니다.")
  private String matchTitle;

  @NotNull
  private String matchDate;

  @NotNull
  private String matchTime;

  @NotNull
  private String matchType;

  @NotNull
  private String courtType;

  @NotNull @Size(min = 1, max = 30, message = "장소는 1 ~ 30 자 이내여야 합니다.")
  private String matchPlace;
}
