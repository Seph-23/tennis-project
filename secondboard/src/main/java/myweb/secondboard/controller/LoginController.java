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
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
      log.info("globalError = {}", bindingResult);
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

  @PostMapping("/login/modal")
  @ResponseBody
  public JSONObject loginModal(@RequestParam("loginId") String loginId,
    @RequestParam String password, HttpServletRequest request) throws NoSuchAlgorithmException {
    JSONObject result = new JSONObject();
    System.out.println("loginId = " + loginId);
    System.out.println("password = " + password);
    Member loginMember = loginService.login(loginId, password);
    if (loginMember == null) {
      result.put("result", "fail");
      return result;
    }
    //세션에 로그인 객체 저장.
    request.getSession().setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
    result.put("result", "success");
    return result;
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
