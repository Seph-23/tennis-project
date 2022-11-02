package myweb.secondboard.dto;

import lombok.Getter;
import lombok.Setter;
import myweb.secondboard.web.Team;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
public class PlayerAddForm {

  String memberId;
  @NotNull @Size(message = "최소 한 팀을 선택해주세요")
  String team;
  String matchId;

}
