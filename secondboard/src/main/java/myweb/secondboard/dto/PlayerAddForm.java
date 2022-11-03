package myweb.secondboard.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
public class PlayerAddForm {

  @NotNull
  private String memberId;

  @NotNull
  private String team;

  @NotNull
  private String matchingId;

}
