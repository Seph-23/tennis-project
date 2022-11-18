package myweb.secondboard.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class MemberWithdrawlForm {

  private Long id;

  @NotNull
  private String loginId;

  @NotNull
  private String password;

  @NotNull
  private String nickname;

  @NotEmpty
  private String email;

  @NotNull
  private String phoneNumber;

}
