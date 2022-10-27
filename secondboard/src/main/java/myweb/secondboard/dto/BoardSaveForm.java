package myweb.secondboard.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import myweb.secondboard.domain.Member;

@Getter @Setter
public class BoardSaveForm {

  @NotNull
  @Size(min = 1, max = 30, message = "제목은 1 ~ 30 자 이내여야 합니다.")
  private String title;

  @NotNull
  @Size(min = 1, max = 1000000000, message = "게시글 용량이 너무 큽니다.")
  private String content;
}
