package myweb.secondboard.controller;

import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Member;
import myweb.secondboard.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members/api")
public class MemberApiController {

  private final MemberService memberService;

  //회원가입 아이디 중복확인
  @GetMapping("/checkId")
  public String checkId(@RequestParam Map<String, Object> param) {

    Optional<Member> member = memberService.findByLoginId(param.get("loginId").toString());
    if (member.isEmpty()) {
      return "success";       //가입가능
    }
    return "duplicate";            //중복회원
  }

  //회원가입 닉네임 중복확인
  @GetMapping("/checkNick")
  public String checkNick(@RequestParam Map<String, Object> param) {

    Optional<Member> member = memberService.findByNickname(param.get("nickname").toString());
    if (member.isEmpty()) {
      return "success";       //가입가능
    }
    return "duplicate";            //중복회원
  }

  //회원가입 이메일 중복확인
  @GetMapping("/checkEmail")
  public String checkEmail(@RequestParam Map<String, Object> param) {

    Optional<Member> member = memberService.findByEmail(param.get("email").toString());
    if (member.isEmpty()) {
      return "success";       //가입가능
    }
    return "duplicate";            //중복회원
  }
}
