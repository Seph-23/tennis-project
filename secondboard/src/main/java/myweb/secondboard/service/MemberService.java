package myweb.secondboard.service;

import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.File;
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
  private final FileService fileService;
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
    System.out.println("이미지 상관없이 닉네임과 자기소개 수정합니다.");
    member.updateMember(form, member);

    File originFile = member.getFile();

    // 처음 프로필 이미지 수정 할 때
    if(member.getFile()==null){
      System.out.println("기본이미지가 없어서 file추가합니다.");
      originFile = File.createImg(fileService.ImgSave(file));
      System.out.println("이미지 추가했어요 이미지번호는 ==>"+originFile.getId());
      member.setFile(originFile);
    } else {
      if(file.isEmpty()!=false){
        System.out.println("파일 수정 없다.!");
        return member.getId();
      } else {
        System.out.println("파일 수정 있다.!");
        byte[] files = fileService.ImgSave(file);
        originFile.setSaveImg(files);
        member.setFile(originFile);
      }
    }

    return member.getId();
  }

}


