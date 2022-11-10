package myweb.secondboard.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
  ScriptEngineManager scriptEngineMgr = new ScriptEngineManager();
  ScriptEngine jsEngine= scriptEngineMgr.getEngineByName("JavaScript");

  private static void initScriptValue(ScriptEngine jsEngine) throws ScriptException {

    jsEngine.eval("<script language='javascript'>");
    jsEngine.eval("alert('아이디와 비밀번호를 입력해 주세요.')");
    jsEngine.eval("</script>");
  }


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

  @PostMapping("/login/modal")
  public String loginModal(@Valid @ModelAttribute("loginForm") LoginForm loginForm,
      BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response)
      throws NoSuchAlgorithmException, IOException, ScriptException {
    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();

      out.println("<script language='javascript'>");
      out.println("alert('아이디와 비밀번호를 입력해 주세요.')");
      out.println("</script>");

      out.flush();
//      initScriptValue(jsEngine);
      return "home";
    }
    Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
    if (loginMember == null) {
      bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();

      out.println("<script language='javascript'>");
      out.println("alert('아이디 또는 비밀번호가 맞지 않습니다.')");
      out.println("</script>");

      out.flush();
      return "home";
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
