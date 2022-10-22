package myweb.secondboard.controller;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Member;
import myweb.secondboard.service.KaKaoService;
import myweb.secondboard.service.MemberService;
import myweb.secondboard.web.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    System.out.println("controller token value = " + userInfo.toString());

    if (memberService.findByEmail(userInfo.get("email").toString()).isEmpty()) {
      Member member = memberService.kakaoSignUp(userInfo, access_token);
      request.getSession().setAttribute(SessionConst.LOGIN_MEMBER, member);
    } else {
      Member member = memberService.findByEmail(userInfo.get("email").toString()).get();
      request.getSession().setAttribute(SessionConst.LOGIN_MEMBER, member);
    }

//    model.addAttribute("code", code);
//    model.addAttribute("access_token", access_token);
//    model.addAttribute("userInfo", userInfo);

    //ci는 비즈니스 전환후 검수신청 -> 허락받아야 수집 가능
    return "redirect:/";
  }
}
