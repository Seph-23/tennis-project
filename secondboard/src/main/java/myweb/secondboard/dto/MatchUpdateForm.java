package myweb.secondboard.dto;

import lombok.Getter;
import lombok.Setter;
import myweb.secondboard.web.CourtType;
import myweb.secondboard.web.MatchType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class MatchUpdateForm {

  private Long id;

  @NotNull
  @Size(min = 1, max = 30, message = "1에서 30자 이내로 입력해주세요.")
  private String matchTitle;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @NotNull(message = "날짜를 선택해주세요.")
  private LocalDate matchDate;

  @DateTimeFormat(pattern = "HH:mm")
  @NotNull(message = "시작시간을 선택해주세요.")
  private LocalTime startTime;

  @DateTimeFormat(pattern = "HH:mm")
  @NotNull(message = "종료시간을 선택해주세요.")
  private LocalTime EndTime;

  @NotNull(message = "매치종류를 선택해주세요.")
  private MatchType matchType;

  @NotNull(message = "코트종류를 선택해주세요.")
  private CourtType courtType;

  @NotNull @Size(min = 1, max = 144, message = "장소를 입력해주세요.")
  private String matchPlace;


  @Override
  public String toString() {
    return "MatchUpdateForm{" +
      "id=" + id +
      ", matchTitle='" + matchTitle + '\'' +
      ", matchDate=" + matchDate +
      ", startTime=" + startTime +
      ", EndTime=" + EndTime +
      ", matchType=" + matchType +
      ", courtType=" + courtType +
      ", matchPlace='" + matchPlace + '\'' +
      '}';
  }
}


