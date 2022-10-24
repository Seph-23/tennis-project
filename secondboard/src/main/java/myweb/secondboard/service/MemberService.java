package myweb.secondboard.service;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Member;
import myweb.secondboard.dto.MemberSaveForm;
import myweb.secondboard.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  @Transactional
  public Long signUp(MemberSaveForm form) throws NoSuchAlgorithmException {
    Member member = Member.createMember(form);
    memberRepository.save(member);
    return member.getId();
  }

  public Optional<Member> findByLoginId(String loginId) {
    return memberRepository.findByLoginId(loginId);
  }

  public Optional<Member> findByNickname(String nickname) {
    return memberRepository.findByNickname(nickname);
  }

  public Optional<Member> findByEmail(String email) {
    return memberRepository.findByEmail(email);
  }

  public Member findById(Long memberId) {
    return memberRepository.findById(memberId).get();
  }

  @Transactional
  public Member kakaoSignUp(Map<String, Object> userInfo, String access_token) {
    Member member = Member.createKakaoMember(userInfo, access_token);
    memberRepository.save(member);
    return member;
  }

  @Transactional
  public void renewAccessToken(Member member, String access_token) {
    member.setAccessToken(access_token);
  }
}
