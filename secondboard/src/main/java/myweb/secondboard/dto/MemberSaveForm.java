package myweb.secondboard.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

@Getter @Setter
public class MemberSaveForm {

  @NotNull @Size(min = 8, max = 15, message = "아이디는 8 ~ 15자 이내여야 합니다.")
  private String loginId;

  @NotNull @Size(min = 9, max = 24, message = "비밀번호는 9 ~ 24자 이내여야 합니다.")
  private String password;

  @NotNull(message = "닉네임을 입력 하세요.") @Size(min = 4, max = 10, message = "닉네임은 4 ~ 10자 이내여야 합니다.")
  private String nickname;

  @NotNull(message = "이메일을 입력 하세요.") @Email
  private String email;

  //생년월일
  private String year;
  private String month;
  private String day;

  @NotNull(message = "핸드폰 번호를 입력 하세요.")
  private String phoneNumber;

  @NotNull(message = "성별을 선택 하세요.")
  private String gender;
}
