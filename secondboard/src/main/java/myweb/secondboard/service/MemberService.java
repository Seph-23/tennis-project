package myweb.secondboard.service;

import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.Record;
import myweb.secondboard.dto.MemberSaveForm;
import myweb.secondboard.dto.MemberUpdateForm;
import myweb.secondboard.dto.UpdatePasswordForm;
import myweb.secondboard.repository.MemberRepository;
import myweb.secondboard.repository.RecordRepository;
import myweb.secondboard.web.PasswordEncrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final RecordRepository recordRepository;

  @Transactional
  public Long signUp(MemberSaveForm form) throws NoSuchAlgorithmException {
    Record record = Record.createRecord();
    recordRepository.save(record);
    Member member = Member.createMember(form, record);
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

  public List<Member> findAll(){
    return memberRepository.findAll();
  }

  @Transactional
  public Member kakaoSignUp(Map<String, Object> userInfo, String access_token) {
    Record record = Record.createRecord();
    Member member = Member.createKakaoMember(userInfo, access_token, record);
    recordRepository.save(record);
    memberRepository.save(member);
    return member;
  }

  @Transactional
  public void renewAccessToken(Member member, String access_token) {
    member.setAccessToken(access_token);
  }

  @Transactional
  public void updatePassword(UpdatePasswordForm form, Member member) throws NoSuchAlgorithmException {
    PasswordEncrypt passwordEncrypt = new PasswordEncrypt();
    member.setPassword(passwordEncrypt.encrypt(form.getUpdatePassword()));
    memberRepository.save(member);
  }

  public Optional<Member> findByPhoneNum(String phoneNum) {
    return memberRepository.findByPhoneNum(phoneNum);
  }

  @Transactional
  public Long updateMember(MemberUpdateForm form, MultipartFile file) throws IOException {

    // case1: 기존 file null -> 변경해서 추가
    // case2: 기존 사진 -> 변경
    // case3: 기존 사진 -> 변경없음

    Member member = findById(form.getId());

    // 닉네임, 자기소개 변경
    member.updateMember(form, member);

//    첫 테스트
//    member.setImgEn(file, member);


    // 첫 회원가입으로 이미지가 없을때
    if(member.getImgEn() == null){
      // 기본 이미지X, 새로 받은 파일 O
      if(!file.isEmpty()){
        System.out.println("기본 이미지X, 새로 받은 파일 O");
        member.setImgEn(file, member);
      } else {
        System.out.println("기존 이미지 X, 새로 받은 이미지 X");
        // 기존 이미지 X, 새로 받은 이미지 X
      }
    } else {
      // 기본 이미지 O, 새로 받은 파일 O
      if (!file.isEmpty()) {
        System.out.println("기본 이미지 O, 새로 받은 파일 O");
        member.setImgEn(file, member);
      } else {
        // 기본이미지 o, 새로 받은 파일 X
        System.out.println("기본이미지 o, 새로 받은 파일 X");
      }
    }

    return member.getId();
  }



  @Transactional
  public Long memberWithDrawl(Long memberId, String uuid) {

    Member member = findById(memberId);

    member.memberWithdrawl(member, uuid);

    return member.getId();
  }
}


