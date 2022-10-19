package myweb.secondboard.domain;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myweb.secondboard.dto.MemberSaveForm;
import myweb.secondboard.web.Gender;
import myweb.secondboard.web.PasswordEncrypt;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Member implements Serializable {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;

  @NotNull @Column(unique = true, length = 16)
  private String loginId;

  @NotNull @Column(unique = true, length = 64)
  private String password;

  @NotNull @Column(length = 11)
  private String nickname;

  @NotNull @Column(unique = true, length = 50)
  private String email;

  @NotNull @Column(length = 10)
  private String birthday;

  @NotNull @Column(unique = true, length = 11)
  private String phoneNumber;

  @Enumerated(EnumType.STRING)
  private Gender sex;

  public static Member createMember(MemberSaveForm form) throws NoSuchAlgorithmException {
    Member member = new Member();
    PasswordEncrypt passwordEncrypt = new PasswordEncrypt();

    member.setLoginId(form.getLoginId());
    member.setPassword(passwordEncrypt.encrypt(form.getPassword()));
    member.setNickname(form.getNickname());
    member.setEmail(form.getEmail());

    //생년월일
    String birth = form.getYear()+form.getMonth()+form.getDay();
    member.setBirthday(birth);

    member.setPhoneNumber(form.getPhoneNumber());

    if (form.getGender().equals("man")) {
      member.setSex(Gender.MALE);
    } else {
      member.setSex(Gender.FEMALE);
    }

    return member;
  }
}