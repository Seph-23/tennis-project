package myweb.secondboard.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentSaveForm {

//  @NotNull @Size(min = 1, max = 100, message = "댓글은 1 ~100 자 이내여야 합니다.")
  private String content;

}
