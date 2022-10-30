package myweb.secondboard.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.domain.Member;
import myweb.secondboard.dto.FindPasswordForm;
import myweb.secondboard.dto.MemberSaveForm;
import myweb.secondboard.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    BindingResult bindingResult) throws NoSuchAlgorithmException {
    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/members/signUpPage";
    }
    //SignUp Success Logic
    memberService.signUp(form);
    return "redirect:/";
  }

  // TODO
  @GetMapping("/profile/{memberId}")
  public String profileHome(@PathVariable("memberId") Long memberId, Model model) {
    Member member = memberService.findById(memberId);
    model.addAttribute("member", member);
    return "/members/profileHome";
  }

  //==비밀번호 찾기==//
  @GetMapping("/find/password")
  public String findPassword(Model model) {
    model.addAttribute("form", new FindPasswordForm());
    return "/members/findPassword";
  }

  //==비밀번호 인증 : 휴대폰 인증 사용==//
  @PostMapping("/find")
  public String findPassword(Model model, @ModelAttribute("form") FindPasswordForm form, BindingResult bindingResult) {
    System.out.println("form.getLoginId() = " + form.getLoginId());

    Optional<Member> findByLoginId = memberService.findByLoginId(form.getLoginId());
    if (findByLoginId.isEmpty()) {
      bindingResult.reject("NotFoundLoginMember", "입력하신 아이디를 찾을 수 없습니다.");
      return  "/members/findPassword";
    }
    String phoneNumber = findByLoginId.get().getPhoneNumber();
    //01012341234 -> *1*1*
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < phoneNumber.length(); i++) {
      if (i % 3 == 0) {
        builder.append(phoneNumber.charAt(i));
      }
      else {
        builder.append('*');
      }
    }
    model.addAttribute("phoneNum",builder.toString());

    return "/members/memberAuthentication";
  }


}
