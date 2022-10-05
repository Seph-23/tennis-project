package myweb.secondboard.service;

import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Member;
import myweb.secondboard.repository.MemberRepositoryImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

  private final MemberRepositoryImpl memberRepository;

  public Member login(String loginId, String password) {
    return memberRepository.findByLoginId(loginId).filter(m -> m.getPassword().equals(password))
      .orElse(null);
  }

}
