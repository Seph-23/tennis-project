package myweb.secondboard.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginForm {

  @NotNull @Size(min = 8, max = 15, message = "아이디는 8 ~ 15자 이내여야 합니다.")
  private String loginId;

  @NotNull @Size(min = 9, max = 24, message = "비밀번호는 9 ~ 24자 이내여야 합니다.")
  private String password;

}
