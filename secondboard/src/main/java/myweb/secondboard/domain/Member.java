package myweb.secondboard.domain;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myweb.secondboard.dto.MemberSaveForm;
import myweb.secondboard.web.Gender;
import myweb.secondboard.web.PasswordEncrypt;
import myweb.secondboard.web.Provider;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Member implements Serializable {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;

  @Column(unique = true, length = 50)
  private String loginId;

  @Column(unique = true, length = 64)
  private String password;

  @Column(unique = true, length = 11)
  private String nickname;

  @Column(unique = true, length = 50)
  private String email;

  @Column(length = 10)
  private String birthday;

  @Column(unique = true, length = 11)
  private String phoneNumber;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Enumerated(EnumType.STRING)
  private Provider provider;

  private String accessToken;

  public static Member createMember(MemberSaveForm form) throws NoSuchAlgorithmException {
    Member member = new Member();
    PasswordEncrypt passwordEncrypt = new PasswordEncrypt();
    member.setLoginId(form.getLoginId());
    member.setPassword(passwordEncrypt.encrypt(form.getPassword()));
    member.setNickname(form.getNickname());
    member.setEmail(form.getEmail());
    String birth = form.getYear()+form.getMonth()+form.getDay();
    member.setBirthday(birth);
    member.setPhoneNumber(form.getPhoneNumber());
    member.setGender(Gender.valueOf(form.getGender()));
    member.setProvider(Provider.GOGOTENNIS);
    return member;
  }

  public static Member createKakaoMember(Map<String, Object> userInfo, String access_token) {
    Member member = new Member();
    member.setProvider(Provider.KAKAO);
    member.setNickname(userInfo.get("nickname").toString());
    member.setEmail(userInfo.get("email").toString());
    member.setLoginId(userInfo.get("email").toString());

    if (userInfo.get("has_gender").toString().equals("true")) {
       member.setGender(Gender.valueOf(userInfo.get("gender").toString().toUpperCase()));
    } // male, female => MALE, FEMALE

    member.setAccessToken(access_token);

    return member;
  }
}