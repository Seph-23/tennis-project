package myweb.secondboard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myweb.secondboard.dto.MemberSaveForm;
import myweb.secondboard.dto.MemberUpdateForm;
import myweb.secondboard.web.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
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

  @Column(length = 100)
  private String introduction;
  
  @Enumerated(EnumType.STRING)
  private Role role;

  // base64 인코딩하여 파일 저장
  @Lob
  private byte[] imgEn;

  @NotNull
  @Column(length = 12)
  @Enumerated(EnumType.STRING)
  private Tier tier;

  @OneToOne
  @JoinColumn(name = "record_id")
  private Record record;


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
    member.setTier(Tier.IRON);
    return member;
  }

  public void updateMember(MemberUpdateForm form, Member member ){
    member.setId(form.getId());
    member.setNickname(form.getNickname());
    member.setIntroduction(form.getIntroduction());

  }

  public static Member createKakaoMember(Map<String, Object> userInfo, String access_token, Record record) {
    Member member = new Member();
    member.setProvider(Provider.KAKAO);
    member.setNickname(userInfo.get("nickname").toString());
    member.setEmail(userInfo.get("email").toString());
    member.setLoginId(userInfo.get("email").toString());
//    member.setBirthday(userInfo.get("birthday").toString());

    //birthday example = 0529
    String birthday = userInfo.get("birthday").toString();
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < birthday.length(); i++) {

        if (i != 2) {
          builder.append(birthday.charAt(i));
        }
        if (i == 2) {
          builder.append("월 ");
          builder.append(birthday.charAt(i));
        }
        if (i == 3) {
          builder.append("일");
        }
    }
    if (builder.charAt(0) == '0') {
      builder.deleteCharAt(0);
    }
    member.setBirthday(builder.toString());
    member.setRole(Role.MEMBER);
    member.setTier(Tier.IRON);
    member.setRecord(record);


    if (userInfo.get("has_gender").toString().equals("true")) {
       member.setGender(Gender.valueOf(userInfo.get("gender").toString().toUpperCase()));
    } // male, female => MALE, FEMALE

    member.setAccessToken(access_token);

    return member;
  }

  public void memberWithdrawl(Member member, String uuid) {
    member.setId(member.getId());
    member.setLoginId("탈퇴된 회원" + member.getId());
    member.setPassword(uuid);
    member.setNickname("탈퇴된 회원" + member.getId());
    member.setEmail("탈퇴된 회원" + member.getId());
    member.setPhoneNumber("탈퇴된 회원" + member.getId());
  }

  public void setImgEn(MultipartFile file, Member member) throws IOException {
    byte[] ImgEn = new byte[0];

    if (file != null) {
      Base64.Encoder encoder = Base64.getEncoder();
      ImgEn = encoder.encode(file.getBytes());
    }
    member.setImgEn(ImgEn);

  }
}