package myweb.secondboard.dto;

import lombok.Getter;
import lombok.Setter;
import myweb.secondboard.web.Team;

@Getter @Setter
public class PlayerAddForm {

  String memberId;
  String team;
  String matchId;

}
