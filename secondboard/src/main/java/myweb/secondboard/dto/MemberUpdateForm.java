package myweb.secondboard.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
public class MemberUpdateForm {

  @NotNull(message = "닉네임을 입력 하세요.")
  @Size(min = 4, max = 10, message = "닉네임은 4 ~ 10자 이내여야 합니다.")
  private String nickname;

  private Long id;

  private String introduction;


}

