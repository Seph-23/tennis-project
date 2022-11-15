package myweb.secondboard.dto;

import lombok.Getter;
import lombok.Setter;
import myweb.secondboard.web.CourtType;
import myweb.secondboard.web.MatchingType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter
public class MatchingSaveForm {

  @NotNull @Size(min = 1, max = 15, message = "제목은 1 ~ 15자 이내여야 합니다.")
  private String title;

  @NotNull @Size(min = 1, max = 200, message = "매치 소개는 1 ~ 200자 이내여야 합니다.")
  private String content;

  @NotNull @Size(min = 1, max = 40, message = "장소은 1 ~ 40자 이내여야 합니다.")
  private String place;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @NotNull(message = "매칭 날짜를 선택해주세요.")
  private LocalDate matchingDate;

  @NotNull(message = "매칭 시작 시간을 선택해주세요.")
  private String matchingStartTime;

  @NotNull(message = "매칭 종료 시간을 선택해주세요.")
  private String matchingEndTime;

  @NotNull(message = "매칭 종류를 선택해주세요.")
  private MatchingType matchingType;

  @NotNull(message = "코트 종류를 선택해주세요.")
  private CourtType courtType;

  private String beforeHour;

  private String beforeTwoHour;

  private String team;

  private String lat;
  private String lng;

}
