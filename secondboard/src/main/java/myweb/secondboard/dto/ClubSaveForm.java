package myweb.secondboard.dto;

import lombok.Getter;
import lombok.Setter;
import myweb.secondboard.domain.Local;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
public class ClubSaveForm {

  @NotNull @Size(min=1, max=16, message="동호회 이름은 1 ~ 16 자 이내여야 합니다." )
  private String name;

  private String img;

  private String imgPath;

  @Size(min=1, max=256, message = "소개글은 256자 이내여야합니다")
  private String introduction;

  private String leader;

  private Local local;


}