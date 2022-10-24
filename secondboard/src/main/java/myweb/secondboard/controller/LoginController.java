package myweb.secondboard.controller;

import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.domain.Member;
import myweb.secondboard.dto.LoginForm;
import myweb.secondboard.service.LoginService;
import myweb.secondboard.web.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

  private final LoginService loginService;

  @GetMapping("/login")
  public String loginPage(Model model) {
    model.addAttribute("loginForm", new LoginForm());
    return "/login/loginPage";
  }

  @PostMapping("/login")
  public String login(@Valid @ModelAttribute("loginForm") LoginForm loginForm,
    BindingResult bindingResult, HttpServletRequest request) throws NoSuchAlgorithmException {
    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/login/loginPage";
    }
    Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
    if (loginMember == null) {
      bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
      return "login/loginPage";
    }
    //세션에 로그인 객체 저장.
    request.getSession().setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
    return "redirect:/";
  }

  @GetMapping("/logout")
  public String logout(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    return "redirect:/";
  }
}
