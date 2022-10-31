package myweb.secondboard.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdatePasswordForm {


  private Long id;

  @NotNull @Size(min = 9, max = 24, message = "비밀번호는 9 ~ 24자 이내여야 합니다.")
  private String updatePassword;

  @NotNull @Size(min = 9, max = 24, message = "비밀번호는 9 ~ 24자 이내여야 합니다.")
  private String updatePasswordCheck;


}
