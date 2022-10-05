package myweb.secondboard.controller;

import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.domain.Member;
import myweb.secondboard.dto.MemberSaveForm;
import myweb.secondboard.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/new")
  public String signUpPage(Model model) {
    model.addAttribute("member", new MemberSaveForm());
    return "/members/signUpPage";
  }

  @PostMapping("/new")
  public String signUp(@Validated @ModelAttribute("member") MemberSaveForm form,
    BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/members/signUpPage";
    }

    //SignUp Success Logic
    memberService.signUp(form);

    return "redirect:/";
  }

  //회원가입 아이디 중복확인
  @ResponseBody @GetMapping("/checkId")
  public int checkId(@RequestParam Map<String, Object> param) {

    int result = 0;
    Optional<Member> member = memberService.findByLoginId(param.get("loginId").toString());
    if (member.isEmpty()) {
      return result;       //가입가능
    }
    return (result = 1);            //중복회원
  }

  //회원가입 닉네임 중복확인
  @ResponseBody @GetMapping("/checkNick")
  public int checkNick(@RequestParam Map<String, Object> param) {

    int result = 0;
    Optional<Member> member = memberService.findByNickname(param.get("nickname").toString());
    if (member.isEmpty()) {
      return result;       //가입가능
    }
    return (result = 1);            //중복회원
  }

  //회원가입 이메일 중복확인
  @ResponseBody @GetMapping("/checkEmail")
  public int checkEmail(@RequestParam Map<String, Object> param) {

    int result = 0;
    Optional<Member> member = memberService.findByEmail(param.get("email").toString());
    if (member.isEmpty()) {
      return result;       //가입가능
    }
    return (result = 1);            //중복회원
  }
}
