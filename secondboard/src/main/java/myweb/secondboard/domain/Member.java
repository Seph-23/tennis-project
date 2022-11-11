package myweb.secondboard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myweb.secondboard.dto.MemberSaveForm;
import myweb.secondboard.dto.MemberUpdateForm;
import myweb.secondboard.web.Gender;
import myweb.secondboard.web.PasswordEncrypt;
import myweb.secondboard.web.Provider;
import myweb.secondboard.web.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

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

  private String introduction;
  
  @Enumerated(EnumType.STRING)
  private Role role;


  @OneToOne
  @JoinColumn(name = "record_id")
  private Record record;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="file_id")
  private File file;

  public static Member createMember(MemberSaveForm form, Record record) throws NoSuchAlgorithmException {
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
    member.setRecord(record);
    member.setRole(Role.MEMBER);
    return member;
  }

  public void updateMember(MemberUpdateForm form, Member member ){
    member.setId(form.getId());
    member.setNickname(form.getNickname());
    member.setIntroduction(form.getIntroduction());

  }

  public static Member createKakaoMember(Map<String, Object> userInfo, String access_token) {
    Member member = new Member();
    member.setProvider(Provider.KAKAO);
    member.setNickname(userInfo.get("nickname").toString());
    member.setEmail(userInfo.get("email").toString());
    member.setLoginId(userInfo.get("email").toString());
    member.setRole(Role.MEMBER);

    //==임시로 휴대전화 번호 넣어줌==//
    member.setPhoneNumber("01012341234");

    if (userInfo.get("has_gender").toString().equals("true")) {
       member.setGender(Gender.valueOf(userInfo.get("gender").toString().toUpperCase()));
    } // male, female => MALE, FEMALE

    member.setAccessToken(access_token);

    return member;
  }
}