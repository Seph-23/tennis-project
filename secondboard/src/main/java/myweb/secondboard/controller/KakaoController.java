package myweb.secondboard.controller;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Member;
import myweb.secondboard.dto.LoginForm;
import myweb.secondboard.service.KaKaoService;
import myweb.secondboard.service.MemberService;
import myweb.secondboard.web.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class KakaoController {

  private final KaKaoService kaKaoService;
  private final MemberService memberService;

  @GetMapping("/kakao")
  public String getCI(@RequestParam String code, Model model,
    HttpServletRequest request) throws IOException {

    System.out.println("code = " + code);
    String access_token = kaKaoService.getToken(code);
    Map<String, Object> userInfo = kaKaoService.getUserInfo(access_token);

    //==카카오 로그인 정보==//
    System.out.println("controller token value = " + userInfo.toString());
    System.out.println("access_token = " + access_token);

    if (memberService.findByEmail(userInfo.get("email").toString()).isEmpty()) {
      Member member = memberService.kakaoSignUp(userInfo, access_token);
      request.getSession().setAttribute(SessionConst.LOGIN_MEMBER, member);
    } else {
      Member member = memberService.findByEmail(userInfo.get("email").toString()).get();
      memberService.renewAccessToken(member, access_token);
      request.getSession().setAttribute(SessionConst.LOGIN_MEMBER, member);
    }

    return "redirect:/";
  }

  @GetMapping("/kakao/logout")
  public String logout(HttpServletRequest request, Model model) {
    model.addAttribute("loginForm", new LoginForm()); //카카오 로그인 용
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    return "redirect:/";
  }
}
