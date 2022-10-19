package myweb.secondboard.controller;

import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Member;
import myweb.secondboard.service.MemberService;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberApiController {

  private final MemberService memberService;

  //회원가입 아이디 중복확인
  @GetMapping("/checkId")
  public JSONObject checkId(@RequestParam Map<String, Object> param) {

    JSONObject result = new JSONObject();
    int inputLength = param.get("loginId").toString().length();
    if (inputLength < 8 || inputLength > 15) {
      result.put("result", "validate");
      return result;
    }

    Optional<Member> member = memberService.findByLoginId(param.get("loginId").toString());

    if (member.isEmpty()) {
      result.put("result", "ok");
      return result;       //가입가능
    }

    result.put("result", "duplicate");
    return result;            //중복회원
  }

  //회원가입 닉네임 중복확인
  @GetMapping("/checkNick")
  public JSONObject checkNick(@RequestParam Map<String, Object> param) {

    JSONObject result = new JSONObject();
    int inputLength = param.get("nickname").toString().length();

    if (inputLength > 10 || inputLength < 4) {
      result.put("result", "validate");
      return result;
    }

    Optional<Member> member = memberService.findByNickname(param.get("nickname").toString());

    if (member.isEmpty()) {
      result.put("result", "ok");
      return result;       //가입가능
    }
    result.put("result", "duplicate");
    return result;            //중복회원
  }

  //회원가입 이메일 중복확인
  @GetMapping("/checkEmail")
  public JSONObject checkEmail(@RequestParam Map<String, Object> param) {

    JSONObject result = new JSONObject();
    Optional<Member> member = memberService.findByEmail(param.get("email").toString());

    if (member.isEmpty()) {
      result.put("result", "ok");
      return result;       //가입가능
    }
    result.put("result", "duplicate");
    return result;            //중복회원
  }
}