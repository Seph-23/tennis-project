package myweb.secondboard.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardSaveForm {


  @NotNull @Size(min = 1, max = 30, message = "제목은 1 ~ 30 자 이내여야 합니다.")
  private String title;

  @NotNull @Size(min = 1, max = 144, message = "내용은 1 ~ 144 자 이내여야 합니다.")
  private String content;
}
