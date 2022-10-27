package myweb.secondboard.service;

import java.security.NoSuchAlgorithmException;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Member;
import myweb.secondboard.repository.MemberRepository;
import myweb.secondboard.repository.MemberRepositoryImpl;
import myweb.secondboard.web.PasswordEncrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

  private final MemberRepository memberRepository;

  public Member login(String loginId, String password) throws NoSuchAlgorithmException {
    PasswordEncrypt passwordEncrypt = new PasswordEncrypt();
    String encryptedPassword = passwordEncrypt.encrypt(password);
    System.out.println(encryptedPassword + "암호화된 암호");
//    return memberRepository.findByLoginId(loginId).filter(m -> m.getPassword()
//      .equals(encryptedPassword)).orElse(null);
    System.out.println("왜 터질까요?"); // 찍힘

    return memberRepository.findByLoginId(loginId).filter(m -> m.getPassword()
        .equals(password)).orElse(null);
  }
}
